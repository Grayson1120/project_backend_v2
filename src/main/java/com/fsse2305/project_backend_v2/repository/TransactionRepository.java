package com.fsse2305.project_backend_v2.repository;

import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    @Query(value = "SELECT * FROM Fsse2305_Final_Project.transaction WHERE transaction.buyer_uid =?1", nativeQuery = true)
    Optional<TransactionEntity> findByUserId(Integer uid);

}
