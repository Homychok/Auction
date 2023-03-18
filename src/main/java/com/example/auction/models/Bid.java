package com.example.auction.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_last_bid")
    private int lotLastBid;
    @Column(name = "bidder_id")
    private int bidderId;
    @Column(name = "bidder_name")
    private String bidderName;
    @Column(name = "bidder_date")
    private LocalDateTime bidDate;
    private long lotId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_last_bid")
    private Lot lot;
}
