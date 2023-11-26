package com.example.gradlesocceronline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateTransferReturn {
    private String errorMessage;
    private Team fromTeam;
    private Team toTeam;
    private TransferPlayer transferPlayer;
}
