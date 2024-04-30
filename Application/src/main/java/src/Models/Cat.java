package src.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cats_main_info")
//TODO: роблема с N+1 запросами в JPA и HIbernate хабр
public class Cat implements BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id", nullable = false)
    private Integer id;
    @Column(name = "cat_name", unique = true)
    private String name;
    @Column(name = "cat_birthday", nullable = false)
    private LocalDate birthDate;
    @Column(name = "cat_breed", nullable = false)
    private String breed;
    @Column(name = "cat_color" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private CatColor color;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cats_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<Cat> friends = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "owners_with_cats",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Owner owner;

    public void addFriend(Cat cat) {
        friends.add(cat);
        cat.getFriends().add(this);
    }

    public void addOwner(Owner newOwner) {
        this.owner = newOwner;
    }

    public void removeFriend(Cat cat) {
        friends.remove(cat);
        cat.getFriends().remove(this);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
