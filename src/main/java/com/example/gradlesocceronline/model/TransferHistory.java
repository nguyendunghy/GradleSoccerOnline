package com.example.gradlesocceronline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer_history")
public class TransferHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long playerId;

    @Column
    private Long price;

    @Column Long fromTeamId;

    @Column Long toTeamId;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Long status;
}
