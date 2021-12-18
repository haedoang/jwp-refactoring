package kitchenpos.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName : kitchenpos.menu
 * fileName : MenuProductRepository
 * author : haedoang
 * date : 2021/12/18
 * description :
 */
public interface MenuProductRepository extends JpaRepository<MenuProduct, Long> {
    List<MenuProduct> findAllByMenuId(Long menuId);
}
