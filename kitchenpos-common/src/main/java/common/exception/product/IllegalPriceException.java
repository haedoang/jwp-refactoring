package common.exception.product;


import common.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * packageName : kitchenpos.exception
 * fileName : IllegalPriceException
 * author : haedoang
 * date : 2021/12/20
 * description :
 */
public class IllegalPriceException extends ServiceException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "사용할 수 없는 금액입니다. %s";

    public IllegalPriceException(BigDecimal value) {
        super(String.format(message, value));
    }
}
