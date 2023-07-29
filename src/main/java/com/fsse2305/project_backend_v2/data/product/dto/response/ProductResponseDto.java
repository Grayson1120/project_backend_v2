package com.fsse2305.project_backend_v2.data.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2305.project_backend_v2.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend_v2.data.transactionProduct.domainObject.TransactionProductDetailData;

import java.math.BigDecimal;

public class ProductResponseDto {
    private Integer pid;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private String vol;
    private Integer stock;

    public ProductResponseDto(ProductDetailData data) {
        this.description = data.getDescription();
        this.pid = data.getPid();
        this.name = data.getName();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.vol = data.getVol();
        this.stock = data.getStock();
    }

    public ProductResponseDto(TransactionProductDetailData data){
        this.pid = data.getPid();
        this.stock = data.getStock();
        this.name = data.getName();
        this.price = data.getPrice();
        this.description = data.getDescription();
        this.stock = data.getStock();
        this.imageUrl = data.getImageUrl();
        this.vol = data.getVol();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
