package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerWithCatsDto implements Dto {
    private OwnerDto owner;
    private CatDto cat;

    public OwnerWithCatsDto() { }
}