package com.example.auction.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import com.example.auction.models.Bid;
@Entity
@Data
@NoArgsConstructor
public class Lot {
    @Id
    @Column(name = "lot_last_bid")
    private int lotLastBid;
    @Column(name = "lot_id")
    private long lotId;
    @Column(name = "lot_status")
    private String lotStatus;
    @Column(name = "lot_title")
    private String lotTitle;
    @Column(name = "lot_description", length=4096)
    private String lotDescription;
    @Column(name = "lot_current_price")
    private int lotCurrentPrice;
private int lotStartPrice;
private int lotBidPrice;
    @OneToMany(mappedBy = "lot")
    private List<Bid> bids;
//    enum Queries {
//        GET("SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id AND id=(?)"),
//        GET_ALL("SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id"),
//        INSERT("INSERT INTO employee(first_name, last_name, gender, age, city_id) VALUES ((?), (?), (?),(?),(?))"),
//        DELETE("DELETE FROM employee WHERE id=(?)"),
//        UPDATE("UPDATE employee SET age = (?) WHERE id=(?)");
//
//        String query;
//        Queries(String query) {
//            this.query = query;
//        }
//    }

}
