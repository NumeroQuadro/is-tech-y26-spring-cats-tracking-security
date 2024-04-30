package src.DumbModels;

import jakarta.persistence.*;
import lombok.Getter;
import src.Models.Cat;
import src.Models.CatColor;
import src.Models.Owner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CatWithoutOwner {
    private String name;
    private LocalDate birthDate;
    private String breed;
    private CatColor color;
    private final Set<Cat> friends = new HashSet<>();

    public CatWithoutOwner(String name, LocalDate birthDate, String breed, CatColor color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
    }
}
