//package com.example.auction.dto;
//
//import com.example.auction.models.Bid;
//import com.example.auction.models.Lot;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//@Data
//@NoArgsConstructor
//public class CreateLotDTO {
//    private String lotTitle;
//    private String lotDescription;
//    private int lotStartPrice;
//    private int lotBidPrice;
//    public static CreateLotDTO fromCreateLotDTO (Lot lot) {
//        CreateLotDTO createLotDTO = new CreateLotDTO();
//        createLotDTO.setLotTitle(lot.getLotTitle());
//        createLotDTO.setLotDescription(lot.getLotDescription());
//        createLotDTO.setLotBidPrice(lot.getLotBidPrice());
//        createLotDTO.setLotStartPrice(lot.getLotStartPrice());
//        return createLotDTO;
//    }
//
//    public Lot toCreateLotDTO() {
//        Lot lot = new Lot();
//        lot.setLotTitle(this.getLotTitle());
//        lot.setLotDescription(this.getLotDescription());
//        lot.setLotStartPrice(this.getLotStartPrice());
//        lot.setLotBidPrice(this.getLotBidPrice());
//        return lot;
//    }
//}
