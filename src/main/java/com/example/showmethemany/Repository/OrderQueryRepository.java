package com.example.showmethemany.Repository;
import com.example.showmethemany.domain.Orders;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.LockModeType;
import java.util.List;
import static com.example.showmethemany.domain.QOrders.orders;

@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public OrderQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Orders> findOrderByOrderNumNoneLock(String orderNum) {
        return jpaQueryFactory.select(orders)
                .from(orders)
                .join(orders.products).fetchJoin()
                .where(orders.orderNum.eq(orderNum))
                .fetch();
    }

    public List<Orders> findOrderByOrderNumLock(String orderNum) {
        return jpaQueryFactory.select(orders)
                .from(orders)
                .join(orders.products).fetchJoin()
                .where(orders.orderNum.eq(orderNum))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetch();
    }

    public Orders findOrderById(Long orderId) {
        return jpaQueryFactory.select(orders)
                .from(orders)
                .join(orders.products).fetchJoin()
                .where(orders.id.eq(orderId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();
    }
}
