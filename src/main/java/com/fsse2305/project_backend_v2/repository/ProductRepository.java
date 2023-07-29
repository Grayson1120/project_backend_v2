package com.fsse2305.project_backend_v2.repository;

import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    @Query(value = "SELECT * FROM product WHERE product.pid =?1", nativeQuery = true)
    Optional<ProductEntity> findByPid(Integer pid);
}
