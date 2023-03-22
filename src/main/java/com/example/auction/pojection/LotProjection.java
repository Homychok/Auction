package com.example.auction.pojection;

import java.time.LocalDateTime;

public interface LotProjection {
    String getBidderName();
    LocalDateTime getBidDate();
}
