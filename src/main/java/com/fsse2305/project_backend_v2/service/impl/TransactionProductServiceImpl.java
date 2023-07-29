package com.fsse2305.project_backend_v2.service.impl;

import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import com.fsse2305.project_backend_v2.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.project_backend_v2.repository.TransactionProductRepository;
import com.fsse2305.project_backend_v2.service.TransactionProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {
    private final Logger logger = LoggerFactory.getLogger(TransactionProductServiceImpl.class);
    private final TransactionProductRepository transactionProductRepository;

    @Autowired
    public TransactionProductServiceImpl(TransactionProductRepository transactionProductRepository) {
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public TransactionProductEntity createTransactionProductEntity(CartItemEntity cartItem, TransactionEntity transaction) {
        TransactionProductEntity transactionProductEntity = new TransactionProductEntity(cartItem, transaction);
        return transactionProductRepository.save(transactionProductEntity);
    }

    @Override
    public List<TransactionProductEntity> findTransactionProductByTid(Integer tid) {
        return transactionProductRepository.findAllByTid(tid);
    }
}
