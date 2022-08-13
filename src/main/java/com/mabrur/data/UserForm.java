package com.mabrur.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.mabrur.entities.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserForm {


    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min= 8, max= 20)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min= 8, max= 20)
    private String password;

    private Collection<Role> roles = new ArrayList<>();
}
