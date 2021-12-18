package kitchenpos.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class MenuProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id", foreignKey = @ForeignKey(name = "fk_menu_product_menu"))
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_menu_product_product"))
    private Product product;

    private long quantity;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public void setMenuId(long menuId) {
        this.menu.setId(menuId);
    }

    public void setProductId(long productId) {
        this.product.setId(productId);
    }

    public Long getProductId() {
        return product.getId();
    }

    public Long getMenuId() {
        return menu.getId();
    }
}
