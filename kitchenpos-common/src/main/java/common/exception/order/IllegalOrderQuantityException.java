package common.exception.order;


import common.exception.ServiceException;

/**
 * packageName : kitchenpos.order.domain
 * fileName : IllegalOrderQuantityException
 * author : haedoang
 * date : 2021-12-27
 * description :
 */
public class IllegalOrderQuantityException extends ServiceException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "사용할 수 없는 수량입니다. %s";

    public IllegalOrderQuantityException(Long value) {
        super(String.format(message, value));
    }
}
