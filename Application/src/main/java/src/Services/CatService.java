package src.Services;

import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import src.Models.Users;
import src.Repositories.CatRepository;
import src.Models.Cat;
import src.Models.CatColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Repositories.OwnerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CatService {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private UserService userService;

    public Cat findCatByName(String name) {
        var cat = catRepository.findByName(name);

        return cat.orElseThrow(() -> new UsernameNotFoundException("cat with name" + name + "not found"));
    }

    public void updateCatInMainTable(Cat newCat) {
        catRepository.save(newCat);
    }
    public void addCatToMainTable(String catName, String catBreed, LocalDate catBirthday, CatColor catColor) {
        var cat = new Cat();
        cat.setName(catName);
        cat.setBreed(catBreed);
        cat.setBirthDate(catBirthday);
        cat.setColor(catColor);
        catRepository.save(cat);
    }

    public void deleteCatFromMainTable(String catName) {
        var cat = catRepository.findByName(catName);

        if (cat.isPresent()) {
            cat.ifPresent(catRepository::delete);
        }

    }

    @Transactional
    public void addFriendToCat(String catName, String friendName) {
        var cat = catRepository.findByName(catName);
        var friendCat = catRepository.findByName(friendName);

        if (friendCat.isPresent()) {
            var friendCatValue = friendCat.get();
            cat.ifPresent(value -> value.addFriend(friendCatValue));
            cat.ifPresent(catRepository::save);
        }
    }

    @Transactional
    public Collection<Cat> getCatsByColorRelatedToOwner(CatColor catColor, String ownerName) {
        var user = userService.findUserByName(ownerName);
        var owner = ownerRepository.findByName(user.getUsername());
        if (owner.isEmpty()) {
            throw new UsernameNotFoundException("Owner with name " + ownerName + " not found");
        }

        var cat = new Cat();
        cat.setColor(catColor);
        cat.setOwner(owner.get());
        var example = Example.of(cat);

        return catRepository.findAll(example);

    }

    public Collection<Cat> getAllCatsOwnedByOwner(String ownerName) {
        var owner = ownerRepository.findByName(ownerName).orElseThrow(() -> new UsernameNotFoundException("Owner with name " + ownerName + " not found"));
        var cat = new Cat();
        cat.setOwner(owner);
        var example = Example.of(cat);

        return catRepository.findAll(example);
    }

    public Collection<Cat> listCatsFromMainTable() {
        return catRepository.findAll();
    }
}
