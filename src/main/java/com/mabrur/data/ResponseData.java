package com.mabrur.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseData<T> {
    
    private int status;

    private String message;

    private T payload;

}
