package com.neemroz.config;

import com.neemroz.model.Product;
import com.neemroz.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) return;

            List<Product> products = Arrays.asList(

                    // ─────────────────────────────────────────
                    // BEDSHEETS
                    // ─────────────────────────────────────────
                    Product.builder()
                            .name("Crimson Rose Embroidered Bed Set")
                            .description("Luxurious maroon bedsheet with intricate white rose embroidery and elegant lace border. Includes 1 bedsheet + 4 pillow covers + 2 cushion covers. Premium 300TC cotton, soft on skin, perfect for all seasons.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/crimson-1.jpg")
                            .images(Arrays.asList(
                                    "/images/products/crimson-1.jpg",
                                    "/images/products/crimson-2.jpg",
                                    "/images/products/crimson-3.jpg",
                                    "/images/products/crimson-4.jpg",
                                    "/images/products/crimson-5.jpg",
                                    "/images/products/crimson-6.jpg",
                                    "/images/products/crimson-7.jpg"
                            ))
                            .colors(Arrays.asList("#8B0000", "#1a1a1a"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(15).badge("BESTSELLER").rating(4.9).reviewCount(87).isActive(true).build(),

                    Product.builder()
                            .name("Scarlet Bloom Embroidered Bed Set")
                            .description("Vibrant scarlet red bedsheet with beautiful rose embroidery and white lace trim. Includes 1 bedsheet + 4 pillow covers + 2 cushion covers. 100% pure cotton, machine washable, colour-fast.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/scarlet-1.jpg")
                            .images(Arrays.asList(
                                    "/images/products/scarlet-1.jpg",
                                    "/images/products/scarlet-2.jpg",
                                    "/images/products/scarlet-3.jpg",
                                    "/images/products/scarlet-4.jpg",
                                    "/images/products/scarlet-5.jpg",
                                    "/images/products/scarlet-6.jpg"
                            ))
                            .colors(Arrays.asList("#CC0000", "#5C3317"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(12).badge("HOT").rating(4.8).reviewCount(64).isActive(true).build(),

                    Product.builder()
                            .name("Garden Circle Embroidered Bed Set")
                            .description("Unique circular medallion botanical embroidery on premium red cotton. Includes 1 bedsheet + 4 pillow covers + 2 cushion covers. Stunning design with white lace border, available in Red and Grey.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/garden-1.jpg")
                            .images(Arrays.asList(
                                    "/images/products/garden-1.jpg",
                                    "/images/products/garden-2.jpg",
                                    "/images/products/garden-3.jpg",
                                    "/images/products/garden-4.jpg"
                            ))
                            .colors(Arrays.asList("#CC0000", "#555555"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(10).badge("NEW").rating(4.7).reviewCount(42).isActive(true).build(),

                    Product.builder()
                            .name("Navy Blue Medallion Embroidered Bed Set")
                            .description("Stunning navy blue bedsheet with intricate white medallion embroidery. All-over circular floral pattern with white lace border. Includes 1 bedsheet + 4 pillow covers + 2 cushion covers. Premium cotton, breathable and elegant.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/new-24.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-24.jpg",
                                    "/images/products/new-25.jpg",
                                    "/images/products/new-21.jpg",
                                    "/images/products/new-22.jpg",
                                    "/images/products/new-23.jpg"
                            ))
                            .colors(Arrays.asList("#0A1172"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(12).badge("NEW").rating(4.9).reviewCount(38).isActive(true).build(),

                    Product.builder()
                            .name("Orange Red Floral Embroidered Bed Set")
                            .description("Vibrant orange-red bedsheet with all-over white floral embroidery and scalloped lace border. Includes 1 bedsheet + 2 pillow covers. Bold and beautiful design that transforms any bedroom. 100% pure cotton.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/new-26.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-26.jpg",
                                    "/images/products/new-28.jpg",
                                    "/images/products/new-29.jpg",
                                    "/images/products/new-27.jpg"
                            ))
                            .colors(Arrays.asList("#FF4500"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(15).badge("BESTSELLER").rating(4.8).reviewCount(55).isActive(true).build(),

                    Product.builder()
                            .name("Maroon Geometric Embroidered Bed Set")
                            .description("Rich maroon bedsheet with intricate geometric leaf and snowflake embroidery pattern. White lace border adds an elegant finishing touch. Includes 1 bedsheet + 2 pillow covers. Premium 300TC cotton, soft and durable.")
                            .price(BigDecimal.valueOf(3500.00)).oldPrice(BigDecimal.valueOf(4500.00))
                            .category("bedsheet")
                            .imageUrl("/images/products/new-31.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-31.jpg",
                                    "/images/products/new-33.jpg",
                                    "/images/products/new-30.jpg",
                                    "/images/products/new-32.jpg"
                            ))
                            .colors(Arrays.asList("#800000"))
                            .sizes(Arrays.asList("Double", "King"))
                            .stock(10).badge("NEW").rating(4.7).reviewCount(29).isActive(true).build(),

                    // ─────────────────────────────────────────
                    // KIDS WEAR
                    // ─────────────────────────────────────────
                    Product.builder()
                            .name("Black Peplum Dhoti Salwar Set")
                            .description("Elegant black peplum kurta with dhoti salwar and dupatta. Features beautiful gold trim borders. Perfect for festivals, weddings and special occasions. Full set includes kurta, salwar and dupatta.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-2.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-2.jpg",
                                    "/images/products/new-1.jpg",
                                    "/images/products/new-3.jpg"
                            ))
                            .colors(Arrays.asList("#000000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(15).badge("NEW").rating(4.8).reviewCount(34).isActive(true).build(),

                    Product.builder()
                            .name("Black Sharara Set with White Embroidery")
                            .description("Stunning black sharara set with intricate white embroidery on yoke and hem. Comes with matching dupatta. Perfect for Eid, weddings and festive wear.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-5.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-5.jpg",
                                    "/images/products/new-4.jpg",
                                    "/images/products/new-6.jpg"
                            ))
                            .colors(Arrays.asList("#000000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(15).badge("BESTSELLER").rating(4.9).reviewCount(51).isActive(true).build(),

                    Product.builder()
                            .name("Red Peplum Dhoti Salwar Set")
                            .description("Beautiful red peplum kurta with matching dhoti salwar and dupatta. Gold trim detailing on borders. A perfect traditional outfit for festivals and celebrations.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-8.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-8.jpg",
                                    "/images/products/new-7.jpg",
                                    "/images/products/new-9.jpg"
                            ))
                            .colors(Arrays.asList("#CC0000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(20).badge("HOT").rating(4.7).reviewCount(28).isActive(true).build(),

                    Product.builder()
                            .name("Purple Paisley Lawn Suit")
                            .description("Pretty purple paisley printed lawn suit with matching trousers. Lightweight and breathable fabric, perfect for everyday wear and casual occasions.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-12.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-12.jpg",
                                    "/images/products/new-11.jpg",
                                    "/images/products/new-10.jpg",
                                    "/images/products/new-13.jpg"
                            ))
                            .colors(Arrays.asList("#9B59B6"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(18).badge(null).rating(4.6).reviewCount(19).isActive(true).build(),

                    Product.builder()
                            .name("Red Embroidered Kurta with Dupatta")
                            .description("Gorgeous red kurta with rich gold embroidered border and matching dupatta with paisley design. Traditional yet stylish, perfect for weddings and festive occasions.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-14.jpg")
                            .images(Arrays.asList("/images/products/new-14.jpg"))
                            .colors(Arrays.asList("#CC0000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(15).badge("NEW").rating(4.8).reviewCount(22).isActive(true).build(),

                    Product.builder()
                            .name("Pink Bandhani Tiered Gharara Set")
                            .description("Vibrant pink bandhani printed tiered frock with mustard palazzo and dupatta. Beautiful ethnic print with gold lace trim. Perfect for festive occasions and celebrations.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-15.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-15.jpg",
                                    "/images/products/new-16.jpg",
                                    "/images/products/new-17.jpg"
                            ))
                            .colors(Arrays.asList("#E91E63", "#F0A500"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(20).badge("BESTSELLER").rating(4.9).reviewCount(45).isActive(true).build(),

                    Product.builder()
                            .name("Maroon Booti Gharara Set — Kids")
                            .description("Elegant maroon booti printed gharara set with gold coin lace trim. Traditional gold print on rich maroon fabric, perfect for weddings and festive occasions.")
                            .price(BigDecimal.valueOf(1299.00)).oldPrice(BigDecimal.valueOf(1899.00))
                            .category("kids")
                            .imageUrl("/images/products/new-19.jpg")
                            .images(Arrays.asList(
                                    "/images/products/new-19.jpg",
                                    "/images/products/new-20.jpg"
                            ))
                            .colors(Arrays.asList("#800000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(15).badge("NEW").rating(4.8).reviewCount(31).isActive(true).build(),

                    // ─────────────────────────────────────────
                    // WOMEN WEAR
                    // ─────────────────────────────────────────
                    Product.builder()
                            .name("Maroon Booti Gharara Set — Women")
                            .description("Stunning maroon gharara set with all-over gold booti print and intricate embroidered yoke. Features gold coin lace hem. Includes kurta and flared gharara. Perfect for weddings, Eid and festive occasions.")
                            .price(BigDecimal.valueOf(2499.00)).oldPrice(BigDecimal.valueOf(3499.00))
                            .category("women")
                            .imageUrl("/images/products/new-18.jpg")
                            .images(Arrays.asList("/images/products/new-18.jpg"))
                            .colors(Arrays.asList("#800000"))
                            .sizes(Arrays.asList("S", "M", "L", "XL"))
                            .stock(10).badge("NEW").rating(4.9).reviewCount(12).isActive(true).build()
            );

            productRepository.saveAll(products);
            System.out.println("✅ Ayezu Collection — " + products.size() + " products seeded successfully!");
        };
    }
}
