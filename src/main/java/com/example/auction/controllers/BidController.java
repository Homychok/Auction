//package com.example.auction.controllers;
//
//import com.example.auction.dto.BidDTO;
//import com.example.auction.dto.LotDTO;
//import com.example.auction.models.Bid;
//import com.example.auction.services.BidService;
//import com.example.auction.services.LotService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//
//@RestController
//@RequestMapping("bidders")
//public class BidController {
//    private BidService bidService;
//    private LotService lotService;
//
//    public BidController(BidService bidService) {
//        this.bidService = bidService;
//    }
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
//
////    @GetMapping("{bidderId}/lots") // GET http://localhost:8080/bidders/3/lots
////    public ResponseEntity<LotDTO> getLotByBidderId(@PathVariable Long bidderId) {
////        return ResponseEntity.ok(bidService.getLotByBidderId(bidderId));
////    }
//    /*
//    Возвращает первого ставившего на этот лот
//     */
//    @GetMapping("/lot/{lotId}/firstBidder")
//    public ResponseEntity<Long> getFirstBidderByLotId(int bidderId, Long lotId) {
//        return ResponseEntity.ok(bidService.getFirstBidderByLotId(bidderId, lotId));
//    }
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
//    /*
//Возвращает имя ставившего на данный лот наибольшее количество раз
//*/
//    @GetMapping("/maxCount")
//    public ResponseEntity<Long> getMaxBiddersOfBidByLotId(int bidderId, Long lotId) {
//        return ResponseEntity.ok(bidService.getMaxBiddersOfBidByLotId(bidderId, lotId));
//    }
//    /*
//    Сделать ставку по лоту
//     */
//    @PostMapping// POST http://localhost:8080/bidders
//    public ResponseEntity<BidDTO> createNewBidder(@RequestBody BidDTO bidDTO,
//                                                  @RequestBody LotDTO lotDTO) {
//        String lotStatus = lotService.findStatus(lotDTO.getLotId());
//        if (lotStatus == "STARTED") {
//            BidDTO createNewBidder = bidService.createNewBidder(bidDTO);
//            return ResponseEntity.ok(createNewBidder);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
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
//}
