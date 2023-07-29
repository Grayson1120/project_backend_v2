package com.fsse2305.project_backend_v2.service;

import com.fsse2305.project_backend_v2.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartItemService {

    boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    List<CartItemDetailData> getUserCartItem(FirebaseUserData firebaseUserData);

    @Transactional
    CartItemDetailData patchCartItemQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    @Transactional
    boolean deleteCartItemByPid(FirebaseUserData firebaseUserData, Integer pid);

    List<CartItemEntity> getAllCartItemEntityByFirebaseUser(FirebaseUserData firebaseUserData);

    boolean emptyCartItemByUser(UserEntity user);
}
