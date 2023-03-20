package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.enums.LotStatus;
import com.example.auction.models.Lot;
import com.example.auction.services.BidService;
import com.example.auction.services.LotService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/{id}/first")
    public ResponseEntity<Long> getFirstBidderByLotId(Long id, Long lotId) {
        return ResponseEntity.ok(bidService.getFirstBidderByLotId(id, lotId));
    }
    /*
2.Возвращает имя ставившего на данный лот наибольшее количество раз
*/
    @GetMapping("/{id}/frequent")
    public ResponseEntity<Long> getMaxBiddersOfBidByLotId(Long id, Long lotId) {
        return ResponseEntity.ok(bidService.getMaxBiddersOfBidByLotId(id, lotId));
    }
/*
3.Получить полную информацию о лоте
 */
    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getFullLotById(@PathVariable Long id) {
        FullLotDTO lotDTO = lotService.getFullLotById(id);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lotDTO);
    }
    /*
    4.Начать торги по лоту
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<LotDTO> updateStatusAuctionStart(@RequestBody Long id) {
        LotDTO updateInfoAboutLot = lotService.getLotById(id);
        if (updateInfoAboutLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.STARTED)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.STOPPED)) {
            lotService.updateStatusToStarted(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if (updateInfoAboutLot.getStatus().equals(LotStatus.CREATED)) {
            lotService.updateStatusToStarted(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        }
        return ResponseEntity.ok().build();
//        LotDTO updateInfoAboutLot = lotService.updateStatus(id, "STARTED");
//        if (updateInfoAboutLot == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        if (id == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(updateInfoAboutLot);
    }
    /*
5.Сделать ставку по лоту
 */
    @PostMapping("/{id}/bid")
    public ResponseEntity<BidDTO> createNewBidder(@RequestBody BidDTO bidDTO,
                                                  @RequestBody LotDTO lotDTO) {
        LotDTO findInfoAboutLot = lotService.getLotById(lotDTO.getId());
        if (findInfoAboutLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STARTED)) {
            BidDTO createNewBidder = bidService.createNewBidder(bidDTO);
            return ResponseEntity.ok(createNewBidder);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    /*
6.Остановить торги по лоту
 */
    @PostMapping("/{id}/stop")
    public ResponseEntity<LotDTO> updateStatusAuctionStop(@RequestBody Long id) {
        LotDTO findInfoAboutLot = lotService.getLotById(id);
        if (findInfoAboutLot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STOPPED)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.STARTED)) {
            lotService.updateStatusToStopped(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        if (findInfoAboutLot.getStatus().equals(LotStatus.CREATED)) {
            lotService.updateStatusToStopped(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        }
//        LotDTO updateInfoAboutLot = lotService.updateStatus(id, "STOPPED");
//        if (updateInfoAboutLot == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        if (id == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        return ResponseEntity.ok(findInfoAboutLot);
    }
    /*
7.Создает новый лот
 */
    @PostMapping
    public ResponseEntity<LotDTO> createNewLot(@RequestBody CreateLotDTO createLotDTO) {
        LotDTO createNewLot = lotService.createNewLot(createLotDTO);
        return ResponseEntity.ok(createNewLot);
    }
    /*
    8.Получить все лоты, основываясь на фильтре статуса и номере страницы
     */
    @GetMapping
    public ResponseEntity<Collection<LotDTO>> getAllLots(@RequestParam LotStatus lotStatus,
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
//
//    @GetMapping("{lotTitle}") // GET http://localhost:8080/lots/str
//    public ResponseEntity<LotDTO> getLotByTitle(@RequestParam String lotTitle) {
//        LotDTO lot = lotService.getLotByTitle(lotTitle);
//        if (lotTitle.isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(lot);
//    }

//Ненужные методы:
//    @GetMapping// GET http://localhost:8080/lots
//    public ResponseEntity<Collection<LotDTO>> getLotsByStatus(@RequestParam(required = false) String lotStatus) {
//        if (lotStatus.isBlank()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(lotService.getLotsByStatus(lotStatus));
//    }
//
//    @GetMapping("{lotId}/bidders") // GET http://localhost:8080/lots/3/lots
//    public ResponseEntity<Collection<BidDTO>> getBiddersByLotId(@PathVariable Long lotId) {
//        return ResponseEntity.ok(lotService.getBiddersByLotId(lotId));
//    }
//
//    @GetMapping(value = "/{lotId}/picture/preview")
//    public ResponseEntity<byte[]> downloadPicture(@PathVariable Long lotId) {
//        LotsPic lotsPic = lotsPicService.getLotsPic(lotId);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(lotsPic.getMediaType()));
//        headers.setContentLength(lotsPic.getPreview().length);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lotsPic.getPreview());
//    }


//    @PutMapping("{lotId}")// PUT http://localhost:8080/lots
//    public ResponseEntity<LotDTO> updateStatusAboutLot(@RequestBody LotDTO lotDTO) {
//        LotDTO updateInfoAboutLot = lotService.updateInfoAboutLot(lotDTO);
//        if (updateInfoAboutLot == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(updateInfoAboutLot);
//    }
///*
//убрать лот с аукциона
// */
//    @DeleteMapping("{lotId}") // DELETE http://localhost:8080/lots/3
//    public ResponseEntity<Lot> deleteLotById(@PathVariable Long lotId) {
//        lotService.deleteLotById(lotId);
//        return ResponseEntity.ok().build();
//    }
//
//    /*
//    Возвращает участника аукциона по id
//     */
//    @GetMapping("{bidderId}")// GET http://localhost:8080/bidders/3
//    public ResponseEntity<BidDTO> getBid(@PathVariable Long bidderId) {
//        BidDTO bid = bidService.getBidderById(bidderId);
//        if (bid == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(bid);
//    }
//    /*
//    Возвращает всех участников аукциона
//     */
//    @GetMapping // GET http://localhost:8080/bidders
//    public ResponseEntity<Collection<BidDTO>> getBidders(@RequestParam(required = false) LocalDateTime bidDate) {
//        if (bidDate != null) {
//            return ResponseEntity.ok(bidService.getBidderByDate(bidDate));
//        }
//        return ResponseEntity.ok(bidService.getAllBidders());
//    }

    //    @GetMapping("{bidderId}/lots") // GET http://localhost:8080/bidders/3/lots
//    public ResponseEntity<LotDTO> getLotByBidderId(@PathVariable Long bidderId) {
//        return ResponseEntity.ok(bidService.getLotByBidderId(bidderId));
//    }
//
//    /*
//    Возвращает последнего ставившего на этот лот
// */
//    @GetMapping("/lastBidder")
//    public ResponseEntity<Long> getLastBidderByLotId(int bidderId, Long lotId) {
//        return ResponseEntity.ok(bidService.getLastBidderByLotId(bidderId, lotId));
//    }
//    /*
//Возвращает наибольшее количество ставок на этот лот
//*/
//    @GetMapping("/countBidder")
//    public ResponseEntity<Long> getCountNumberOfBidByLotId(int bidderId, Long lotId) {
//        return ResponseEntity.ok(bidService.getCountNumberOfBidByLotId(bidderId, lotId));
//    }
//
//
//    @PutMapping// PUT http://localhost:8080/bidders
//    public ResponseEntity<BidDTO> updateInfoAboutBidder(@RequestBody BidDTO bidDTO) {
//        BidDTO updateInfoAboutBidder = bidService.updateInfoAboutBidder(bidDTO);
//        if (updateInfoAboutBidder == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(updateInfoAboutBidder);
//    }
//
//    @DeleteMapping("{bidderId}")// DELETE http://localhost:8080/bidders/3
//    public ResponseEntity<Bid> deleteBidderById(@PathVariable Long bidderId) {
//        bidService.deleteBidderById(bidderId);
//        return ResponseEntity.ok().build();
//    }

}


