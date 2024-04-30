package src.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @Column(name = "owner_name", nullable = false, unique = true)
    private String name;
    @Column(name = "owner_birthday", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cat> cats = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    public Owner(Integer id, String name, LocalDate birthDate, Set<Cat> cats, Users user) {
        this.id = id;
        this.users = user;
        this.name = name;
        this.birthDate = birthDate;
        this.cats = cats;
    }
}
