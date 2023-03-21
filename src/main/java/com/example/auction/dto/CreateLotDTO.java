package com.example.auction.dto;

import com.example.auction.models.Lot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLotDTO {
    @JsonIgnore
    private Long id;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
    public static Lot fromCreatedLotDTOToLot(CreateLotDTO createdLotDTO){
        Lot lot = new Lot();
        lot.setId(createdLotDTO.getId());
        lot.setTitle(createdLotDTO.getTitle());
        lot.setDescription(createdLotDTO.getDescription());
        lot.setStartPrice(createdLotDTO.getStartPrice());
        lot.setBidPrice(createdLotDTO.getBidPrice());
        return lot;
    }
//    public static Lot fromCreateLotDTO (CreateLotDTO createLotDTO) {
//        Lot lot = new Lot();
//        lot.setTitle(createLotDTO.getTitle());
//        lot.setDescription(createLotDTO.getDescription());
//        lot.setBidPrice(createLotDTO.getBidPrice());
//        lot.setStartPrice(createLotDTO.getStartPrice());
//        return lot;
//    }
//
////    public Lot toCreateLotDTO() {
////        Lot lot = new Lot();
////        lot.setId(this.getId());
////        lot.setTitle(this.getTitle());
////        lot.setDescription(this.getDescription());
////        lot.setStartPrice(this.getStartPrice());
////        lot.setBidPrice(this.getBidPrice());
////        return lot;
////    }
}
