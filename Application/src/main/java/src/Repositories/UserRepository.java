package src.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.Models.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
