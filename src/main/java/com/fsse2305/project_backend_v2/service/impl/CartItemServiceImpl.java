package com.fsse2305.project_backend_v2.service.impl;

import com.fsse2305.project_backend_v2.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;
import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import com.fsse2305.project_backend_v2.exception.cartItem.CartItemNotFoundException;
import com.fsse2305.project_backend_v2.exception.cartItem.DeleteCartItemException;
import com.fsse2305.project_backend_v2.exception.cartItem.UpdateCartItemException;
import com.fsse2305.project_backend_v2.exception.product.NoStockException;
import com.fsse2305.project_backend_v2.exception.product.ProductNotFoundException;
import com.fsse2305.project_backend_v2.repository.CartItemRepository;
import com.fsse2305.project_backend_v2.service.CartItemService;
import com.fsse2305.project_backend_v2.service.ProductService;
import com.fsse2305.project_backend_v2.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    public CartItemServiceImpl(UserService userService, ProductService productService, CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        try {
            UserEntity userEntity = getUserEntity(firebaseUserData);
            ProductEntity productEntity = productService.getEntityByPid(pid);
            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserAndProduct(userEntity, productEntity);
            if (optionalCartItemEntity.isEmpty()) {
                if (productEntity.getStock() != 0) {
                    cartItemRepository.save(new CartItemEntity(productEntity, userEntity, quantity));
                    return true;
                }
            }
            CartItemEntity cartItemEntity = optionalCartItemEntity.get();
            if (!checkHasStock(productEntity, quantity)) {
                logger.warn("No Stock");
                throw new NoStockException();
            } else if (cartItemEntity.getQuantity() + quantity > productEntity.getStock()) {
                logger.warn("Not Enough Stock");
                throw new NoStockException();
            } else {
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
                return true;
            }


        } catch (ProductNotFoundException ex) {
            logger.warn("Put CartItem Failed: Product Not Found " + pid);
            throw ex;
        }
    }

    @Override
    public List<CartItemDetailData> getUserCartItem(FirebaseUserData firebaseUserData) {

        UserEntity userEntity = getUserEntity(firebaseUserData);
        List<CartItemDetailData> cartItemDetailDataList = new ArrayList<>();
        for (CartItemEntity entity : cartItemRepository.findAllByUser(userEntity)) {
            cartItemDetailDataList.add(new CartItemDetailData(entity));
        }
        return cartItemDetailDataList;
    }

    @Override
    @Transactional
    public CartItemDetailData patchCartItemQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        if (firebaseUserData == null || pid == null || quantity == null || quantity < 0) {
            logger.warn("Update Cart Item Quantity Failed: Data Missing");
            throw new UpdateCartItemException();
        }
        try {
            UserEntity userEntity = getUserEntity(firebaseUserData);
            ProductEntity productEntity = productService.getEntityByPid(pid);
            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserAndProduct(userEntity, productEntity);

            if (optionalCartItemEntity.isEmpty()) {
                logger.warn("Update Cart Item Quantity Failed: Cart Item Not Found");
                throw new CartItemNotFoundException();
            }

            CartItemEntity cartItemEntity = optionalCartItemEntity.get();
            if (quantity == 0) {
                cartItemRepository.deleteById(cartItemEntity.getCid());
                return new CartItemDetailData(cartItemEntity);
            }

            if (!checkHasStock(productEntity, quantity)) {
                logger.warn("Update Cart Item Quantity Failed: No Stock");
                throw new NoStockException();
            }
            cartItemEntity.setQuantity(quantity);
            cartItemRepository.save(cartItemEntity);
            return new CartItemDetailData(cartItemEntity);
        } catch (ProductNotFoundException ex) {
            logger.warn("Update Cart Item Quantity Failed: Product Not Found");
            throw new ProductNotFoundException();
        }
    }

    @Override
    @Transactional
    public boolean deleteCartItemByPid(FirebaseUserData firebaseUserData, Integer pid) {
        if (firebaseUserData == null || pid == null) {
            throw new DeleteCartItemException();
        }
        UserEntity userEntity = getUserEntity(firebaseUserData);
        ProductEntity productEntity = productService.getEntityByPid(pid);
        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserAndProduct(userEntity, productEntity);
        if (optionalCartItemEntity.isEmpty()) {
            logger.warn("Delete Cart Item Failed: Cart Item Not Found");
            throw new CartItemNotFoundException();
        }
        CartItemEntity cartItemEntity = optionalCartItemEntity.get();
        cartItemRepository.deleteById(cartItemEntity.getCid());
        return true;
    }

    @Override
    public List<CartItemEntity> getAllCartItemEntityByFirebaseUser(FirebaseUserData firebaseUserData) {
        return cartItemRepository.findAllByUser(userService.getUserEntityByFirebaseUserData(firebaseUserData));
    }

    @Override
    public boolean emptyCartItemByUser(UserEntity user) {
        cartItemRepository.deleteAllByUserUid(user.getUid());
        return true;
    }


    public boolean checkHasStock(ProductEntity product, Integer quantity) {
        return product.getStock() >= quantity;
    }

    public UserEntity getUserEntity(FirebaseUserData firebaseUserData) {
        return userService.getUserEntityByFirebaseUserData(firebaseUserData);
    }
}
