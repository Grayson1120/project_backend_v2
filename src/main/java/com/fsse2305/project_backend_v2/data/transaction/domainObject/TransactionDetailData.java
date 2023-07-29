package com.fsse2305.project_backend_v2.data.transaction.domainObject;


import com.fsse2305.project_backend_v2.data.transaction.TransactionStatus;
import com.fsse2305.project_backend_v2.data.transaction.entity.TransactionEntity;
import com.fsse2305.project_backend_v2.data.transactionProduct.domainObject.TransactionProductDetailData;
import com.fsse2305.project_backend_v2.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.project_backend_v2.data.user.domainObject.UserDetailData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailData {
    private Integer tid;
    private UserDetailData user;
    private TransactionStatus transactionStatus;
    private LocalDateTime datetime;
    private BigDecimal total;
    private List<TransactionProductDetailData> transactionProductDetailDataList = new ArrayList<>();

    public TransactionDetailData(TransactionEntity entity) {
        this.tid = entity.getTid();
        this.user = new UserDetailData(entity.getUser());
        this.transactionStatus = entity.getStatus();
        this.datetime = entity.getDateTime();
        this.total = entity.getTotal();
        setTransactionProductDetailDataList(entity);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserDetailData getUser() {
        return user;
    }

    public void setUser(UserDetailData user) {
        this.user = user;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductDetailData> getTransactionProductDetailDataList() {
        return transactionProductDetailDataList;
    }

    public void setTransactionProductDetailDataList(List<TransactionProductDetailData> transactionProductDetailDataList) {
        this.transactionProductDetailDataList = transactionProductDetailDataList;
    }

    public void setTransactionProductDetailDataList(TransactionEntity entity) {
        for (TransactionProductEntity transactionProductEntity : entity.getTransactionProductEntityList()) {
            transactionProductDetailDataList.add(new TransactionProductDetailData(transactionProductEntity));
        }
    }
}
