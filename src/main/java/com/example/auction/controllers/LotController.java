package com.example.auction.controllers;

import com.example.auction.dto.*;
import com.example.auction.enums.LotStatus;
import com.example.auction.services.BidService;
import com.example.auction.services.LotService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

@RestController
@RequestMapping("lot")
public class LotController {
    private LotService lotService;
    private BidService bidService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }
@Autowired
    public LotController(LotService lotService, BidService bidService) {
        this.lotService = lotService;
        this.bidService = bidService;
    }
    /*
1.Возвращает первого ставившего на этот лот
 */
    @GetMapping("/{lotId}/first")
    public ResponseEntity<?> getFirstBidder(@PathVariable Long lotId) {
        BidDTOForFullLotDTO firstBidder = bidService.getFirstBidderByLotId(lotId);
        if (firstBidder == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        return ResponseEntity.ok(firstBidder);
    }
    /*
2.Возвращает имя ставившего на данный лот наибольшее количество раз
*/
    @GetMapping("/{lotId}/frequent")
    public ResponseEntity<?> getMostFrequentBidder(@RequestParam(name = "id of lot for choosen bid") Long lotId) {
        BidDTOForFullLotDTO bidDTOForFullLotDTO = bidService.getMaxBiddersOfBidByLotId(lotId);
        if (bidDTOForFullLotDTO == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        return ResponseEntity.ok(bidDTOForFullLotDTO);
    }
/*
3.Получить полную информацию о лоте
 */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFullLot(@PathVariable Long id) {
        FullLotDTO lotDTO = lotService.getFullLotById(id);
        if (lotDTO == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        return ResponseEntity.ok(lotDTO);
    }
    /*
    4.Начать торги по лоту
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<?> updateStatusAuctionStart(@PathVariable(name = "id of lot for update") Long id) {
        LotDTO updateInfoAboutLot = lotService.getLotById(id);
        if (updateInfoAboutLot == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.STARTED)) {
            return ResponseEntity.status(200).body("Лот в статусе начато");
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.STOPPED)) {
            lotService.updateStatusToStarted(id);
            return ResponseEntity.status(200).body("Лот переведен в статус начато");
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.CREATED)) {
            lotService.updateStatusToStarted(id);
            return ResponseEntity.status(200).body("Лот переведен в статус начато");
        }
        return ResponseEntity.ok().build();
    }
    /*
5.Сделать ставку по лоту
 */
    @PostMapping("/{id}/bid")
    public ResponseEntity<?> createBid(@RequestBody BidDTOForFullLotDTO bidDTOForFullLotDTO,
                                               @PathVariable Long id) {
        LotDTO findInfoAboutLot = lotService.getLotById(id);
        if (findInfoAboutLot == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STOPPED) ||
                findInfoAboutLot.getStatus().equals(LotStatus.CREATED)) {
            return ResponseEntity.status(400).body("Лот в неверном статусе");
        }
        bidDTOForFullLotDTO.setLotId(bidDTOForFullLotDTO.getLotId());
        bidService.createNewBidder(bidDTOForFullLotDTO);
        return ResponseEntity.status(200).body("Ставка создана");
    }
    /*
6.Остановить торги по лоту
 */
    @PostMapping("/{id}/stop")
    public ResponseEntity<?> updateStatusAuctionStop(@PathVariable(name = "id of lot for update") Long id) {
        LotDTO findInfoAboutLot = lotService.getLotById(id);
        if (findInfoAboutLot == null) {
            return ResponseEntity.status(404).body("Лот не найден");
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STOPPED)) {
            return ResponseEntity.status(200).body("Лот в статусе остановлен");
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STARTED)) {
            lotService.updateStatusToStopped(id);
            return ResponseEntity.status(200).body("Лот переведен в статус остановлен");
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.CREATED)) {
            lotService.updateStatusToStopped(id);
            return ResponseEntity.status(200).body("Лот переведен в статус остановлен");

        }
        return ResponseEntity.ok().build();
    }
    /*
7.Создает новый лот
 */
    @PostMapping
    public ResponseEntity<?> createLot(@RequestBody CreateLotDTO createLotDTO) {
        LotDTO createNewLot = lotService.createNewLot(createLotDTO);
        boolean checking = lotService.checkMistakeInCreatingLot(createLotDTO);
        if (!checking) {
            return ResponseEntity.status(400).body("Лот передан с ошибкой");
        }
        return ResponseEntity.ok(createNewLot);
    }
    /*
    8.Получить все лоты, основываясь на фильтре статуса и номере страницы
     */
    @GetMapping
    public ResponseEntity<Collection<LotDTO>> findLots(@RequestParam LotStatus lotStatus,
                                                         @RequestParam(name = "page", required = false) Integer pageNumber) {
        if (pageNumber <= 0) {
            return ResponseEntity.ok(lotService.getAllLotsByStatusOnPage(lotStatus, 1));
        }

        return ResponseEntity.ok(lotService.getAllLotsByStatusOnPage(lotStatus, pageNumber));
    }
    /*
9. Экспортировать все лоты в файл CSV
 */
    @GetMapping("/export")
    public void downloadLotTable(HttpServletResponse response) throws IOException {
        Collection<FullLotDTO> lots = lotService.getAllFullLots();
        StringWriter writer = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        for (FullLotDTO lot : lots) {
            csvPrinter.printRecord(lot.getId(),
                    lot.getTitle(),
                    lot.getStatus(),
                    lot.getStartPrice(),
                    lot.getLastBid(),
                    lot.getCurrentPrice());
        }
        csvPrinter.flush();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"lots.csv\"");

        PrintWriter pWriter = response.getWriter();
        pWriter.write(writer.toString());
        pWriter.flush();
        pWriter.close();
    }
}


