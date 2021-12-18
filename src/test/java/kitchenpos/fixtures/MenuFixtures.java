package kitchenpos.fixtures;

import kitchenpos.menu.Menu;
import kitchenpos.menu.MenuGroup;
import kitchenpos.menu.MenuProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * packageName : kitchenpos.fixtures
 * fileName : MenuFixtures
 * author : haedoang
 * date : 2021/12/17
 * description :
 */
public class MenuFixtures {

    public static Menu createMenu(Long id, String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroupId(menuGroupId);
        menu.setMenuProducts(menuProducts);
        return menu;
    }

    public static Menu createMenu(Long id, String name, BigDecimal price, MenuGroup menuGroup) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroup(menuGroup);
        return menu;
    }

    public static List<Menu> createMenus(Menu... menus) {
        return Arrays.asList(menus);
    }
}