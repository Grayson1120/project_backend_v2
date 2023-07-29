package com.fsse2305.project_backend_v2.data.user.entity;

import com.fsse2305.project_backend_v2.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    private String email;

    @Column(unique = true)
    private String firebaseUid;

    @OneToMany(mappedBy = "user")
    private List<CartItemEntity> cartItemList;

    public UserEntity(FirebaseUserData data) {
        this.firebaseUid = data.getFirebaseUid();
        this.email = data.getEmail();
    }

    public UserEntity() {
    }

    public List<CartItemEntity> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemEntity> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

}
