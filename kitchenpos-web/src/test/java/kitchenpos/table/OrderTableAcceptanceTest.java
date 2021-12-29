package kitchenpos.table;

import api.menugroup.MenuGroupFixtures;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import api.order.OrderFixtures;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.product.dto.ProductResponse;
import kitchenpos.table.dto.ChangeEmptyRequest;
import kitchenpos.table.dto.ChangeGuestNumberRequest;
import kitchenpos.table.dto.OrderTableResponse;
import kitchenpos.table.dto.OrderTableSaveRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import kitchenpos.AcceptanceTest;
import kitchenpos.menu.MenuAcceptanceTest;
import kitchenpos.menugroup.MenuGroupAcceptanceTest;
import kitchenpos.tablegroup.TableGroupAcceptanceTest;

import java.util.List;

import static api.menu.MenuFixtures.메뉴등록요청;
import static api.menu.MenuProductFixtures.메뉴상품등록요청;
import static api.order.OrderLineItemFixtures.주문정보_등록요청;
import static api.product.ProductFixtures.양념치킨요청;
import static api.table.OrderTableFixtures.*;
import static api.tablegroup.TableGroupFixtures.그룹테이블_그룹요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static kitchenpos.order.OrderAcceptanceTest.*;
import static kitchenpos.product.ProductAcceptanceTest.상품_등록되어_있음;

/**
 * packageName : kitchenpos.acceptance
 * fileName : OrderTableAcceptanceTest
 * author : haedoang
 * date : 2021-12-21
 * description :
 */
@DisplayName("주문테이블 인수테스트")
public class OrderTableAcceptanceTest extends AcceptanceTest {
    private Long 메뉴_ID;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ProductResponse 양념치킨 = 상품_등록되어_있음(양념치킨요청());
        MenuGroupResponse 한마리메뉴그룹 = MenuGroupAcceptanceTest.메뉴그룹_등록되어있음(MenuGroupFixtures.한마리메뉴그룹요청());

