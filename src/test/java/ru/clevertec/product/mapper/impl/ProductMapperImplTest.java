package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.util.ProductTestData;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperImplTest {
    private ProductMapper productMapper;
    @BeforeEach
    void setUp(){
        productMapper = new ProductMapperImpl();
    }

    @Test
    void shouldMappingProductDtoToProduct() {
        // given
        ProductDto productDto = ProductTestData.builder()
                .build().buildProductDto();
        Product expected = ProductTestData.builder()
                .withUuid(null)
                .withCreated(null)
                .build().buildProduct();

        //when
        Product actual = productMapper.toProduct(productDto);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void shouldMappingProductToInfoProductDto() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();
        InfoProductDto expected = ProductTestData.builder()
                .build().buildInfoProductDto();

        //when
        InfoProductDto actual = productMapper.toInfoProductDto(product);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void shouldMergeProductAndProductDtoInProduct_whenChangeNameAndPrice() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();
        ProductDto productDto = ProductTestData.builder()
                .withName("Груша")
                .withPrice(new BigDecimal(1.2))
                .build()
                .buildProductDto();
        Product expected = ProductTestData.builder()
                .withName("Груша")
                .withPrice(new BigDecimal(1.2))
                .build()
                .buildProduct();

        //when
        Product actual = productMapper.merge(product, productDto);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void shouldMergeProductAndProductDtoInProduct_whenChangeDescription() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();
        ProductDto productDto = ProductTestData.builder()
                .withDescription("Страна происхождения PБ")
                .build()
                .buildProductDto();
        Product expected = ProductTestData.builder()
                .withDescription("Страна происхождения PБ")
                .build()
                .buildProduct();

        //when
        Product actual = productMapper.merge(product, productDto);

        //then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

}