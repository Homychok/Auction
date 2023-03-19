package com.example.auction.services;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Bid;
import com.example.auction.models.Lot;
import com.example.auction.repositories.LotRepository;
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

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }


    public LotDTO createNewLot (LotDTO lotDTO) {
        Lot lot = lotDTO.toLot();
        Lot createNewLot = lotRepository.save(lot);
        return LotDTO.fromLot(createNewLot);
    }
    public LotDTO updateInfoAboutLot (LotDTO lotDTO) {
        Lot lot = lotDTO.toLot();
        Lot updateInfoAboutLot = lotRepository.save(lot);
        return LotDTO.fromLot(updateInfoAboutLot);
    }
    public void deleteLotById (Long lotId) {
        lotRepository.deleteById(lotId);
    }
    public List<Lot> getAllLots (Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return lotRepository.findAll(pageRequest).getContent();
    }
    public LotDTO getLotById (Long lotId) {
        return LotDTO.fromLot(lotRepository.findById(lotId).get());
    }
    public Collection<LotDTO> getAllLots() {
        return lotRepository.findAll()
                .stream()
                .map(LotDTO::fromLot)
                .collect(Collectors.toList());
    }
    public Collection<BidDTO> getBiddersByLotId(Long lotId) {
        List<Bid> bids = lotRepository.findById(lotId).get().getBids();
        List<BidDTO> bidDTOs = new ArrayList<>();
        for(Bid bid : bids) {
            BidDTO bidDTO = BidDTO.fromBid(bid);
            bidDTOs.add(bidDTO);
        }
        return bidDTOs;
    }
    public Collection<LotDTO> getLotsByStatus(String lotStatus){
        return lotRepository.findByLotStatusIs(lotStatus)
                .stream()
                .map(LotDTO::fromLot)
                .collect(Collectors.toList()); }
    public LotDTO getLotByTitle (String lotTitle) {
        return LotDTO.fromLot(lotRepository.findByLotTitleContainsIgnoreCase(lotTitle));
    }
}
