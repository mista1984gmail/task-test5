package ru.clevertec.product.db.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.db.DataBase;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ConstantsForTest;
import ru.clevertec.product.util.ProductTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyDataBaseTest {
    private DataBase dataBaseForTests;

    @BeforeEach
    void setUp(){
        dataBaseForTests = new MyDataBase();
        List<Product> listForDelete = dataBaseForTests.executeGetAllOperation();
        for (Product product: listForDelete) {
            dataBaseForTests.executeDeleteOperation(product.getUuid());
        }
    }

    @Test
    void shouldReturnOptionalProductByUUID() {
        // given
        Product productForSave = ProductTestData.builder()
                .withUuid(null)
                .build().buildProduct();
        Optional<Product> expected = ProductTestData.builder()
                .withUuid(null)
                .build().buildProductOptional();
        Product savedProduct = dataBaseForTests.executeSaveOperation(productForSave);
        UUID expectedUUID = dataBaseForTests.executeGetOperation(savedProduct.getUuid()).get().getUuid();

        //when
        Optional<Product> actual = dataBaseForTests.executeGetOperation(expectedUUID);

        //then
        assertThat(actual.get())
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expectedUUID)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.get().getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.get().getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.get().getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.get().getCreated());
    }

    @Test
    void shouldReturnNull_whenDoNotFindProductByUUID() {
        // given
        Optional<Product> expected = Optional.empty();
        UUID uuid = ConstantsForTest.PRODUCT_UUID;

        //when
        Optional<Product> actual = dataBaseForTests.executeGetOperation(uuid);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void shouldSaveProduct() {
        // given
        Product productForSave = ProductTestData.builder()
                .withUuid(null)
                .withCreated(null)
                .build().buildProduct();

        //when
        Product savedProduct = dataBaseForTests.executeSaveOperation(productForSave);
        Product productFromDB = dataBaseForTests.executeGetOperation(savedProduct.getUuid()).get();

        //then
        assertThat(productFromDB)
                .hasFieldOrPropertyWithValue(Product.Fields.name, productForSave.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, productForSave.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, productForSave.getPrice());
    }

    @Test
    void shouldReturnListOfAllProducts() {
        // given
        Product firstProductToSave = ProductTestData.builder()
                .withUuid(null)
                .build().buildProduct();
        Product secondProductToSave = ProductTestData.builder()
                .withUuid(null)
                .build().buildProduct();

        //when
        dataBaseForTests.executeSaveOperation(firstProductToSave);
        dataBaseForTests.executeSaveOperation(secondProductToSave);

        //then
        assertEquals(2, dataBaseForTests.executeGetAllOperation().size());
    }

    @Test
    void shouldDeleteProductByUUID() {
        // given
        Product productForSave = ProductTestData.builder()
                .withUuid(null)
                .build().buildProduct();
        Product savedProduct = dataBaseForTests.executeSaveOperation(productForSave);
        UUID uuidForDelete = dataBaseForTests.executeGetOperation(savedProduct.getUuid()).get().getUuid();

        //when
        dataBaseForTests.executeDeleteOperation(uuidForDelete);

        //then
        assertEquals(0, dataBaseForTests.executeGetAllOperation().size());
    }
}