package com.vti.request;

import lombok.Data;

@Data
public class AccountRequest {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private Integer groupId;
}
