package kitchenpos.table.application;

import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.tablegroup.domain.TableGroupEvent;
import kitchenpos.tablegroup.domain.TableUngroupEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * packageName : kitchenpos.table.application
 * fileName : TableGroupEventListener
 * author : haedoang
 * date : 2021-12-27
 * description :
 */
@Component
public class TableEventHandler {
    private final OrderTableRepository orderTableRepository;

    public TableEventHandler(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void groupEvent(TableGroupEvent event) {
        List<OrderTable> orderTables = orderTableRepository.findAllById(event.getTableIds());
        orderTables.forEach(orderTable -> orderTable.groupBy(event.getGroupTableId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void ungroupEvent(TableUngroupEvent event) {
        List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(event.getTableGroupId());
        orderTables.forEach(OrderTable::ungroup);
    }

}
