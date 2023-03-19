package com.example.auction.services;

import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.models.Lot;
import com.example.auction.repositories.LotRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FullLotService {
    private LotRepository lotRepository;

    public FullLotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public FullLotDTO createNewFullLot (FullLotDTO fullLotDTO) {
        Lot lot = fullLotDTO.toFullLot();
        Lot createNewFullLot = lotRepository.save(lot);
        return FullLotDTO.fromFullLot(createNewFullLot);
    }
    public FullLotDTO getFullLotById (Long lotId) {
        return FullLotDTO.fromFullLot(lotRepository.findById(lotId).get());
    }

}
