package kitchenpos.application;

import kitchenpos.dao.MenuDao;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderLineItemDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.*;
import kitchenpos.fixtures.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kitchenpos.fixtures.MenuProductFixtures.createMenuProduct;
import static kitchenpos.fixtures.MenuProductFixtures.createMenuProducts;
import static kitchenpos.fixtures.OrderLineItemFixtures.createOrderLineItem;
import static kitchenpos.fixtures.OrderLineItemFixtures.createOrderLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

/**
 * packageName : kitchenpos.application
 * fileName : MenuServiceTest
 * author : haedoang
 * date : 2021/12/17
 * description :
 */
@DisplayName("주문 비즈니스 오브젝트 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private Order order;
    private OrderTable orderTable;

    @Mock
    OrderDao orderDao;

    @Mock
    MenuDao menuDao;

    @Mock
    OrderLineItemDao orderLineItemDao;

    @Mock
    OrderTableDao orderTableDao;

    @InjectMocks
    OrderService orderService;


    @BeforeEach
    void setUp() {
        Product 후라이드 = ProductFixtures.createProduct(1L, "후라이드", new BigDecimal(16000));
        Product 양념치킨 = ProductFixtures.createProduct(2L, "양념치킨", new BigDecimal(16000));

        MenuGroup 두마리메뉴 = MenuGroupFixtures.createMenuGroup(1L, "두마리메뉴");
        MenuGroup 한마리메뉴 = MenuGroupFixtures.createMenuGroup(2L, "두마리메뉴");


        MenuProduct 양념치킨두마리메뉴상품 = createMenuProduct(1L, 1L, 1L, 2);
        MenuProduct 후라이드한마리메뉴상품 = createMenuProduct(4L, 1L, 2L, 1);


        Menu 후라이드한마리메뉴 =
                MenuFixtures.createMenu(
                        1L,
                        "후라이드한마리",
                        new BigDecimal(16000),
                        한마리메뉴.getId(),
                        createMenuProducts(후라이드한마리메뉴상품)
                );

        Menu 양념치킨두마리메뉴 =
                MenuFixtures.createMenu(
                        2L,
                        "양념치킨두마리",
                        new BigDecimal(32000),
                        두마리메뉴.getId(),
                        createMenuProducts(양념치킨두마리메뉴상품)
                );

        OrderLineItem orderLineItem1 = createOrderLineItem(1L, 1L, 후라이드한마리메뉴.getId(), 1);
        OrderLineItem orderLineItem2 = createOrderLineItem(2L, 1L, 양념치킨두마리메뉴.getId(), 2);
        orderTable = OrderTableFixtures.createOrderTable(1L, null, 2, false);
        order = OrderFixtures.createOrder(1L, orderTable.getId(), OrderStatus.COOKING.name(), LocalDateTime.now(), createOrderLineItems(orderLineItem1, orderLineItem2));

    }

    @Test
    @DisplayName("주문을 조회할 수 있다.")
    public void list() throws Exception {
        // given
        given(orderDao.findAll()).willReturn(Lists.newArrayList(order));
        given(orderLineItemDao.findAllByOrderId(anyLong())).willReturn(order.getOrderLineItems());

        // when
        List<Order> orders = orderService.list();

        // then
        assertThat(orders).hasSize(1);
    }


    @Test
    @DisplayName("주문을 등록할 수 있다.")
    public void createOrder() throws Exception {
        // given
        given(orderDao.save(order)).willReturn(order);
        given(menuDao.countByIdIn(anyList())).willReturn((long) order.getOrderLineItems().size());
        given(orderTableDao.findById(anyLong())).willReturn(Optional.of(orderTable));

        // when
        Order actual = orderService.create(order);

        // then
        assertThat(actual).isEqualTo(order);
    }

    @Test
    @DisplayName("주문정보가 있지 않은 경우 등록할 수 없다.")
    public void createFailByNotExistOrderLineItem() throws Exception {
        // given
        order.setOrderLineItems(null);

        // then
        assertThatThrownBy(() -> orderService.create(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문정보가 등록되어 있지 않은 경우 등록할 수 없다.")
    public void createFailByUnknownOrderLineItem() throws Exception {
        // then
        assertThatThrownBy(() -> orderService.create(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("테이블정보가 등록되어 있지 않은 경우 등록할 수 없다.")
    public void createFailByUnknownTable() throws Exception {
        // given
        given(menuDao.countByIdIn(anyList())).willReturn((long) order.getOrderLineItems().size());
        // then
        assertThatThrownBy(() -> orderService.create(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 테이블인 경우 등록할 수 없다.")
    public void createFailByEmptyTable() throws Exception {
        // given
        orderTable.setEmpty(true);
        given(menuDao.countByIdIn(anyList())).willReturn((long) order.getOrderLineItems().size());
        given(orderTableDao.findById(anyLong())).willReturn(Optional.of(orderTable));

        // then
        assertThatThrownBy(() -> orderService.create(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 상태를 변경할 수 있다")
    public void changeOrderStatus() throws Exception {
        // given
        order.setOrderStatus(OrderStatus.MEAL.name());
        given(orderDao.findById(anyLong())).willReturn(Optional.of(order));
        given(orderDao.save(any(Order.class))).willReturn(order);

        // when
        Order actual = orderService.changeOrderStatus(this.order.getId(), this.order);

        // then
        assertThat(actual).isEqualTo(order);
        assertThat(actual.getOrderStatus()).isEqualTo(OrderStatus.MEAL.name());
    }

    @Test
    @DisplayName("주문이 존재하지 않은 경우 주문 상태를 변경할 수 없다.")
    public void changeOrderStatusFailByUnknownOrder() throws Exception {
        // given
        order.setOrderStatus(OrderStatus.MEAL.name());

        // then
        assertThatThrownBy(() -> orderService.changeOrderStatus(this.order.getId(), this.order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문상태가 완료인 경우 변경할 수 없다.")
    public void changeOrderStatusFailByOrderStatus() throws Exception {
        // given
        order.setOrderStatus(OrderStatus.COMPLETION.name());
        given(orderDao.findById(anyLong())).willReturn(Optional.of(order));

        // then
        assertThatThrownBy(() -> orderService.changeOrderStatus(this.order.getId(), this.order)).isInstanceOf(IllegalArgumentException.class);
    }
}