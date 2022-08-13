package com.mabrur.data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class TodoForm {
    
   
    private Long id; 
    
    @NotBlank(message = "title is mandatory")
    @Size(min= 8, max= 20)
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(min= 8, max= 100)
    private String description;

    private Boolean checked;
}
