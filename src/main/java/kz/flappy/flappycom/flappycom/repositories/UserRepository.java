package kz.flappy.flappycom.flappycom.repositories;

import kz.flappy.flappycom.flappycom.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
    List<Users> findAllByFullName(String fullName);

}
