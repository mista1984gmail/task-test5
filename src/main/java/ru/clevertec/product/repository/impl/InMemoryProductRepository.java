package ru.clevertec.product.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.db.DataBase;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.validator.ProductValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryProductRepository implements ProductRepository {

    private final DataBase dataBase;
    private final ProductValidator productValidator;

    @Override
    public Optional<Product> findById(UUID uuid) {
        return dataBase.executeGetOperation(uuid);
    }

    @Override
    public List<Product> findAll() {
        return dataBase.executeGetAllOperation();
    }

    @Override
    public Product save(Product product) throws IllegalArgumentException{
        String errorMessages = productValidator.validateProduct(product);
        if(!errorMessages.isEmpty()){
            throw new IllegalArgumentException(errorMessages);
        }else {
            return dataBase.executeSaveOperation(product);
        }
    }

    @Override
    public void delete(UUID uuid) {
        dataBase.executeDeleteOperation(uuid);
    }
}
