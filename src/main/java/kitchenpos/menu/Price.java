package kitchenpos.menu;

import kitchenpos.menu.exception.IllegalPriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * packageName : kitchenpos.menu
 * fileName : Price
 * author : haedoang
 * date : 2021/12/18
 * description :
 */
@Embeddable
public class Price {
    public static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(BigDecimal value) {
        validate(value);
        this.price = value;
    }

    private void validate(BigDecimal value) {
        if(Objects.isNull(value) || MIN_PRICE.compareTo(value) > 0) {
            throw new IllegalPriceException();
        }
    }

    public BigDecimal value() {
        return price;
    }


}
