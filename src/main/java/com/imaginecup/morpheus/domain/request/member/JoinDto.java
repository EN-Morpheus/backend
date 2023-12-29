package com.imaginecup.morpheus.domain.request.member;

import lombok.Getter;

@Getter
public class JoinDto {

    private String id;
    private String password;
    private String passwordChecked;
    private String email;
    private String name;

}
