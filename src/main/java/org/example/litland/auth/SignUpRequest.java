package org.example.litland.auth;

import lombok.Data;


@Data
public class SignUpRequest {
    private String username;
    private String password;
}
