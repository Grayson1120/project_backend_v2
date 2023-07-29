package com.fsse2305.project_backend_v2.data.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2305.project_backend_v2.data.transaction.TransactionStatus;
import com.fsse2305.project_backend_v2.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.project_backend_v2.data.transactionProduct.domainObject.TransactionProductDetailData;
import com.fsse2305.project_backend_v2.data.transactionProduct.dto.response.TransactionProductResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer uid;
    private TransactionStatus status;
    @JsonFormat(pattern="yyyyMMdd'T'HH:mm:ss")
    private LocalDateTime datetime;
    private BigDecimal total;
    @JsonProperty("items")
    private List<TransactionProductResponseDto> transactionProductResponseDtoList = new ArrayList<>();

    public TransactionResponseDto(TransactionDetailData data) {
        this.tid = data.getTid();
        this.uid = data.getUser().getUid();
        this.status = data.getTransactionStatus();
        this.datetime = data.getDatetime();
        this.total = data.getTotal();

        setTransactionProductResponseDtoList(data);
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
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

    public List<TransactionProductResponseDto> getTransactionProductResponseDtoList() {
        return transactionProductResponseDtoList;
    }

    public void setTransactionProductResponseDtoList(List<TransactionProductResponseDto> transactionProductResponseDtoList) {
        this.transactionProductResponseDtoList = transactionProductResponseDtoList;
    }

    public void setTransactionProductResponseDtoList(TransactionDetailData data) {
        for (TransactionProductDetailData transactionProductDetailData : data.getTransactionProductDetailDataList()) {
            transactionProductResponseDtoList.add(new TransactionProductResponseDto(transactionProductDetailData));
        }
    }
}
