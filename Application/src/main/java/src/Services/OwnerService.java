package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.Models.*;
import src.Repositories.CatRepository;
import src.Repositories.OwnerRepository;
import src.Repositories.UserRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private UserRepository userRepository;

    public void addOwnerWithoutCats(String ownerName, LocalDate ownerBirthday) {
        var existingUser = userRepository.findByUsername(ownerName).orElseThrow(() -> new IllegalArgumentException("User with username " + ownerName + " not found"));
        var newOwner = new Owner(existingUser.getId(), ownerName, ownerBirthday, new HashSet<>(), existingUser);
        ownerRepository.save(newOwner);
    }

    public Owner findOwnerByName(String name) {
        var owner = ownerRepository.findByName(name);

        return owner.orElse(null);

    }

    public void updateOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Transactional
    public Cat addNewCatAssociatedWithOwner(String ownerName, String catName, String breed, LocalDate birthDate, CatColor color) {
        Cat cat = new Cat();
        cat.setName(catName);
        cat.setBreed(breed);
        cat.setBirthDate(birthDate);
        cat.setColor(color);
        catRepository.save(cat);

        Owner owner = ownerRepository.findByName(ownerName)
                .orElseThrow(() -> new RuntimeException("Owner not found: " + ownerName));

        owner.getCats().add(cat);
        cat.setOwner(owner);

        ownerRepository.save(owner);

        return cat;
    }
}
