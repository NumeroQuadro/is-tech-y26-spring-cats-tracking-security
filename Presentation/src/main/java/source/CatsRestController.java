package source;

import DtoConverters.DtoConverter;
import WrappedModels.CatDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import src.DumbModels.CatWithoutOwner;
import src.DumbModels.OwnerWithPassword;
import src.Models.*;
import src.Services.CatService;
import src.Services.OwnerService;
import src.Services.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@NoArgsConstructor
@RequestMapping("/cats_tracking")
public class CatsRestController {
    @Autowired
    private CatService catService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private UserService userService;
    private final DtoConverter dtoConverter = new DtoConverter();

    // http://localhost:8080/cats_tracking/cat-by-color/black
    // http://numeroquadro.space:8080/cats_tracking/cat-by-color/black
    @GetMapping("/cat-by-color/{color}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Collection<CatDto> getCatsByColorCats(@PathVariable String color) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();
        var catsByColorRelatedToOwner = catService.getCatsByColorRelatedToOwner(CatColor.valueOf(color), ownerName);

        return catsByColorRelatedToOwner.stream().map(dtoConverter::convertCatToCatDto).toList();
    }

    // http://localhost:8080/cats_tracking/all-cats-owned-by-owner
    // http://numeroquadro.space:8080/cats_tracking/all-cats-owned-by-owner?ownerName=Bob
    @GetMapping("/all-cats-owned-by-owner")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public Collection<CatDto> getAllCatsOwnedByOwner() throws IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();
        Collection<Cat> cats = catService.getAllCatsOwnedByOwner(ownerName);

        return cats.stream().map(dtoConverter::convertCatToCatDto).collect(Collectors.toList());
    }

    // todo: is it correct get possibly null from ownerService.findOwnerByName()?
    // http://localhost:8080/cats_tracking/add-user
    @PostMapping("/add-user")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    // todo: only for admin
    public Owner addNewOwner(@RequestBody OwnerWithPassword ownerWithPassword) {
        userService.addNewUser(ownerWithPassword.getName(), ownerWithPassword.getPassword(), Role.USER);
        ownerService.addOwnerWithoutCats(ownerWithPassword.getName(), ownerWithPassword.getBirthDate());

        return ownerService.findOwnerByName(ownerWithPassword.getName());
    }

    // http://localhost:8080/cats_tracking/add-cat
    @PostMapping("/add-cat")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    // todo: all users
    public Cat addNewCatAssociatedWithCurrentUser(
            @RequestBody CatWithoutOwner catWithoutOwner
    ) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var ownerName = authentication.getName();

        return ownerService.addNewCatAssociatedWithOwner(ownerName, catWithoutOwner.getName(), catWithoutOwner.getBreed(), catWithoutOwner.getBirthDate(), catWithoutOwner.getColor());
    }
}
