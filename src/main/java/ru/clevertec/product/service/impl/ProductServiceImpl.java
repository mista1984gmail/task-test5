package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) throws ProductNotFoundException {
        return mapper.toInfoProductDto(productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(uuid)));
    }

    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(mapper::toInfoProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product productToSave = mapper.toProduct(productDto);
        productToSave.setCreated(LocalDateTime.now());
        Product savedProduct = new Product();
        try {
            savedProduct = productRepository.save(productToSave);
        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        }
        return savedProduct.getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) throws ProductNotFoundException{
        Product product = mapper.toProduct(productDto);
        if(productRepository.findById(uuid).isPresent()){
            Product productFromDB = productRepository.findById(uuid).get();
            product.setUuid(uuid);
            product.setCreated(productFromDB.getCreated());
            try {
                productRepository.save(product);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }}
        else throw new ProductNotFoundException(uuid);
    }

    @Override
    public void delete(UUID uuid) throws ProductNotFoundException{
        Optional<Product> product = productRepository.findById(uuid);
        if(product.isPresent()){
            productRepository.delete(uuid);
        }
        else throw new ProductNotFoundException(uuid);
    }
}
