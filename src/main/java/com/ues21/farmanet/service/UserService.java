package com.ues21.farmanet.service;

import com.ues21.farmanet.model.UserEntity;
import com.ues21.farmanet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findbyUserName(String userName){
        return userRepository.getUserByUsername(userName);
    }

    public List<UserEntity> getApprovers(){
        return userRepository.getApprovers();
    }
}
