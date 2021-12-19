package kitchenpos.fixtures;

import kitchenpos.domain.MenuGroup;

import java.util.Arrays;
import java.util.List;

/**
 * packageName : kitchenpos.fixtures
 * fileName : MenuGroupFixtures
 * author : haedoang
 * date : 2021/12/17
 * description :
 */
public class MenuGroupFixtures {
    public static MenuGroup createMenuGroup(String name) {
        return new MenuGroup(name);
    }
}