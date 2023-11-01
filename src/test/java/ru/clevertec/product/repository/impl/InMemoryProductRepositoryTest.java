package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.db.DataBase;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ConstantsForTest;
import ru.clevertec.product.util.ProductTestData;
import ru.clevertec.product.validator.ProductValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    @Mock
    private DataBase dataBase;
    @Mock
    private ProductValidator productValidator;
    @InjectMocks
    private InMemoryProductRepository productRepository;

    @Test
    void shouldFindProductByUUID() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        Optional<Product> expected = ProductTestData.builder()
                .build().buildProductOptional();

        when(dataBase.executeGetOperation(uuid))
                .thenReturn(expected);

        //when
        Optional<Product> actual = productRepository.findById(uuid);

        //then
        assertEquals(expected, actual);
        verify(dataBase).executeGetOperation(uuid);
    }

    @Test
    void shouldReturnListOfAllProducts() {
        // given
        Product firstProductToSave = ProductTestData.builder()
                .build().buildProduct();
        Product secondProductToSave = ProductTestData.builder()
                .build().buildProduct();
        List<Product> expected = Arrays.asList(firstProductToSave, secondProductToSave);

        when(dataBase.executeGetAllOperation())
                .thenReturn(expected);

        //when
        List<Product> actual = productRepository.findAll();

        //then
        assertArrayEquals(expected.toArray(), actual.toArray());
        verify(dataBase).executeGetAllOperation();
    }

    @Test
    void shouldSaveProduct() {
        // given
        Product productForSave = ProductTestData.builder().withUuid(null).build().buildProduct();
        String errorMessages = "";
        Product savedProduct = ProductTestData.builder().build().buildProduct();

        when(productValidator.validateProduct(productForSave))
                .thenReturn(errorMessages);
        when(dataBase.executeSaveOperation(productForSave))
                .thenReturn(savedProduct);

        //when
        Product productFromDB = productRepository.save(productForSave);

        //then
        assertEquals(savedProduct, productFromDB);
        verify(productValidator).validateProduct(productForSave);
        verify(dataBase).executeSaveOperation(productForSave);
    }

    @Test
    void shouldNotSaveProductAndThrowsIllegalArgumentException() {
        // given
        Product productForSave = ProductTestData.builder()
                .withUuid(null)
                .withName("apple")
                .build().buildProduct();
        String errorMessages = "Product name is not consists of Cyrillic letters!!!";

        when(productValidator.validateProduct(productForSave))
                .thenReturn(errorMessages);

        //when
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productRepository.save(productForSave);
        });

        //then
        Assertions.assertEquals(errorMessages, thrown.getMessage());
        verify(productValidator).validateProduct(productForSave);
        verify(dataBase, never()).executeSaveOperation(productForSave);
    }

    @Test
    void shouldDeleteProductByUUID() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;

        doNothing()
                .when(dataBase).executeDeleteOperation(uuid);

        //when
        productRepository.delete(uuid);

        //then
        verify(dataBase).executeDeleteOperation(uuid);
    }
}