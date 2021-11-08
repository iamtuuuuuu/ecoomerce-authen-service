package com.tuhungkien.ecommerce.authenticationservice.service;

import com.tuhungkien.ecommerce.authenticationservice.entity.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface AuthDataService {

    UserInfo findByUsername(String username);

    UserInfo findByEmail(String email);

    void deleteByUsername(String username) throws NoSuchAlgorithmException;

    void createUserProfile(UserInfo userInfo) throws NoSuchAlgorithmException;
}
