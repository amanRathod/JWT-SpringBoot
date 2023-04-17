package com.jwt.security.demo;

import com.jwt.security.exception.UserNotFoundException;
import com.jwt.security.user.User;
import com.jwt.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(int Id) throws UserNotFoundException {
        User user = repository.findById(Id);
        if(user!=null){
            return user;
        }else{
            throw new UserNotFoundException("user not found with id : "+ Id);
        }
    }
}
