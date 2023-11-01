package ru.clevertec.product.validator;

import ru.clevertec.product.entity.Product;

public interface ProductValidator {

    /**
     * Проверяет продукт на null
     *
     * @param product       продукт
     * @return true, если продукт null, иначе false
     */

    boolean isProductNull(Product product);

    /**
     * Проверяет имя продукта на null или empty
     *
     * @param product       продукт
     * @return true, если имя продукта null или оно empty, иначе false
     */

    boolean isProductNameNullOrEmpty(Product product);

    /**
     * Проверяет не имеется ли в имени продукта буквы отличные от кирилицы или цифры или иные знаки
     *
     * @param product       продукт
     * @return true, если в имени продукта имеются буквы отличные от кирилицы или цифры или иные знаки, иначе false
     */

    boolean isNameNotConsistsOnlyCyrillicLetters(Product product);

    /**
     * Проверяет длину имени продукта
     *
     * @param product       продукт
     * @return true, если длина имени продукта меньше 5 символов или больше 10, иначе false
     */

    boolean isNameNotLong_5_10_Characters(Product product);

    /**
     * Проверяет не имеется ли в описании продукта буквы отличные от кирилицы или цифры или иные знаки
     *
     * @param product       продукт
     * @return true, если в описании продукта имеются буквы отличные от кирилицы или цифры или иные знаки, иначе false
     */

    boolean isDescriptionNotConsistsOnlyCyrillicLetters(Product product);

    /**
     * Проверяет длину описания продукта
     *
     * @param product       продукт
     * @return true, если длина описания продукта меньше 10 символов или больше 30, иначе false
     */

    boolean isDescriptionNotLong_10_30_Characters(Product product);

    /**
     * Проверяет цену продукта на null
     *
     * @param product       продукт
     * @return true, если цена продукта null, иначе false
     */

    boolean isPriceNull(Product product);

    /**
     * Проверяет цену продукта, чтобы не содержала отрицательных значений и 0
     *
     * @param product       продукт
     * @return true, если цена продукта отрицательна или 0, иначе false
     */

    boolean isPriceNotPositiveNumber(Product product);

    /**
     * Проверяет дату создания продукта на null
     *
     * @param product       продукт
     * @return true, если дата создания продукта null, иначе false
     */

    boolean isCreatedNull(Product product);

    /**
     * Производит валидицию продукта
     *
     * @param product       продукт
     * @return String со списком ошибок валидации, если ошибок нет - пустой String
     */

    String validateProduct(Product product);
}
