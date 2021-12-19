package kitchenpos.exception;

import kitchenpos.domain.Price;

/**
 * packageName : kitchenpos.exception
 * fileName : IllegalPriceException
 * author : haedoang
 * date : 2021/12/19
 * description :
 */
public class IllegalPriceException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "가격은 %d원 이상이어야 합니다.";

    public IllegalPriceException() {
        super(String.format(message, Price.MIN_PRICE.intValue()));
    }
}