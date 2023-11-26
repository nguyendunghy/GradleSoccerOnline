package com.example.gradlesocceronline.controller;

import com.example.gradlesocceronline.model.TransferPlayer;
import com.example.gradlesocceronline.model.request.AddTransferBoardRequest;
import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.service.TransferPlayerService;
import com.example.gradlesocceronline.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.gradlesocceronline.utils.Constants.REQUEST_ATTRIBUTE.TEAM_ID;

@RestController
@RequestMapping("/transfer-board")
@Slf4j
public class TransferBoardController {
    @Autowired
    private TransferPlayerService transferPlayerService;


    @GetMapping(value = "/players", consumes = "application/json", produces = "application/json")
    public CommonResponse getAllPlayers() {
        List<TransferPlayer> playerList = transferPlayerService.getAllPlayer();
        return CommonResponse.builder()
                .code(Constants.CODE.SUCCESS)
                .message(Constants.MESSAGE.SUCCESS)
                .value(playerList)
                .build();
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public CommonResponse addPlayer(@RequestAttribute(TEAM_ID) Long fromTeamId,
                                    @RequestBody AddTransferBoardRequest request) {
        String errMsg = transferPlayerService.add(fromTeamId, request);
        if (StringUtils.isNotEmpty(errMsg)) {
            return CommonResponse.builder()
                    .code(Constants.CODE.ERROR.ADD_TRANSFER_PLAYER)
                    .message(errMsg)
                    .build();
        }

        return CommonResponse.builder()
                .code(Constants.CODE.SUCCESS)
                .message(Constants.MESSAGE.SUCCESS)
                .build();
    }

    @PostMapping(value = "/transfer/{playerId}", consumes = "application/json", produces = "application/json")
    public CommonResponse transfer(@RequestAttribute(TEAM_ID) Long toTeamId,
                                   @PathVariable("playerId") Long playerId) {
        String errMsg = transferPlayerService.transfer(playerId, toTeamId);
        if (StringUtils.isNotEmpty(errMsg)) {
            return CommonResponse.builder()
                    .code(Constants.CODE.ERROR.TRANSFER_PLAYER)
                    .message(errMsg)
                    .build();
        }

        return CommonResponse.builder()
                .code(Constants.CODE.SUCCESS)
                .message(Constants.MESSAGE.SUCCESS)
                .build();
    }

}
