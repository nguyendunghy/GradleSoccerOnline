package com.example.gradlesocceronline.repository;

import com.example.gradlesocceronline.model.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferHistoryRepo extends JpaRepository<TransferHistory,Long> {
}
