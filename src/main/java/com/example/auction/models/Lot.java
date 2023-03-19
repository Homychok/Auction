package com.example.auction.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import com.example.auction.models.Bid;
@Entity
@Data
@NoArgsConstructor
public class Lot {
    @Id
    @Column(name = "lot_last_bid")
    private int lotLastBid;
    @Column(name = "lot_id")
    private long lotId;
    @Column(name = "lot_status")
    @Enumerated(EnumType.STRING)
    private LotStatus lotStatus;
    @Column(name = "lot_title")
    private String lotTitle;
    @Column(name = "lot_description", length=4096)
    private String lotDescription;
    @Column(name = "lot_current_price")
    private int lotCurrentPrice;
private int lotStartPrice;
private int lotBidPrice;
    @OneToMany(mappedBy = "lot")
    private List<Bid> bids;
    public enum LotStatus {
        STARTED,
        STOPPED,
        CREATED;
        LotStatus() {
        }

    }

}
