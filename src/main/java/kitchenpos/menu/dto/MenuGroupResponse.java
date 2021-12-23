package kitchenpos.menu.dto;

import kitchenpos.menu.domain.MenuGroup;

import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * packageName : kitchenpos.application
 * fileName : MenuGroupResponse
 * author : haedoang
 * date : 2021-12-21
 * description :
 */
//FIXME 생성자 제한하기
public class MenuGroupResponse {
    private Long id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse of(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getName());
    }

    public static List<MenuGroupResponse> ofList(List<MenuGroup> menuGroups) {
        return menuGroups.stream()
                .map(MenuGroupResponse::of)
                .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
