package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ConstantsForTest;
import ru.clevertec.product.util.ProductTestData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Captor
    private ArgumentCaptor<Product> productCaptor;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldDeleteProduct() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        Optional<Product> productFromDB = ProductTestData.builder()
                .build().buildProductOptional();

        when(productRepository.findById(uuid))
                .thenReturn(productFromDB);
        doNothing()
                .when(productRepository).delete(uuid);

        //when
        productService.delete(uuid);

        //then
        verify(productRepository).delete(uuid);
    }

    @Test
    void shouldNotDeleteProductAndThrowsProductNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        String errorMessage = "Product with uuid: " + uuid + " not found";
        Optional<Product> productFromDB = Optional.empty();

        when(productRepository.findById(uuid))
                .thenReturn(productFromDB);

        //when
        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class, () -> {
            productService.delete(uuid);
        });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
        verify(productRepository, never()).delete(uuid);
    }

    @Test
    void shouldNotGetProductByUUIDAndThrowsProductNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        String errorMessage = "Product with uuid: " + uuid + " not found";
        Optional<Product> productFromDB = Optional.empty();

        when(productRepository.findById(uuid))
                .thenReturn(productFromDB);

        //when
        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class, () -> {
            productService.get(uuid);
        });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
    }

    @Test
    void shouldGetProductByUUID() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        Optional<Product> optionalProduct = ProductTestData.builder()
                .build().buildProductOptional();
        Product product = ProductTestData.builder()
                .build().buildProduct();
        InfoProductDto expected = ProductTestData.builder()
                .build().buildInfoProductDto();

        when(productRepository.findById(uuid))
                .thenReturn(optionalProduct);
        when(productMapper.toInfoProductDto(product))
                .thenReturn(expected);

        //when
        InfoProductDto actual = productService.get(uuid);

        //then
        assertEquals(expected, actual);
        verify(productRepository).findById(uuid);
        verify(productMapper).toInfoProductDto(product);
    }

    @Test
    void shouldGetAllProducts() {
        // given
        Product firstProduct = ProductTestData.builder()
                .build().buildProduct();
        Product secondProduct = ProductTestData.builder()
                .withUuid(UUID.fromString("5af9041b-6f18-4191-9205-a7bfe8630f6e"))
                .build().buildProduct();
        List<Product> products = Arrays.asList(firstProduct, secondProduct);
        InfoProductDto firstInfoProductDto = ProductTestData.builder()
                .build().buildInfoProductDto();
        InfoProductDto secondInfoProductDto = ProductTestData.builder()
                .withUuid(UUID.fromString("5af9041b-6f18-4191-9205-a7bfe8630f6e"))
                .build().buildInfoProductDto();
        List<InfoProductDto> expected = Arrays.asList(firstInfoProductDto, secondInfoProductDto);

        when(productRepository.findAll())
                .thenReturn(products);
        when(productMapper.toInfoProductDto(firstProduct))
                .thenReturn(firstInfoProductDto);
        when(productMapper.toInfoProductDto(secondProduct))
                .thenReturn(secondInfoProductDto);

        //when
        List<InfoProductDto> actual = productService.getAll();

        //then
        assertArrayEquals(expected.toArray(), actual.toArray());
        assertEquals(2, actual.size());
        verify(productRepository).findAll();
        verify(productMapper, times(2)).toInfoProductDto(any());
    }

    @Test
    void shouldCreateProduct() {
        // given
        ProductDto productDtoForSave = ProductTestData.builder()
                .build().buildProductDto();
        Product productForSave = ProductTestData.builder()
                .build().buildProduct();
        UUID expectedUUID = ConstantsForTest.PRODUCT_UUID;

        when(productMapper.toProduct(productDtoForSave))
                .thenReturn(productForSave);
        when(productRepository.save(productForSave))
                .thenReturn(productForSave);

        //when
        UUID actualUUID = productService.create(productDtoForSave);

        //then
        verify(productRepository).save(productCaptor.capture());
        verify(productMapper).toProduct(productDtoForSave);
        Product actual = productCaptor.getValue();

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, productForSave.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, productForSave.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, productForSave.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, productForSave.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, productForSave.getCreated());

        assertEquals(expectedUUID, actualUUID);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        ProductDto productDtoForUpdate = ProductTestData.builder()
                .withName("груша")
                .build().buildProductDto();
        Product productForUpdate = ProductTestData.builder()
                .withName("груша")
                .build().buildProduct();
        Product expected = ProductTestData.builder()
                .withName("груша")
                .build().buildProduct();
        Optional <Product> productFromDB = ProductTestData.builder()
                .build().buildProductOptional();
        UUID uuid = ConstantsForTest.PRODUCT_UUID;

        when(productMapper.toProduct(productDtoForUpdate)).thenReturn(productForUpdate);
        when(productRepository.findById(uuid)).thenReturn(productFromDB);
        when(productRepository.save(productForUpdate)).thenReturn(expected);

        //when
        productService.update(uuid, productDtoForUpdate);

        //then
        verify(productRepository).save(productCaptor.capture());
        verify(productMapper).toProduct(productDtoForUpdate);
        Product actual = productCaptor.getValue();

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void shouldNotUpdateProductAndThrowsProductNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.PRODUCT_UUID;
        String errorMessage = "Product with uuid: " + uuid + " not found";
        Optional<Product> productFromDB = Optional.empty();

        when(productRepository.findById(uuid))
                .thenReturn(productFromDB);

        //when
        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class, () -> {
            productService.get(uuid);
        });

        //then
        Assertions.assertEquals(errorMessage, thrown.getMessage());
    }

}