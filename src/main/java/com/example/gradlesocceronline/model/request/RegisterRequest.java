package com.example.gradlesocceronline.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    private String username;

    private String password;

    private String confirmPassword;

}
