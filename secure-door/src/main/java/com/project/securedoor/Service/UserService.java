package com.project.securedoor.Service;

import com.project.securedoor.Model.UserModel;
import com.project.securedoor.Model.UserRequestModel;
import com.project.securedoor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    public boolean isNewUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        List<SimpleGrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority()));
//        System.out.println(grantedAuthorities);
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public UserModel save(UserRequestModel user) {
        UserModel newUser = new UserModel();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAuthority(user.getAuthority());
        return userRepository.save(newUser);
    }

    public boolean loadUserByUsernameForCheck(String username) {
        UserModel newUser = userRepository.findByUsername(username);
        if (newUser == null) {
            isNewUser = true;
        } else {
            isNewUser = false;
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return isNewUser;
    }
}