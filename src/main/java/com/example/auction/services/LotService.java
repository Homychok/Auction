package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.enums.LotStatus;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.BidRepository;
import com.example.auction.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotService {
    private LotRepository lotRepository;
    private BidRepository bidRepository;
    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Autowired
    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }
    public LotDTO createNewLot (CreateLotDTO createLotDTO) {
        Lot lot = CreateLotDTO.fromCreateLotDTO(createLotDTO);
        lot.setStatus(LotStatus.CREATED);
        Lot createNewLot = lotRepository.save(lot);
        return LotDTO.fromLotToLotDTO(createNewLot);
    }

    public List<LotDTO> getAllLotsByStatusOnPage (LotStatus lotStatus, Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 10);
        return lotRepository.findAllByStatus(lotStatus, pageRequest)
                .stream()
                .map(LotDTO::fromLotToLotDTO)
                .collect(Collectors.toList());
    }
    public Collection<FullLotDTO> getAllFullLots() {
        return lotRepository.findAll()
                .stream()
                .map(FullLotDTO::fromLotToFullLotDTO)
                .collect(Collectors.toList());
    }
    private Integer sumCurrentPrice(Long lotId, Integer lotBidPrice, Integer lotStartPrice) {
        return (int) (bidRepository.getBidderCount(lotId) * lotBidPrice + lotStartPrice);
    }
    public FullLotDTO getFullLotById (Long id) {
        FullLotDTO fullLotDTO = FullLotDTO.fromLotDTOToFullLotDTO(getLotById(id));
        Integer currentPrice = sumCurrentPrice(id, fullLotDTO.getBidPrice(), fullLotDTO.getStartPrice());
        fullLotDTO.setCurrentPrice(currentPrice);
        fullLotDTO.setLastBid(findInfoABoutLastBid(id));
        return fullLotDTO;
    }
//    public LotDTO updateStatusStopped (Long lotId, String lotStatus) {
//        Lot updateInfoAboutLot = lotRepository.updateLotStatus(lotId, lotStatus);
//        return LotDTO.fromLot(updateInfoAboutLot);
//    }
    public LotDTO getLotById (Long lotId) {
        return LotDTO.fromLot(lotRepository.findById(lotId).get());
    }
    public void updateStatusToStarted(Long id) {
        Lot lot = lotRepository.findById(id).get();
        lot.setStatus(LotStatus.STARTED);
        lotRepository.save(lot);
    }
    public void updateStatusToStopped(Long id) {
        Lot lot = lotRepository.findById(id).get();
        lot.setStatus(LotStatus.STOPPED);
        lotRepository.save(lot);
    }
    //    public Collection<LotDTO> getLotsByStatus(String status){
//        return lotRepository.findByStatus(status)
//                .stream()
//                .map(LotDTO::fromLot)
//                .collect(Collectors.toList());
//    }
    //    public LotDTO updateStatus (Long id, String status) {
//        Lot updateInfoAboutLot = lotRepository.updateLotStatus(id, status);
//        return LotDTO.fromLot(updateInfoAboutLot);
//    }
//    public String findStatus (Long id) {
//        String lotStatus = String.valueOf(lotRepository.findLotStatusByLotId(id));
//        return lotStatus;
//    }
//    public LotDTO updateInfoAboutLot (LotDTO lotDTO) {
//        Lot lot = lotDTO.toLot();
//        Lot updateInfoAboutLot = lotRepository.save(lot);
//        return LotDTO.fromLot(updateInfoAboutLot);
//    }
//
//    public void deleteLotById (Long lotId) {
//        lotRepository.deleteById(lotId);
//    }
//    public List<Lot> getAllLotsByPage (Integer pageNumber, Integer pageSize, String lotStatus) {
//        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
//        return lotRepository.findAll(pageRequest).getContent();
//    }
//
//    public Collection<LotDTO> getAllLots() {
//        return lotRepository.findAll()
//                .stream()
//                .map(LotDTO::fromLot)
//                .collect(Collectors.toList());
//    }
//
//    public Collection<BidDTO> getBiddersByLotId(Long lotId) {
//        List<Bid> bids = lotRepository.findById(lotId).get().getBids();
//        List<BidDTO> bidDTOs = new ArrayList<>();
//        for(Bid bid : bids) {
//            BidDTO bidDTO = BidDTO.fromBid(bid);
//            bidDTOs.add(bidDTO);
//        }
//        return bidDTOs;
//    }
//    public LotDTO getLotByTitle (String lotTitle) {
//        return LotDTO.fromLot(lotRepository.findByLotTitleContainsIgnoreCase(lotTitle));
//    }
//    public Collection<FullLotDTO> getAllLotsForExport(Long bidderId, Long lotId) {
//        return lotRepository.findAll().stream()
//                .map(FullLotDTO::fromLot)
//                .peek(lot -> (lot).setLotCurrentPrice(sumCurrentPrice(lot.getLotId(), lot.getLotLastBid(), lot.getLotStartPrice())))
//                .peek(lot -> (lot).setLotBidPrice(findInfoABoutLastBid(bidderId,lotId)
//                .getLotBidPrice();
//    }

//    public FullLotDTO createNewFullLot (FullLotDTO fullLotDTO) {
//        Lot lot = fullLotDTO.toFullLot();
//        Lot createNewFullLot = lotRepository.save(lot);
//        return FullLotDTO.fromFullLot(createNewFullLot);
//    }
}
