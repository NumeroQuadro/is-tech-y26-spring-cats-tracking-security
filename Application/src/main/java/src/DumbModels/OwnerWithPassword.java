package src.DumbModels;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
public class OwnerWithPassword {
    private final String name;
    private final LocalDate birthDate;

    private final String password;

    public OwnerWithPassword(String name, LocalDate birthDate, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
    }
}
