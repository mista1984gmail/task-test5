package ru.clevertec.product.db.impl;

import ru.clevertec.product.db.DataBase;
import ru.clevertec.product.entity.Product;

import java.util.*;

public class MyDataBase implements DataBase {
    private Map<UUID, Product> PRODUCTS;

    private static MyDataBase instance;

    public MyDataBase() {
        PRODUCTS = new HashMap<>();}

    public static MyDataBase getInstance(){
        if (instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    @Override
    public synchronized Product executeSaveOperation(Product product){
        MyDataBase dataBase = MyDataBase.getInstance();
        if(product.getUuid()==null){
            UUID savedUUID = UUID.randomUUID();
            product.setUuid(savedUUID);
            dataBase.PRODUCTS.put(savedUUID, product);
            System.out.println("Product created!!!");
        }else {
            dataBase.PRODUCTS.put(product.getUuid(), product);
            System.out.println("Product updated!!!");
        }
        return product;
    }

    @Override
    public Optional<Product> executeGetOperation(UUID uuid) {
        MyDataBase dataBase = MyDataBase.getInstance();
        return Optional.ofNullable(dataBase.PRODUCTS.get(uuid));
    }

    @Override
    public List<Product> executeGetAllOperation() {
        MyDataBase dataBase = MyDataBase.getInstance();
        return new ArrayList<>(dataBase.PRODUCTS.values());
    }

    @Override
    public synchronized void executeDeleteOperation(UUID uuid){
        MyDataBase dataBase = MyDataBase.getInstance();
        dataBase.PRODUCTS.remove(uuid);
    }
}
