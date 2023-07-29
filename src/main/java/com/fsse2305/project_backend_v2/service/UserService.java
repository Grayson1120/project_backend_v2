package com.fsse2305.project_backend_v2.service;

import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;

public interface UserService {
    UserEntity getUserEntityByFirebaseUserData(FirebaseUserData firebaseUserData);
}
