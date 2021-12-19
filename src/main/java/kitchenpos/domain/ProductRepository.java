package kitchenpos.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : kitchenpos.domain
 * fileName : ProductRepository
 * author : haedoang
 * date : 2021/12/19
 * description :
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