        메뉴_ID = MenuAcceptanceTest.메뉴_등록_되어있음(
                메뉴등록요청(
                        "양념치킨하나",
                        양념치킨.getPrice(),
                        한마리메뉴그룹.getId(),
                        Lists.newArrayList(메뉴상품등록요청(양념치킨.getId(), 1L)
                        )
                )
        ).getId();
    }

    @Test
    @DisplayName("주문 테이블을 등록한다.")
    public void create() {
        // when
        final ExtractableResponse<Response> response = 주문_테이블_등록_요청함(주문불가_다섯명테이블요청());

        // then
        주문_테이블_등록됨(response);
    }

    @Test
    @DisplayName("주문 테이블 조회 요청함")
    public void list() {
        // given
        주문_테이블_등록되어_있음(주문불가_다섯명테이블요청());
        주문_테이블_등록되어_있음(주문가능_두명테이블요청());

        // when
        final ExtractableResponse<Response> response = 주문_테이블_조회_요청함();

        // then
        주문_테이블_조회됨(response);
    }


    @Test
    @DisplayName("주문 테이블의 상태를 변경한다.")
    public void changeEmpty() {
        // given
        final OrderTableResponse savedTable = 주문_테이블_등록되어_있음(주문불가_다섯명테이블요청());

        // when
        final ExtractableResponse<Response> response1 = 주문_테이블_상태_변경_요청(savedTable.getId(), 주문가능으로_변경요청());

        // then
        주문상태_변경됨(response1, 주문가능으로_변경요청().isEmpty());

        // when
        final ExtractableResponse<Response> response2 = 주문_테이블_상태_변경_요청(savedTable.getId(), 주문불가로_변경요청());

        // then
        주문상태_변경됨(response2, 주문불가로_변경요청().isEmpty());
    }


    @Test
    @DisplayName("주문 테이블이 존재하지 않는 경우 상태를 변경할 수 없다.")
    public void changeEmptyFail() {
        // when
        final ExtractableResponse<Response> response = 주문_테이블_상태_변경_요청(1L, 주문가능으로_변경요청());

        // then
        주문_상태_변경_실패함(response);
    }

    @Test
    @DisplayName("주문 테이블이 그룹테이블인 경우 상태를 변경할 수 없다.")
    public void changeEmptyFailByGroupTable() {
        // given
        OrderTableResponse savedTable1 = 주문_테이블_등록되어_있음(주문불가_두명테이블요청());
        OrderTableResponse savedTable2 = 주문_테이블_등록되어_있음(주문불가_두명테이블요청());
        TableGroupAcceptanceTest.테이블_그룹화_되어있음(그룹테이블_그룹요청(newArrayList(테이블_그룹요청(savedTable1.getId()), 테이블_그룹요청(savedTable2.getId()))));

        // when
        ExtractableResponse<Response> response = 주문_테이블_상태_변경_요청(savedTable1.getId(), 주문가능으로_변경요청());

        // then
        주문_상태_변경_실패함(response);
    }

    @Test
    @DisplayName("주문 테이블의 상태가 주문 완료가 아닌 경우 상태를 변경할 수 없다.")
    public void changeEmptyFailByOrderStatus() {
        // given
        OrderTableResponse savedTable1 = 주문_테이블_등록되어_있음(주문불가_두명테이블요청());
        OrderTableResponse savedTable2 = 주문_테이블_등록되어_있음(주문불가_두명테이블요청());

        TableGroupAcceptanceTest.테이블_그룹화_되어있음(그룹테이블_그룹요청(newArrayList(테이블_그룹요청(savedTable1.getId()), 테이블_그룹요청(savedTable2.getId()))));

        OrderResponse savedOrder = 주문등록되어있음(OrderFixtures.주문등록요청(savedTable1.getId(), Lists.newArrayList(주문정보_등록요청(메뉴_ID, 1L))));
        주문_상태_변경요청함(savedOrder.getId(), 식사중으로_변경_요청());

        // when
        ExtractableResponse<Response> response = 주문_테이블_상태_변경_요청(savedTable1.getId(), 주문가능으로_변경요청());

        // then
        주문_상태_변경_실패함(response);
    }

    @Test
    @DisplayName("주문 테이블의 사용자 수를 변경할 수 있다.")
    public void changeNumberOfGuests() {
        // given
        final OrderTableResponse savedTable = 주문_테이블_등록되어_있음(주문가능_다섯명테이블요청());

        // when
        final ExtractableResponse<Response> response = 테이블_사용자수_변경_요청함(savedTable.getId(), 두명으로_변경요청());

        // then
        주문_테이블_사용자수_변경됨(response, 두명으로_변경요청());

        // when
        final ExtractableResponse<Response> response2 = 테이블_사용자수_변경_요청함(savedTable.getId(), 다섯명으로_변경요청());

        // then
        주문_테이블_사용자수_변경됨(response2, 다섯명으로_변경요청());
    }

    @Test
    @DisplayName("빈 테이블은 사용자 수를 변경할 수 없다.")
    public void changeNumberOfGuestsFail() {
        // given
        final OrderTableResponse savedTable = 주문_테이블_등록되어_있음(주문불가_다섯명테이블요청());

        // when
        final ExtractableResponse<Response> response = 테이블_사용자수_변경_요청함(savedTable.getId(), 두명으로_변경요청());

        // then
        사용자수_변경_실패함(response);
    }

    @Test
    @DisplayName("존재하지 않은 테이블의 사용자 수를 변경할 수 없다.")
    public void changeNumberOfGuestsFailByUnknownTable() {
        // when
        final ExtractableResponse<Response> response = 테이블_사용자수_변경_요청함(1L, 두명으로_변경요청());

        // then
        사용자수_변경_실패함(response);
    }

    private ExtractableResponse<Response> 테이블_사용자수_변경_요청함(Long id, ChangeGuestNumberRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/tables/{id}/number-of-guests", id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_테이블_상태_변경_요청(Long savedId, ChangeEmptyRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/tables/{id}/empty", savedId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 주문_테이블_조회_요청함() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/tables")
                .then().log().all()
                .extract();
    }

    public static OrderTableResponse 주문_테이블_등록되어_있음(OrderTableSaveRequest request) {
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/tables")
                .then().log().all()
                .extract();

        return response.jsonPath().getObject("", OrderTableResponse.class);
    }

    private ExtractableResponse<Response> 주문_테이블_등록_요청함(OrderTableSaveRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/tables")
                .then().log().all()
                .extract();
    }

    private void 주문_테이블_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final List<OrderTableResponse> orderTableResponses = response.jsonPath().getList("", OrderTableResponse.class);
        assertThat(orderTableResponses).hasSize(2);
    }

    private void 사용자수_변경_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 주문_테이블_사용자수_변경됨(ExtractableResponse<Response> response, ChangeGuestNumberRequest request) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final OrderTableResponse orderTableResponse = response.jsonPath().getObject("", OrderTableResponse.class);
        assertThat(orderTableResponse.getNumberOfGuests()).isEqualTo(request.getNumberOfGuests());
    }

    private void 주문_상태_변경_실패함(ExtractableResponse<Response> actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 주문상태_변경됨(ExtractableResponse<Response> response, boolean isEmpty) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final OrderTableResponse orderTableResponse = response.jsonPath().getObject("", OrderTableResponse.class);
        assertThat(orderTableResponse.isEmpty()).isEqualTo(isEmpty);
    }

    private void 주문_테이블_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }
}
