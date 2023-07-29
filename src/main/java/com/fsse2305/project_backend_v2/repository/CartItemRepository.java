package com.fsse2305.project_backend_v2.repository;

import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByUserAndProduct(UserEntity user, ProductEntity product);

    List<CartItemEntity> findAllByUser (UserEntity user);

    int deleteAllByUserUid (Integer uid);


}
