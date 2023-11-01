package ru.clevertec.product.validator.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.validator.ProductValidator;

public class ProductValidatorImpl implements ProductValidator {
    @Override
    public boolean isProductNull(Product product) {
        return false;
    }

    @Override
    public boolean isProductNameNullOrEmpty(Product product) {
        return false;
    }

    @Override
    public boolean isNameNotConsistsOnlyCyrillicLetters(Product product) {
        return false;
    }

    @Override
    public boolean isNameNotLong_5_10_Characters(Product product) {
        return false;
    }

    @Override
    public boolean isDescriptionNotConsistsOnlyCyrillicLetters(Product product) {
        return false;
    }

    @Override
    public boolean isDescriptionNotLong_10_30_Characters(Product product) {
        return false;
    }

    @Override
    public boolean isPriceNull(Product product) {
        return false;
    }

    @Override
    public boolean isPriceNotPositiveNumber(Product product) {
        return false;
    }

    @Override
    public boolean isCreatedNull(Product product) {
        return false;
    }

    @Override
    public String validateProduct(Product product) {
        return null;
    }
}
