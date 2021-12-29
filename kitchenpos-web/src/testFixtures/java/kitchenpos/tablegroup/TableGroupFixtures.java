package kitchenpos.tablegroup;

import kitchenpos.table.OrderTableFixtures;
import kitchenpos.table.dto.OrderTableRequest;
import kitchenpos.tablegroup.dto.TableGroupSaveRequest;

import java.util.Arrays;
import java.util.List;

/**
 * packageName : kitchenpos.fixtures
 * fileName : TableGroupFixtures
 * author : haedoang
 * date : 2021/12/17
 * description :
 */
public class TableGroupFixtures {
    public static TableGroupSaveRequest 그룹테이블_그룹요청() {
        return TableGroupSaveRequest.of(Arrays.asList(OrderTableFixtures.테이블_그룹요청(), OrderTableFixtures.테이블_그룹요청()));
    }

    public static TableGroupSaveRequest 그룹테이블_그룹요청(List<OrderTableRequest> orderTableRequests) {
        return TableGroupSaveRequest.of(orderTableRequests);
    }

    public static TableGroupSaveRequest 그룹테이블_그룹요청_예외_테이블한개() {
        return TableGroupSaveRequest.of(Arrays.asList(OrderTableFixtures.테이블_그룹요청()));
    }
}
