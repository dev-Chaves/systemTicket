package com.devchaves.ticketSystem.DTOS;

import com.devchaves.ticketSystem.models.RoleEnum;

public class UserRegisterDTO {
    private String usersName;
    private String usersPass;
    private RoleEnum usersRole;
    private String token;

    public UserRegisterDTO(String usersName, String usersPass, RoleEnum usersRole, String token) {
        this.usersName = usersName;
        this.usersPass = usersPass;
        this.usersRole = usersRole;
        this.token = token;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersPass() {
        return usersPass;
    }

    public void setUsersPass(String usersPass) {
        this.usersPass = usersPass;
    }

    public RoleEnum getUsersRole() {
        return usersRole;
    }

    public void setUsersRole(RoleEnum usersRole) {
        this.usersRole = usersRole;
    }
}
