package kitchenpos.domain;

import kitchenpos.exception.IllegalPriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * packageName : kitchenpos.domain
 * fileName : Price
 * author : haedoang
 * date : 2021/12/19
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

    public static Price of(BigDecimal price) {
        return new Price(price);
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
