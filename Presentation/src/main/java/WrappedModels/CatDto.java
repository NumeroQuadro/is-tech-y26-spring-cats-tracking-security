package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import src.Models.CatColor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CatDto implements Dto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private CatColor color;
    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
