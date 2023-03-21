package com.example.auction.services;
import com.example.auction.dto.LotDTO;
import com.example.auction.dto.BidDTO;
import com.example.auction.dto.BidDTOForFullLotDTO;
import com.example.auction.models.Bid;
import com.example.auction.repositories.BidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class BidService {
    private BidRepository bidRepository;
//    private LotService lotService;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }
////@Autowired
//    public BidService(BidRepository bidRepository, LotService lotService) {
//        this.bidRepository = bidRepository;
//        this.lotService = lotService;
////    }
//    public BidDTO createBid (BidDTO bidDTO) {
//        Bid bid = BidDTO.toBid(bidDTO);
//        bid.setBidderName(bid.getBidderName());
//        Bid newBid = bidRepository.save(bid);
//        return BidDTO.fromBid(newBid);
//    }
    public BidDTOForFullLotDTO createNewBidder (BidDTOForFullLotDTO bidDTOForFullLotDTO, LotDTO lotDTO) {
        Bid bid = bidDTOForFullLotDTO.toBid();
        bid.setLot(lotDTO.toLot());
        bid.setBidDate(LocalDateTime.now());
        return BidDTOForFullLotDTO.fromBid(bidRepository.save(bid));
    }
    public BidDTO getFirstBidderByLotId (Long lotId) {
        return BidDTO.fromBid(bidRepository.findByBidDateMin(lotId));
    }
    public BidDTOForFullLotDTO getMaxBiddersOfBidByLotId(Long lotId) {
        return BidDTOForFullLotDTO.fromBid(bidRepository.getMaxBidders(lotId));
    }
    public Long countTotalPrice(Long lotId) {
        return bidRepository.getCountNumberOfBidByLotId(lotId);
    }
}
