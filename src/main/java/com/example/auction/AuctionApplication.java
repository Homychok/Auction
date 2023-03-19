package com.example.auction;

import com.example.auction.models.Lot;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SpringBootApplication
@OpenAPIDefinition
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
//    @Test
//    void givenCSVfile() throws IOException {
//        Lot lot = new Lot();
//
//        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                .setHeader("HEADERS")
//                .build();
//
//        try (final CSVPrinter printer = new CSVPrinter((Appendable) lot, csvFormat)) {
//            lot.wait((lot_id, lot_status, lot_title, lot_description, lot_current_price, lot_last_bid) -> {
//                try {
//                    printer.printRecord(lot_id, lot_status, lot_description, lot_current_price, lot_title, lot_last_bid);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        assertEquals(EXPECTED_FILESTREAM, lot.toString().trim());
//    }
}
