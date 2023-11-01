package ru.clevertec.product.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID uuid = ConstantsForTest.PRODUCT_UUID;
    @Builder.Default
    private String name = ConstantsForTest.PRODUCT_NAME;
    @Builder.Default
    private String description = ConstantsForTest.PRODUCT_DESCRIPTION;
    @Builder.Default
    private BigDecimal price = ConstantsForTest.PRODUCT_PRICE;
    @Builder.Default
    private LocalDateTime created = ConstantsForTest.PRODUCT_CREATED;

    public Product buildProduct(){
        return new Product(uuid, name, description, price, created);
    }

    public ProductDto buildProductDto(){
        return new ProductDto(name, description, price);
    }
    public InfoProductDto buildInfoProductDto(){
        return new InfoProductDto(uuid, name, description, price);
    }

    public Optional <Product> buildProductOptional(){
        return Optional.of(new Product(uuid, name, description, price, created));
    }
}
