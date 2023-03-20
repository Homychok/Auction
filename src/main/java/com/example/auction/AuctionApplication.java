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

}
