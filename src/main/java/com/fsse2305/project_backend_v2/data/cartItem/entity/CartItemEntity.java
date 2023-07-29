package com.fsse2305.project_backend_v2.data.cartItem.entity;

import com.fsse2305.project_backend_v2.data.product.entity.ProductEntity;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer quantity;

    public CartItemEntity(ProductEntity product, UserEntity user, Integer quantity) {
        this.quantity = quantity;
        this.product = product;
        this.user = user;
    }

    public CartItemEntity() {
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity pid) {
        this.product = pid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity uid) {
        this.user = uid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
