package src.Services;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import src.Models.Role;
import src.Models.Users;
import src.Models.UsersDetails;
import src.Repositories.UserRepository;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userValue = userRepository.findByUsername(username);

        return userValue.map(UsersDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }

    @Transactional
    public void addAdminIfNotExists(String username, String password) {
        var admin = userRepository.findByUsername(username);
        if (admin.isEmpty()) {
            addNewUser(username, password, Role.ADMIN);
        }
    }

    public void addNewUser(String username, String password, Role role) {
        var passwordEncoder = new BCryptPasswordEncoder();
        var encodedPassword = passwordEncoder.encode(password);
        var user = new Users(username, encodedPassword, role);
        userRepository.save(user);
    }

    public Users findUserByName(String username) {
        var userValue = userRepository.findByUsername(username);

        return userValue.orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
