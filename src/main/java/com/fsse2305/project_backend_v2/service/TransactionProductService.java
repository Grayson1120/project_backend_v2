package com.fsse2305.project_backend_v2.service;

import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import com.fsse2305.project_backend_v2.data.transactionProduct.entity.TransactionProductEntity;

import java.util.List;

public interface TransactionProductService {
    TransactionProductEntity createTransactionProductEntity(CartItemEntity cartItem, TransactionEntity transaction);

    List<TransactionProductEntity> findTransactionProductByTid(Integer tid);
}
