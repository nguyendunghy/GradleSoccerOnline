package com.example.gradlesocceronline.repository;

import com.example.gradlesocceronline.model.TransferPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferPlayerRepo extends JpaRepository<TransferPlayer, Long> {

    List<TransferPlayer> findTransferPlayerByStatus(Long status);

    List<TransferPlayer> findTransferPlayerByPlayerIdAndStatus(Long playerId, Long status);


}
