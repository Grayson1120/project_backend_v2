package com.fsse2305.project_backend_v2.service;

import com.fsse2305.project_backend_v2.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;

public interface TransactionService {
    TransactionDetailData createTransaction(FirebaseUserData firebaseUserData);

    TransactionDetailData findTransactionByTid(FirebaseUserData firebaseUserData, Integer tid);

    Boolean updateTransactionStatus(FirebaseUserData firebaseUserData, Integer tid);

    TransactionDetailData finishTransactionStatus(FirebaseUserData firebaseUserData, Integer tid);
}
