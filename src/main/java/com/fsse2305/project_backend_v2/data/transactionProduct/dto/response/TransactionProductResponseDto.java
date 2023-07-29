package com.fsse2305.project_backend_v2.data.transactionProduct.dto.response;

import com.fsse2305.project_backend_v2.data.product.dto.response.ProductResponseDto;
import com.fsse2305.project_backend_v2.data.transactionProduct.domainObject.TransactionProductDetailData;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    private Integer tpid;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal subtotal;

    public TransactionProductResponseDto(TransactionProductDetailData data) {
        this.tpid = data.getTpid();
        this.product = new ProductResponseDto(data);
        this.quantity = data.getQuantity();
        this.subtotal = data.getSubtotal();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
