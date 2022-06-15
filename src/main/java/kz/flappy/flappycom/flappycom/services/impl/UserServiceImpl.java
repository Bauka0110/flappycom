package kz.flappy.flappycom.flappycom.services.impl;

import kz.flappy.flappycom.flappycom.entities.Users;
import kz.flappy.flappycom.flappycom.repositories.UserRepository;
import kz.flappy.flappycom.flappycom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);

        if(user!=null) return user;
        else throw new UsernameNotFoundException("USER NOT FOUND");
    }

    @Override
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users getUser(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Users addUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Users> getAllByFullName(String fullName) {
        return userRepository.findAllByFullName(fullName);
    }
}
