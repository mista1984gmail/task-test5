package ru.clevertec.product.validator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ProductTestData;
import ru.clevertec.product.validator.ProductValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductValidatorImplTest {
    private ProductValidator productValidator;

    @BeforeEach
    void setUp(){
        productValidator = new ProductValidatorImpl();
    }

    @Test
    void shouldReturnTrue_whenProductIsNull() {
        // given
        Product product = null;

        //when
        boolean actual = productValidator.isProductNull(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductIsNotNull() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();;

        //when
        boolean actual = productValidator.isProductNull(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnTrue_whenProductNameIsNull() {
        // given
        Product product = ProductTestData.builder()
                .withName(null)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isProductNameNullOrEmpty(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductNameIsNotNull() {
        // given
        Product product = ProductTestData.builder()
                .build()
                .buildProduct();

        //when
        boolean actual = productValidator.isProductNameNullOrEmpty(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnTrue_whenProductNameIsEmpty() {
        // given
        Product product = ProductTestData.builder()
                .withName("")
                .build().buildProduct();

        //when
        boolean actual = productValidator.isProductNameNullOrEmpty(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductNameIsNotEmpty() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isProductNameNullOrEmpty(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnFalse_whenProductNameConsistsOnlyCyrillicLetters() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isNameNotConsistsOnlyCyrillicLetters(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnTrue_whenProductNameDoesNotConsistOnlyCyrillicLetters() {
        // given
        Product product = ProductTestData.builder()
                .withName("apple")
                .build().buildProduct();

        //when
        boolean actual = productValidator.isNameNotConsistsOnlyCyrillicLetters(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductNameIsLongFrom_5_To_10_Characters() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isNameNotLong_5_10_Characters(product);

        //then
        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ябл", "белорусские блоки"})
    void shouldReturnTrue_whenProductNameIsNotLongFrom_5_To_10_Characters(String name) {
        // given
        Product product = ProductTestData.builder()
                .withName(name)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isNameNotLong_5_10_Characters(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductDescriptionConsistsOnlyCyrillicLetters() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isDescriptionNotConsistsOnlyCyrillicLetters(product);

        //then
        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"green", "белорусские 123", "green_123"})
    void shouldReturnTrue_whenProductDescriptionDoesNotConsistOnlyCyrillicLetters(String description) {
        // given
        Product product = ProductTestData.builder()
                .withDescription(description)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isDescriptionNotConsistsOnlyCyrillicLetters(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductDescriptionIsLongFrom_10_To_30_Characters() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isDescriptionNotLong_10_30_Characters(product);

        //then
        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "бел ябл", "очень крупные яблоки сорта Каштеля"})
    void shouldReturnTrue_whenProductDescriptionIsNotLongFrom_10_To_30_Characters(String description) {
        // given
        Product product = ProductTestData.builder()
                .withDescription(description)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isDescriptionNotLong_10_30_Characters(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_whenProductPriceIsNull() {
        // given
        Product product = ProductTestData.builder()
                .withPrice(null)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isPriceNull(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductPriceIsNotNull() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isPriceNull(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnTrue_whenProductPriceIsZero() {
        // given
        Product product = ProductTestData.builder().withPrice(BigDecimal.valueOf(0)).build().buildProduct();

        //when
        boolean actual = productValidator.isPriceNotPositiveNumber(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrue_whenProductPriceIsNegative() {
        // given
        Product product = ProductTestData.builder()
                .withPrice(new BigDecimal(-10.0))
                .build().buildProduct();

        //when
        boolean actual = productValidator.isPriceNotPositiveNumber(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductPriceIsPositive() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isPriceNotPositiveNumber(product);

        //then
        assertFalse(actual);
    }

    @Test
    void shouldReturnFalse_whenProductCreatedIsNull() {
        // given
        Product product = ProductTestData.builder()
                .withCreated(null)
                .build().buildProduct();

        //when
        boolean actual = productValidator.isCreatedNull(product);

        //then
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_whenProductCreatedIsNotNull() {
        // given
        Product product = ProductTestData.builder()
                .build().buildProduct();

        //when
        boolean actual = productValidator.isCreatedNull(product);

        //then
        assertFalse(actual);
    }

}