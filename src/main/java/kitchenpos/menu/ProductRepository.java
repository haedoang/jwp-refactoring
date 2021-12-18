package kitchenpos.menu;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : kitchenpos.product
 * fileName : ProductRepository
 * author : haedoang
 * date : 2021/12/18
 * description :
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
