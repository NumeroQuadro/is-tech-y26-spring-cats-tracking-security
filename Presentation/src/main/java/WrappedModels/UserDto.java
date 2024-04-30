package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import src.Models.Role;

@Getter
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Role role;
}
