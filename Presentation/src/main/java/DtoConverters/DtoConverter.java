package DtoConverters;

import WrappedModels.CatDto;
import WrappedModels.OwnerDto;
import WrappedModels.OwnerWithCatsDto;
import src.Models.Cat;
import src.Models.Owner;

import java.util.stream.Collectors;
// TODO: Model mapper
public class DtoConverter {
    public CatDto convertCatToCatDto(Cat cat) {
        return new CatDto(cat.getId(), cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor());
    }

    public OwnerDto convertOwnerToOwnerDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthDate(), owner.getCats().stream().map(this::convertCatToCatDto).collect(Collectors.toSet()));
    }
}
