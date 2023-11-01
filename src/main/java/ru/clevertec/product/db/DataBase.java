package ru.clevertec.product.db;

import ru.clevertec.product.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataBase {
    /**
     * Сохраняет или обновляет продукт в памяти
     *
     * @param product сохраняемый продукт
     * @return сохранённый продукт
     */
    Product executeSaveOperation(Product product);
    /**
     * Ищет в памяти продукт по идентификатору
     *
     * @param uuid идентификатор продукта
     * @return Optional<Product> если найден, иначе Optional.empty()
     */
    Optional<Product> executeGetOperation(UUID uuid);
    /**
     * Ищет все продукты в памяти
     *
     * @return список найденных продуктов
     */
    List<Product> executeGetAllOperation();
    /**
     * Удаляет продукт из памяти по идентификатору
     *
     * @param uuid идентификатор продукта
     */
    void executeDeleteOperation(UUID uuid);
}
