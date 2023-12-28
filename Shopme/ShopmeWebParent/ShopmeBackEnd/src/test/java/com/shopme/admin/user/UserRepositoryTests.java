package com.shopme.admin.user;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = testEntityManager.find(Role.class,1);
        User user = new User("rahulbarik589@gmail.com","Rahul123","Rahul","Barik");
        user.addRole(roleAdmin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateNewUserWithMultipleRole() {

        User user = new User("golupandu583@gmail.com","Reena123","Reena","Barik");
        Role roleEditor = roleRepository.getReferenceById(3L);
        Role roleAssistant = roleRepository.getReferenceById(5L);
        user.addRole(roleEditor);
        user.addRole(roleAssistant);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUser(){
        List<User> users =  userRepository.findAll();
        users.forEach((user)->{
            System.out.println(user);
        });
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void getUserById(){
        if(userRepository.findById(5).isPresent()){
            User user = userRepository.findById(5).get();
            System.out.println(user);
        }else{
            System.out.println("User not found");
        }


    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(9).get();
        user.setEnabled(true);
        user.setEmail("golupanducute@gmail.com");
        userRepository.save(user);

    }

    @Test
    public void testUpdateUserRole(){
        User userReena = userRepository.findById(9).get();
        Role role = roleRepository.findById(5L).get();
        Role salesRole = roleRepository.findById(2L).get();
        userReena.getRoles().remove(role);
        userReena.addRole(salesRole);
        userRepository.save(userReena);
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(8);
    }

//    @Test
//    public void checkDuplicateMail(){
//        String mail = "rahulbarik58376@gmail.com";
//
//        User user = userRepository.findByEmail(mail);
//
//        if(user==null){
//            System.out.println("User having this email does not exist");
//        }else{
//            System.out.println("Duplicate Email user with this email already exists");
//        }
//
//    }

    @Test
    public void checkDuplicateMail(){
        String mail = "rahulbarik583@gmail.com";

        User user = userRepository.getUserByEmail(mail);

        if(user==null){
            System.out.println("User having this email does not exist");
        }else{
            System.out.println("Duplicate Email user with this email already exists");
        }

    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<User> userPage = userRepository.findAll(pageable);

        List<User> content = userPage.getContent();

        for(User user:content){
            System.out.println(user.toString());
        }
    }

    @Test
    public void testSearching(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<User> userPage = userRepository.findAll("Barik",pageable);

        List<User> content = userPage.getContent();

        for(User user:content){
            System.out.println(user.toString());
        }


    }



}
