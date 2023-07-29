package com.fsse2305.project_backend_v2.repository;

import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import com.fsse2305.project_backend_v2.data.transactionProduct.entity.TransactionProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionProductRepository extends CrudRepository<TransactionProductEntity, Integer> {
    @Query(value = "SELECT * FROM transaction_product WHERE tid =:tid",nativeQuery = true)
    List<TransactionProductEntity> findAllByTid(Integer tid);
}
