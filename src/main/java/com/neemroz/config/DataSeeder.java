package com.neemroz.config;

import com.neemroz.model.Product;
import com.neemroz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Products already seeded. Skipping.");
            return;
        }
        log.info("Seeding product data...");

        List<Product> products = List.of(

            Product.builder()
                .name("Ivory Bloom Bedsheet")
                .description("Premium 300TC pure cotton bedsheet with elegant floral print. Soft, breathable and perfect for all seasons. Includes 2 matching pillow covers.")
                .price(new BigDecimal("1299"))
                .oldPrice(new BigDecimal("1899"))
                .category("bedsheets")
                .imageUrl("https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800&q=80"))
                .colors(List.of("#F5F0E8","#D4C5B0","#8B7355","#C4622D"))
                .sizes(List.of("Single","Double","Queen","King"))
                .stock(50).badge("NEW").rating(4.8).reviewCount(124).isActive(true)
                .build(),

            Product.builder()
                .name("Royal Blue Pillow Set")
                .description("Set of 2 premium cotton pillow covers. Size 45x65 cm. Machine washable, colour-fast and fade resistant.")
                .price(new BigDecimal("599"))
                .oldPrice(new BigDecimal("899"))
                .category("pillow")
                .imageUrl("https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=800&q=80"))
                .colors(List.of("#2C4A7C","#4A7CB8","#E8DDD0","#FFFFFF"))
                .sizes(List.of("45x65 cm","50x75 cm"))
                .stock(80).badge("BESTSELLER").rating(4.9).reviewCount(89).isActive(true)
                .build(),

            Product.builder()
                .name("Sunset Garden Bed Set")
                .description("Complete king-size bed set with 1 bedsheet and 4 pillow covers. Vibrant sunset print on premium 300TC cotton.")
                .price(new BigDecimal("2899"))
                .oldPrice(new BigDecimal("4299"))
                .category("bedset")
                .imageUrl("https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=800&q=80"))
                .colors(List.of("#E07B54","#F4A87A","#FAF7F2","#8B6A4A"))
                .sizes(List.of("Double","Queen","King"))
                .stock(30).badge("SALE").rating(4.7).reviewCount(56).isActive(true)
                .build(),

            Product.builder()
                .name("Nordic Frost Duvet Cover")
                .description("Minimalist Scandinavian-inspired duvet cover in cool white and blue tones. 200x200 cm, 100% cotton, hidden button closure.")
                .price(new BigDecimal("1799"))
                .oldPrice(new BigDecimal("2499"))
                .category("duvet")
                .imageUrl("https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800&q=80"))
                .colors(List.of("#FFFFFF","#E8F0F4","#B8D4E0","#6A9BB5"))
                .sizes(List.of("Single","Double","King"))
                .stock(40).badge("NEW").rating(4.6).reviewCount(43).isActive(true)
                .build(),

            Product.builder()
                .name("Sage Garden Bedsheet")
                .description("Calming sage green bedsheet with subtle botanical print. 250TC pure cotton, double size. Gets softer with every wash.")
                .price(new BigDecimal("999"))
                .oldPrice(new BigDecimal("1499"))
                .category("bedsheets")
                .imageUrl("https://images.unsplash.com/photo-1615874959474-d609969a20ed?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1615874959474-d609969a20ed?w=800&q=80"))
                .colors(List.of("#7A9E7E","#A8C4A8","#E8DDD0","#4A6741"))
                .sizes(List.of("Single","Double","Queen"))
                .stock(60).badge(null).rating(4.5).reviewCount(78).isActive(true)
                .build(),

            Product.builder()
                .name("Floral Dream Pillow Set")
                .description("Set of 4 pillow covers with beautiful floral design. 45x65 cm each. Premium cotton with vibrant long-lasting colours.")
                .price(new BigDecimal("799"))
                .oldPrice(new BigDecimal("1199"))
                .category("pillow")
                .imageUrl("https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=800&q=80"))
                .colors(List.of("#E8A0B0","#F4C8D4","#FAF7F2","#C47888"))
                .sizes(List.of("45x65 cm"))
                .stock(70).badge("HOT").rating(4.8).reviewCount(102).isActive(true)
                .build(),

            Product.builder()
                .name("Midnight Luxury Bed Set")
                .description("Premium 400TC king-size bed set in rich midnight blue with gold accents. Includes sheet and 4 pillow covers. Hotel-grade quality.")
                .price(new BigDecimal("3499"))
                .oldPrice(new BigDecimal("5499"))
                .category("bedset")
                .imageUrl("https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800&q=80"))
                .colors(List.of("#1A1A2E","#2D2D4E","#C4A882","#E8DDD0"))
                .sizes(List.of("Queen","King"))
                .stock(20).badge("PREMIUM").rating(4.9).reviewCount(34).isActive(true)
                .build(),

            Product.builder()
                .name("Terracotta Stripe Duvet Cover")
                .description("Warm terracotta with classic stripe pattern. Queen size 220x240 cm. 100% cotton, zipper closure, ties at corners.")
                .price(new BigDecimal("2199"))
                .oldPrice(new BigDecimal("2999"))
                .category("duvet")
                .imageUrl("https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=800&q=80")
                .images(List.of("https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=800&q=80"))
                .colors(List.of("#C4622D","#E8845C","#FAF7F2","#8B4422"))
                .sizes(List.of("Double","Queen","King"))
                .stock(35).badge(null).rating(4.7).reviewCount(61).isActive(true)
                .build()
        );

        productRepository.saveAll(products);
        log.info("Seeded {} products successfully.", products.size());
    }
}
