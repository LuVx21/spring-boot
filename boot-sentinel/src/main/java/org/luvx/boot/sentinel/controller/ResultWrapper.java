package org.luvx.boot.sentinel.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultWrapper {
    private Integer code;
    private String  message;
}
