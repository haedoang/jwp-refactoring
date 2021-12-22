package kitchenpos.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.menu.fixtures.MenuProductFixtures.메뉴상품;
import static kitchenpos.menu.fixtures.ProductFixtures.양념치킨;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * packageName : kitchenpos.menu.domain
 * fileName : MenuProductsTest
 * author : haedoang
 * date : 2021-12-22
 * description :
 */
@DisplayName("MenuProducts 일급컬렉션 테스트")
class MenuProductsTest {

    @Mock
    private Menu menu;

    @Test
    @DisplayName("일급 컬렉션을 생성한다.")
    public void create() throws Exception {
        // given
        List<MenuProduct> given = Arrays.asList(메뉴상품(양념치킨(), 1L), 메뉴상품(양념치킨(), 1L), 메뉴상품(양념치킨(), 1L));
        MenuProducts menuProducts = new MenuProducts();

        // when
        menuProducts.add(menu, given);

        // then
        assertAll(
                () -> assertThat(menuProducts.value()).hasSize(3),
                () -> assertThat(menuProducts.value()).extracting(MenuProduct::getMenu).isNotNull()
        );

    }

}
