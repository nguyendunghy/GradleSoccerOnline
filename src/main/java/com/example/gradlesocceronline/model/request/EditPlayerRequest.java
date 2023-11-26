package com.example.gradlesocceronline.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPlayerRequest {
    private String firstName;

    private String lastName;

    private String country;

    private Long age;

    private Long marketValue;

    private Long teamId;

    private Long status;

}
