package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public String createUser(UserDto userDto){

        User user = new User();
        user.setName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
        return "User Registration Successful";
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByName(userName);
    }

    public boolean isPasswordMatch(UserDto userDto){
        User user = userRepository.getByName(userDto.getUserName());
        if(userDto.getPassword().equals(user.getPassword())){
            return true;
        }else{
            return false;
        }
    }
}
