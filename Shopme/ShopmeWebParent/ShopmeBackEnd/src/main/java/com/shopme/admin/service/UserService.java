package com.shopme.admin.service;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {

    private final   static Logger logger = LoggerFactory.getLogger(UserService.class);

    public static final int USERS_PER_PAGE = 4;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<Role> getAllRole() { return roleRepository.findAll();}

    public User saveUser(User user) {
        logger.info("Calling the save method: "+ user.toString());
        boolean updateUser = Objects.nonNull(user.getId());
        logger.info("Check the boolean value: "+updateUser+"password is null or not: "+Objects.nonNull(user.getPassword()));
        if(updateUser){
            User exitingUser =  userRepository.findById(user.getId()).get();
            if(Objects.isNull(user.getPassword())){
                user.setPassword(exitingUser.getPassword());
            }else{
                encodePassWord(user);
            }
        }else{
            encodePassWord(user);
        }


        return userRepository.save(user);
    }

    private void encodePassWord(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public boolean isEmailUnique(Integer id,String email){
        User user = userRepository.getUserByEmail(email);

        if(Objects.isNull(user)){
            return true;
        }

        if(Objects.nonNull(id) && userRepository.findById(id).isPresent()){
            User savedUser = userRepository.findById(id).get();
            return savedUser.getEmail().equals(email);
        }


        return false;
    }


    public User get(Integer id) throws UserNotFoundException {
        //Logic for checking and retrieving the user from user repository
        try{
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new UserNotFoundException("Could not find any user with ID "+id);
        }

    }

    public void deleteUser(Integer id)  throws UserNotFoundException{
        try{
            User user = userRepository.findById(id).get();
            userRepository.deleteById(id);
        } catch (NoSuchElementException ex){
            throw  new UserNotFoundException("Could not find the user for Delete with ID: "+id);
        }
    }

    public User userEnable(Integer id) throws  UserNotFoundException{
        try {
            User user = userRepository.findById(id).get();
            user.setEnabled(true);
            return userRepository.save(user);
        }catch (Exception ex){
            throw new UserNotFoundException("User not found with a id: "+id);
        }
    }

    public User userDisable(Integer id) throws  UserNotFoundException{
        try {
            User user = userRepository.findById(id).get();
            user.setEnabled(false);
            return userRepository.save(user);
        }catch (Exception ex){
            throw new UserNotFoundException("User not found with a id: "+id);
        }
    }



    public Page<User> listByPage(int pageNum,String sortField,String sortDir,String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum-1,USERS_PER_PAGE,sort);
        if(Objects.nonNull(keyword)){
            return userRepository.findAll(keyword,pageable);
        }
        return userRepository.findAll(pageable);
    }


}
