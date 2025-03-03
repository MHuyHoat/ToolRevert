package com.revert.bedrive.tool.revert.Service;

import java.util.List;

import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revert.bedrive.tool.revert.Model.UserEntity;
import com.revert.bedrive.tool.revert.Repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Getter
@Setter
public class UserService  {
    @Autowired
    private UserRepository userRepository;
        @Autowired
    private DataSource dataSource;

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }
 
    
}
