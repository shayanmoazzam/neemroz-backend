package com.neemroz.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neemroz.model.Product;
import com.neemroz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ImageMigrationController {

    private final Cloudinary cloudinary;
    private final ProductRepository productRepository;

    @PostMapping("/migrate-images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> migrateImages() {
        List<Product> products = productRepository.findAll();
        int updated = 0;
        List<String> errors = new ArrayList<>();

        for (Product product : products) {
            try {
                // Migrate main imageUrl
                String newMainUrl = uploadFromUrl(product.getImageUrl());
                product.setImageUrl(newMainUrl);

                // Migrate images[] list
                if (product.getImages() != null && !product.getImages().isEmpty()) {
                    List<String> newImages = new ArrayList<>();
                    for (String imgUrl : product.getImages()) {
                        try {
                            newImages.add(uploadFromUrl(imgUrl));
                        } catch (Exception e) {
                            newImages.add(imgUrl); // keep original if fails
                        }
                    }
                    product.setImages(newImages);
                }

                productRepository.save(product);
                updated++;
                System.out.println("✅ Migrated: " + product.getName());

            } catch (Exception e) {
                errors.add(product.getName() + ": " + e.getMessage());
                System.out.println("❌ Failed: " + product.getName() + " — " + e.getMessage());
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", products.size());
        result.put("migrated", updated);
        result.put("failed", errors.size());
        if (!errors.isEmpty()) result.put("errors", errors);
        result.put("message", updated + " of " + products.size() + " products migrated to Cloudinary!");

        return ResponseEntity.ok(result);
    }

    private String uploadFromUrl(String imageUrl) throws Exception {
        if (imageUrl == null || imageUrl.isBlank()) throw new Exception("Empty URL");
        // If already on Cloudinary, skip
        if (imageUrl.contains("cloudinary.com")) return imageUrl;

        try (InputStream in = new URL(imageUrl).openStream()) {
            byte[] bytes = in.readAllBytes();
            Map uploadResult = cloudinary.uploader().upload(
                bytes,
                ObjectUtils.asMap(
                    "folder", "neemroz-products",
                    "resource_type", "image"
                )
            );
            return (String) uploadResult.get("secure_url");
        }
    }
}
