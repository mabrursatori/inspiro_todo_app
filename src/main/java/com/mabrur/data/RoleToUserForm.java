package com.mabrur.data;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RoleToUserForm {
    
    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "role name is mandatory")
    private String roleName;

}
