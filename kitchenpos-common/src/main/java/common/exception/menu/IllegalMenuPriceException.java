package common.exception.menu;


import common.exception.ServiceException;

import java.math.BigDecimal;

/**
 * packageName : kitchenpos.menu.exception
 * fileName : IllegalMenuPriceException
 * author : haedoang
 * date : 2021-12-27
 * description :
 */
public class IllegalMenuPriceException extends ServiceException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "사용할 수 없는 금액입니다. %s";

    public IllegalMenuPriceException(BigDecimal value) {
        super(String.format(message, value));
    }
}
