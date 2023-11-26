package com.example.gradlesocceronline.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditTeamRequest {
    private String name;
    private String country;
    private Long status;
    private Long budget;
}
