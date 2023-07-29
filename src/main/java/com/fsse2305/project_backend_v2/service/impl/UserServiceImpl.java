package com.fsse2305.project_backend_v2.service.impl;

import com.fsse2305.project_backend_v2.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend_v2.data.user.entity.UserEntity;
import com.fsse2305.project_backend_v2.repository.UserRepository;
import com.fsse2305.project_backend_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUserEntityByFirebaseUserData(FirebaseUserData firebaseUserData) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
        if (optionalUserEntity.isEmpty()) {
            return userRepository.save(new UserEntity(firebaseUserData));
        }
        return optionalUserEntity.get();
    }
}
