package kz.flappy.flappycom.flappycom.services;

import kz.flappy.flappycom.flappycom.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Users updateUser(Users user);
    Users getUser(String email);
    Users addUser(Users user);
    List<Users> getAllUsers();
    Users getUserById(Long id);
    void deleteUser(Long id);
    List<Users> getAllByFullName(String fullName);
}
