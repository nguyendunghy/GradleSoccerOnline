package com.example.gradlesocceronline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "transfer_player")
public class TransferPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column
    //    private Long playerId;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @Column
    private Long price;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Long status;

    @JsonIgnore
    @Version
    @Column
    private Long version;


}
