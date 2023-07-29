package com.fsse2305.project_backend_v2.service.impl;

import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.transaction.TransactionStatus;
import com.fsse2305.project_backend_v2.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import com.fsse2305.project_backend_v2.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import com.fsse2305.project_backend_v2.exception.transaction.TransactionNotFoundException;
import com.fsse2305.project_backend_v2.exception.transaction.TransactionNotMatchUidException;
import com.fsse2305.project_backend_v2.exception.transaction.TransactionStatusException;
import com.fsse2305.project_backend_v2.repository.TransactionRepository;
import com.fsse2305.project_backend_v2.service.*;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final CartItemService cartItemService;
    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final TransactionProductService transactionProductService;
    private final ProductService productService;

    @Autowired
    public TransactionServiceImpl(CartItemService cartItemService, UserService userService, TransactionRepository transactionRepository, TransactionProductService transactionProductService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.productService = productService;
    }

    @Override
    public TransactionDetailData createTransaction(FirebaseUserData firebaseUserData) {
        UserEntity userEntity = userService.getUserEntityByFirebaseUserData(firebaseUserData);
        List<CartItemEntity> cartItemList = cartItemService.getAllCartItemEntityByFirebaseUser(firebaseUserData);
        TransactionEntity transactionEntity = new TransactionEntity(userEntity);
        transactionEntity = transactionRepository.save(transactionEntity);
        List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();

        for (CartItemEntity cartItemEntity : cartItemList) {
            TransactionProductEntity transactionProductEntity = transactionProductService.createTransactionProductEntity(cartItemEntity, transactionEntity);
            transactionEntity.setTotal(transactionEntity.getTotal().add(transactionProductEntity.getSubTotal()));
            transactionProductEntityList.add(transactionProductEntity);
        }
        transactionEntity.setTransactionProductEntityList(transactionProductEntityList);

        return new TransactionDetailData(transactionEntity);
    }

    @Override
    public TransactionDetailData findTransactionByTid(FirebaseUserData firebaseUserData, Integer tid) {
        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(tid);
        if (optionalTransactionEntity.isEmpty()) {
            logger.warn("Find Transaction By Tid Failed: " + tid);
            throw new TransactionNotFoundException();
        }
        TransactionDetailData transactionDetailData = new TransactionDetailData(optionalTransactionEntity.get());
        UserEntity userEntity = userService.getUserEntityByFirebaseUserData(firebaseUserData);
        if (!transactionDetailData.getUser().getUid().equals(userEntity.getUid())) {
            logger.warn("Find Transaction By Tid Failed: Not Matched User Id/Transaction Id");
            throw new TransactionNotMatchUidException();
        }
        return transactionDetailData;
    }


    @Transactional
    @Override
    public Boolean updateTransactionStatus(FirebaseUserData firebaseUserData, Integer tid) {
        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(tid);
        if (optionalTransactionEntity.isEmpty()) {
            logger.warn("Update Transaction Status Failed : Transaction Not Found " + tid);
            throw new TransactionNotFoundException();
        }
        TransactionEntity transactionEntity = optionalTransactionEntity.get();

        if (!transactionEntity.getUser().getUid().equals(userService.getUserEntityByFirebaseUserData(firebaseUserData).getUid())) {
            logger.warn("Update Transaction By Tid Failed: Not Matched User Id/Transaction Id");
            throw new TransactionNotMatchUidException();
        }
        if (transactionEntity.getStatus() != TransactionStatus.PREPARE) {
            logger.warn("Transaction Status is not PREPARE");
            throw new TransactionStatusException();
        }

        for (TransactionProductEntity transactionProductEntity : transactionProductService.findTransactionProductByTid(transactionEntity.getTid())){
            productService.setProductStock(transactionProductEntity.getPid(), transactionProductEntity.getQuantity());
        }

        transactionEntity.setStatus(TransactionStatus.PROCESSING);
        return true;
    }

    @Transactional
    @Override
    public TransactionDetailData finishTransactionStatus(FirebaseUserData firebaseUserData, Integer tid) {
        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(tid);
        if (optionalTransactionEntity.isEmpty()) {
            logger.warn("Finish Transaction Status Failed : Transaction Not Found " + tid);
            throw new TransactionNotFoundException();
        }
        TransactionEntity transactionEntity = optionalTransactionEntity.get();
        if (!transactionEntity.getUser().getUid().equals(userService.getUserEntityByFirebaseUserData(firebaseUserData).getUid())) {
            logger.warn("Finish Transaction By Tid Failed: Not Matched User Id/Transaction Id");
            throw new TransactionNotMatchUidException();
        }
        if (transactionEntity.getStatus() != TransactionStatus.PROCESSING) {
            logger.warn("Transaction Status is not PROCESSING");
            throw new TransactionStatusException();
        }

        cartItemService.emptyCartItemByUser(userService.getUserEntityByFirebaseUserData(firebaseUserData));

        transactionEntity.setStatus(TransactionStatus.SUCCESS);
        return new TransactionDetailData(transactionEntity);
    }
}