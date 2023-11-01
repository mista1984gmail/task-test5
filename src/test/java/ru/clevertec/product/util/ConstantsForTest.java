package ru.clevertec.product.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ConstantsForTest {
    public final static UUID PRODUCT_UUID = UUID.fromString("0f5d347b-1c1e-4941-a56b-a24847e6e3df");
    public final static String PRODUCT_NAME = "Яблоко";
    public final static String PRODUCT_DESCRIPTION = "Страна происхождения Беларусь";
    public final static BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(0.99);
    public final static LocalDateTime PRODUCT_CREATED = LocalDateTime.of(2023, 10, 29, 14, 15);
}
