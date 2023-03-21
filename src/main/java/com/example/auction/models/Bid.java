package com.example.auction.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import com.example.auction.models.Lot;

@Entity
@Data
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "bidder_name")
    private String bidderName;
    @Column(name = "bid_date")
    private LocalDateTime bidDate;
//    @Column(name = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot lot;

//    private Long lotId;

}
