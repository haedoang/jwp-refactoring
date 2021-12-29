package common.exception.table;


import common.exception.ServiceException;

/**
 * packageName : kitchenpos.table.exception
 * fileName : IllegalGuestNumberException
 * author : haedoang
 * date : 2021-12-27
 * description :
 */

public class IllegalGuestNumberException extends ServiceException {
    private static final Long serialVersionUID = 1L;
    public static final String message = "사용할 수 없는 인원 수 입니다. %s";

    public IllegalGuestNumberException(Integer value) {
        super(String.format(message, value));
    }
}
