package com.example.auction.dto;

import com.example.auction.models.Lot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLotDTO {
//    @JsonIgnore
//    private Long id;
    private String title;
    private String description;
    private Long startPrice;
    private Long bidPrice;
    public Lot toLot(){
        Lot lot = new Lot();
        lot.setId(lot.getId());
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBidPrice(this.getBidPrice());
        return lot;
    }
}
