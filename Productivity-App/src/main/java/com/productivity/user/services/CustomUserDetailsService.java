package com.productivity.user.services;

import com.productivity.user.repositories.UserRepository;
import com.productivity.user.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Replace this with your user repository or data source
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Replace this with the actual user data retrieval
         Optional<User> user = this.userRepository.findByEmail(email);
         if (user.isEmpty()) {
             throw new UsernameNotFoundException("User not found with email: " + email);
         }
         return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                 user.get().getPassword(), new ArrayList<>());

    }

}
