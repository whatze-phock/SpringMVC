package com.example.SpringMVC.Repository;

import com.example.SpringMVC.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
        public User findByUsername (String s);
        public User findByConfirmationCode(String s);
}
