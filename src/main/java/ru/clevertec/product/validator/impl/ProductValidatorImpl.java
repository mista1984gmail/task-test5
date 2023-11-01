package ru.clevertec.product.validator.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.validator.ProductValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ProductValidatorImpl implements ProductValidator {
    @Override
    public boolean isProductNull(Product product) {
        return product == null;
    }

    @Override
    public boolean isProductNameNullOrEmpty(Product product) {
        return product.getName() == null || product.getName().isEmpty();
    }

    @Override
    public boolean isNameNotConsistsOnlyCyrillicLetters(Product product) {
        return !(product.getName().matches("^[а-яёА-ЯЁ\s]+$"));
    }

    @Override
    public boolean isNameNotLong_5_10_Characters(Product product) {
        return product.getName().length() < 5 || product.getName().length() > 10;
    }

    @Override
    public boolean isDescriptionNotConsistsOnlyCyrillicLetters(Product product) {
        return !(product.getDescription().matches("^[а-яёА-ЯЁ\s]+$"));
    }
    @Override
    public boolean isDescriptionNotLong_10_30_Characters(Product product) {
        return product.getDescription().length() < 10 || product.getDescription().length() > 30;
    }

    @Override
    public boolean isPriceNull(Product product) {
        return product.getPrice() == null;
    }

    @Override
    public boolean isPriceNotPositiveNumber(Product product) {
        return product.getPrice().signum() != 1;
    }

    @Override
    public boolean isCreatedNull(Product product) {
        return product.getCreated() == null;
    }

    public Map<String, Predicate<Product>> getCheckListForProduct (){
        Map <String, Predicate<Product>> checklistProduct= new HashMap<>();

        Predicate<Product>isProductNull = this::isProductNull;
        Predicate<Product>isProductNameNullOrEmpty = this::isProductNameNullOrEmpty;
        Predicate<Product>isNameNotConsistsCyrillicLetters = this::isNameNotConsistsOnlyCyrillicLetters;
        Predicate<Product>isNameNotLong_5_10_Characters = this::isNameNotLong_5_10_Characters;
        Predicate<Product>isDescriptionNotConsistsCyrillicLetters = this::isDescriptionNotConsistsOnlyCyrillicLetters;
        Predicate<Product>isDescriptionNotLong_10_30_Characters = this::isDescriptionNotLong_10_30_Characters;
        Predicate<Product>isPriceNull = this::isPriceNull;
        Predicate<Product>isPriceNotPositiveNumber = this::isPriceNotPositiveNumber;
        Predicate<Product>isCreatedNull = this::isCreatedNull;

        checklistProduct.put("Product is null!!!", isProductNull);
        checklistProduct.put("Product name is null or empty!!!", isProductNameNullOrEmpty);
        checklistProduct.put("Product name is not consists of Cyrillic letters!!!", isNameNotConsistsCyrillicLetters);
        checklistProduct.put("Product name is not 5-10 characters long!!!", isNameNotLong_5_10_Characters);
        checklistProduct.put("Product description is not consists of Cyrillic letters!!!", isDescriptionNotConsistsCyrillicLetters);
        checklistProduct.put("Product description is not 10-30 characters long!!!", isDescriptionNotLong_10_30_Characters);
        checklistProduct.put("Product price is null!!!", isPriceNull);
        checklistProduct.put("Product price is not positive number!!!", isPriceNotPositiveNumber);
        checklistProduct.put("Product created is null!!!", isCreatedNull);

        return checklistProduct;

    }

    @Override
    public String validateProduct(Product product) {
        return  String.join("\n", getCheckListForProduct().entrySet().stream()
                .filter(entry -> entry.getValue().test(product))
                .map(Map.Entry::getKey).toList());
    }
}
