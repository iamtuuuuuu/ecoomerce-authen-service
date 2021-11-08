package com.tuhungkien.ecommerce.authenticationservice.service;

import com.tuhungkien.ecommerce.authenticationservice.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthDataServiceImpl implements AuthDataService {

    @Autowired
    private com.tuhungkien.ecommerce.authenticationservice.dao.UserInfoRepository UserInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserInfo findByUsername(String username) {
        Optional<UserInfo> result = UserInfoRepository.findByUsername(username);

        UserInfo userInfo = null;

        if(result.isPresent()) {
            userInfo = result.get();
        }

        return userInfo;
    }

    public UserInfo findByEmail(String email) {
        Optional<UserInfo> result = UserInfoRepository.findByEmail(email);

        UserInfo userInfo = null;

        if(result.isPresent()) {
            userInfo = result.get();
        }

        return userInfo;
    }

    public void createUserProfile(UserInfo userInfo) throws NoSuchAlgorithmException {
        UserInfoRepository.createUserProfile(userInfo.getUsername(),
                userInfo.getFirstName(), userInfo.getLastName(),
                userInfo.getEmail(), passwordEncoder.encode(userInfo.getPassword())
        );
    }

    public void deleteByUsername(String username) throws NoSuchAlgorithmException {
        UserInfoRepository.deleteByUsername(username);
    }
}
