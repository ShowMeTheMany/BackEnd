package com.example.showmethemany.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RequiredArgsConstructor
@Component
public class TransactionUtil {
    private final PlatformTransactionManager transactionManager;

    public TransactionStatus startTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("testTransaction"); // 트랜잭션 이름 설정
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 트랜잭션 전파 속성 설정
        TransactionStatus status = transactionManager.getTransaction(def); // 트랜잭션 시작

        return status;
    }

    public void transactionCommit(TransactionStatus transactionStatus) {
        transactionManager.commit(transactionStatus); // 트랜잭션 커밋
    }

    public void transactionRollback(TransactionStatus transactionStatus) {
        transactionManager.rollback(transactionStatus); // 트랜잭션 롤백
    }
}
