package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class BidService {
    private BidRepository bidRepository;
    private LotRepository lotRepository;
    private LotService lotService;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
@Autowired
    public BidService(BidRepository bidRepository, LotService lotService) {
        this.bidRepository = bidRepository;
        this.lotService = lotService;
    }
    public BidDTO createBid (BidDTO bidDTO) {
        Bid bid = BidDTO.fromBidDTOtoBid(bidDTO);
        bid.setBidderName(bid.getBidderName());
//        bid.setBidDate(bid.getBidDate());
        Bid newBid = bidRepository.save(bid);
        return BidDTO.fromBidToBidDTO(newBid);
    }
    public BidDTOForFullLotDTO createNewBidder (BidDTOForFullLotDTO bidDToForFullLotDTO) {
        Lot lot = LotDTO.fromLotDTOToLot(lotService.getLotById(bidDToForFullLotDTO.getLotId()));
        Bid bid = BidDTOForFullLotDTO.fromBidDToForFullLotDTOtoBid(bidDToForFullLotDTO);
        bid.setLot(lot);
        bid.setBidDate(LocalDateTime.now());
        Bid createNewBidder = bidRepository.save(bid);
        return BidDTOForFullLotDTO.fromBidToBidDToForFullLotDTO(createNewBidder);
//        FullLotDTO lot = FullLotDTO.fromLotToFullLotDTO(lotService.getLotById(l).getId());
//        BidDTO bid = BidDTO.fromBid(new Bid());
//        bid.setLotId(bid.getBidderName().g);
//        bid.setBidDate(LocalDateTime.now());
//        BidDTO createNewBidder = bidRepository.save(bidDTO);
//        return BidDTO.fromBid(createNewBidder);
    }
    public Long getFirstBidderByLotId (Long id, Long lotId) {
        return bidRepository.findByBidDateMin(id, lotId);
    }
    public String getMaxBiddersOfBidByLotId(Long lotId) {
        return bidRepository.getMaxBidders(lotId);
    }
//        public BidDTO getBidderById (Long id) {
//        return BidDTO.fromBid(bidRepository.findById(id).get());
//    }
//    public BidDTO updateInfoAboutBidder (BidDTO bidDTO) {
//        Lot lot = lotRepository.findById(bidDTO.getLotId()).get();
//        Bid bid = bidDTO.toBid();
//        bid.setLot(lot);
//        Bid updateInfoAboutBidder = bidRepository.save(bid);
//        return BidDTO.fromBid(updateInfoAboutBidder);
//    }
//    public void deleteBidderById (Long id) {
//        bidRepository.deleteById(id);
//    }
//
//    public Collection<BidDTO> getAllBidders() {
//        return bidRepository.findAll()
//                .stream()
//                .map(BidDTO::fromBid)
//                .collect(Collectors.toList());
//    }
//    public Collection<BidDTO> getBidderByName (String bidderName) {
//        return bidRepository.findByBidderNameContainsIgnoreCase(bidderName).stream()
//                .map(BidDTO::fromBid)
//                .collect(Collectors.toList());
//    }
//    public LotDTO getLotByBidderId(Long id) {
//        Lot lot = lotRepository.findById(getBidderById(id).getLotId()).get();
//        return LotDTO.fromLot(lot);
//    }
//    public Collection<BidDTO> getBidderByDate (LocalDateTime bidDate) {
//        return bidRepository.findByBidDate(bidDate).stream()
//                .map(BidDTO::fromBid)
//                .collect(Collectors.toList());
//    }
//
//    public Long getLastBidderByLotId (Long id, Long lotId) {
//        return bidRepository.findByBidDateMax(id, lotId);
//    }
//    public Long getCountNumberOfBidByLotId(Long id, Long lotId) {
//        return bidRepository.getCountNumberOfBidByLotId(id, lotId);
//    }

}
