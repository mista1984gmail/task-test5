package ru.clevertec.product.db.impl;

import ru.clevertec.product.db.DataBase;
import ru.clevertec.product.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MyDataBase implements DataBase {
    @Override
    public Product executeSaveOperation(Product product) {
        return null;
    }

    @Override
    public Optional<Product> executeGetOperation(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<Product> executeGetAllOperation() {
        return null;
    }

    @Override
    public void executeDeleteOperation(UUID uuid) {

    }
}
