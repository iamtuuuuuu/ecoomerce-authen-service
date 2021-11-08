package com.tuhungkien.ecommerce.authenticationservice.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountCreationRequest {
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private String email;
}
