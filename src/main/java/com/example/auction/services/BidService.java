package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BidService {
    private BidRepository bidRepository;
    private LotRepository lotRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
@Autowired
    public BidService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    public BidDTO createNewBidder (BidDTO bidDTO) {
        Lot lot = lotRepository.findById(bidDTO.getLotId()).get();
        Bid bid = bidDTO.toBidDTO();
        bid.setLot(lot);
        Bid createNewBidder = bidRepository.save(bid);
        return BidDTO.fromBid(createNewBidder);
    }
    public Long getFirstBidderByLotId (Long id, Long lotId) {
        return bidRepository.findByBidDateMin(id, lotId);
    }
    public Long getMaxBiddersOfBidByLotId(Long id, Long lotId) {
        return bidRepository.getMaxBiddersOfBidByLotId(id, lotId);
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
