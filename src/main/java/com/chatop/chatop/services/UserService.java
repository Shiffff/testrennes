package com.chatop.chatop.services;

import com.chatop.chatop.entity.User;
import com.chatop.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public void register(User user) {
        if(!user.getEmail().contains("@")){
            throw new RuntimeException("Votre email est invalide");
        }
        if(!user.getEmail().contains(".")){
            throw new RuntimeException("Votre email est invalide");
        }
        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new RuntimeException("Votre email est déja utilisé");
        }
        String cryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptedPassword);
        this.userRepository.save(user);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }
}
