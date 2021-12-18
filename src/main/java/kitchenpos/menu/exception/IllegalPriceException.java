package kitchenpos.menu.exception;

import kitchenpos.menu.Price;

/**
 * packageName : kitchenpos.menu.exception
 * fileName : IllegalPriceException
 * author : haedoang
 * date : 2021/12/18
 * description :
 */
public class IllegalPriceException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "가격은 %d원 이상이어야 합니다.";

    public IllegalPriceException() {
        super(String.format(message, Price.MIN_PRICE.intValue()));
    }
}
