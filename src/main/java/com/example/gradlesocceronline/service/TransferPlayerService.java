package com.example.gradlesocceronline.service;

import com.example.gradlesocceronline.model.*;
import com.example.gradlesocceronline.model.request.AddTransferBoardRequest;
import com.example.gradlesocceronline.repository.PlayerRepo;
import com.example.gradlesocceronline.repository.TeamRepo;
import com.example.gradlesocceronline.repository.TransferHistoryRepo;
import com.example.gradlesocceronline.repository.TransferPlayerRepo;
import com.example.gradlesocceronline.utils.Constants;
import com.example.gradlesocceronline.utils.RandomUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TransferPlayerService {

    @Autowired
    private TransferPlayerRepo transferPlayerRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private TransferHistoryRepo transferHistoryRepo;

    public List<TransferPlayer> getAllPlayer() {
        return transferPlayerRepo.findTransferPlayerByStatus(Constants.ACTIVE);
    }

    public TransferPlayer get(Long id) {
        List<TransferPlayer> transferPlayers = transferPlayerRepo
                .findTransferPlayerByPlayerIdAndStatus(id, Constants.ACTIVE);
        if (transferPlayers == null || transferPlayers.isEmpty()) {
            return null;
        }
        return transferPlayers.get(0);
    }

    public String add(Long fromTeamId, AddTransferBoardRequest request) {
        String errMsg = validateAddTransferBoardRequest(fromTeamId, request);
        if (StringUtils.isNotEmpty(errMsg)) {
            return errMsg;
        }

        TransferPlayer transferPlayer = TransferPlayer.builder()
                .player(Player.builder().id(request.getPlayerId()).build())
                .price(request.getPrice())
                .createdAt(new Date())
                .updatedAt(new Date())
                .status(Constants.ACTIVE)
                .build();

        transferPlayerRepo.save(transferPlayer);
        return null;
    }

    public ValidateTransferReturn validateTransfer(Long playerId, Long toTeamId) {
        List<TransferPlayer> transferPlayerList = transferPlayerRepo.
                findTransferPlayerByPlayerIdAndStatus(playerId, Constants.ACTIVE);
        if (transferPlayerList == null || transferPlayerList.isEmpty()) {
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.TRANSFER_PLAYER_NOT_FOUND).build();
        }
        TransferPlayer transferPlayer = transferPlayerList.get(0);

        Player player = transferPlayer.getPlayer();
        if (player == null || !Constants.ACTIVE.equals(player.getStatus())) {
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.PLAYER_NOT_FOUND).build();
        }

        List<Team> toTeamList = teamRepo.getTeamByIdAndStatus(toTeamId, Constants.ACTIVE);
        if (toTeamList == null || toTeamList.isEmpty()) {
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.TO_TEAM_NOT_FOUND).build();
        }
        Team toTeam = toTeamList.get(0);
        if (toTeam.getBudget() < transferPlayer.getPrice()) {
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.NOT_ENOUGH_BUDGET).build();
        }

        List<Team> fromTeamList = teamRepo.getTeamByIdAndStatus(player.getTeamId(), Constants.ACTIVE);
        if (fromTeamList == null || fromTeamList.isEmpty()) {
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.FROM_TEAM_NOT_FOUND).build();
        }
        Team fromTeam = fromTeamList.get(0);

        if(toTeam.getId() != null && toTeam.getId().equals(fromTeam.getId())){
            return ValidateTransferReturn.builder()
                    .errorMessage(Constants.MESSAGE.TRANSFER_TEAM_DUPLICATE).build();
        }

        return ValidateTransferReturn.builder()
                .transferPlayer(transferPlayerList.get(0))
                .fromTeam(fromTeamList.get(0))
                .toTeam(toTeamList.get(0))
                .build();
    }


    @Transactional
    @OptimisticLock(excluded = true)
    public String transfer(Long playerId, Long toTeamId) {
        try {
            ValidateTransferReturn validateTransferReturn = validateTransfer(playerId, toTeamId);
            if (StringUtils.isNotEmpty(validateTransferReturn.getErrorMessage())) {
                return validateTransferReturn.getErrorMessage();
            }

            //Update transfer player
            TransferPlayer transferPlayer = validateTransferReturn.getTransferPlayer();
            transferPlayer.setStatus(Constants.IN_ACTIVE);
            transferPlayer.setUpdatedAt(new Date());
            transferPlayerRepo.save(transferPlayer);

            //Update player
            Player player = transferPlayer.getPlayer();
            int percent = RandomUtils.random(10, 100);
            log.info("percent increase: " + percent);
            Long oldValue = player.getMarketValue();
            Long newValue = oldValue * (100L + percent) / 100L;
            player.setMarketValue(newValue);
            player.setUpdatedAt(new Date());
            player.setTeamId(toTeamId);
            playerRepo.save(player);

            //Update from-team
            Team fromTeam = validateTransferReturn.getFromTeam();
            fromTeam.setBudget(fromTeam.getBudget() + transferPlayer.getPrice());
            fromTeam.setValue(fromTeam.getValue() - oldValue);
            fromTeam.setUpdatedAt(new Date());
            teamRepo.save(fromTeam);

            //Update to-team
            Team toTeam = validateTransferReturn.getToTeam();
            toTeam.setBudget(toTeam.getBudget() - transferPlayer.getPrice());
            toTeam.setValue(toTeam.getValue() + newValue);
            toTeam.setUpdatedAt(new Date());
            teamRepo.save(toTeam);

            //Insert transfer history
            TransferHistory transferHistory = TransferHistory.builder()
                    .playerId(playerId)
                    .price(transferPlayer.getPrice())
                    .fromTeamId(fromTeam.getId())
                    .toTeamId(toTeamId)
                    .status(Constants.ACTIVE)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            transferHistoryRepo.save(transferHistory);

            return "";
        } catch (Exception e) {
            log.error("error", e);
        }
        return Constants.MESSAGE.INTERNAL_ERROR;
    }

    private String validateAddTransferBoardRequest(Long fromTeamId, AddTransferBoardRequest request) {
        if (request == null) {
            return Constants.MESSAGE.BAD_REQUEST;
        }

        if (request.getPlayerId() == null) {
            return Constants.MESSAGE.PLAYER_ID_REQUIRED;
        }

        Player player = playerRepo.findById(request.getPlayerId()).orElse(null);
        if (player == null || !Constants.ACTIVE.equals(player.getStatus())) {
            return Constants.MESSAGE.PLAYER_NOT_FOUND;

        }

        if (!fromTeamId.equals(player.getTeamId())) {
            return Constants.MESSAGE.NO_PERMISSION_TRANSFER_PLAYER;
        }

        List<TransferPlayer> transferPlayers = transferPlayerRepo.
                findTransferPlayerByPlayerIdAndStatus(request.getPlayerId(), Constants.ACTIVE);
        if (transferPlayers != null && !transferPlayers.isEmpty()) {
            return Constants.MESSAGE.TRANSFER_PLAYER_EXISTED;
        }

        if (request.getPrice() == null) {
            return Constants.MESSAGE.PRICE_REQUIRED;
        }

        if (request.getPrice() < 0L) {
            return Constants.MESSAGE.INVALID_PRICE;
        }

        return null;
    }

}
