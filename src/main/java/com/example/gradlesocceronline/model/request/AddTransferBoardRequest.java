package com.example.gradlesocceronline.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AddTransferBoardRequest {
    private Long playerId;
    private Long price;
}
