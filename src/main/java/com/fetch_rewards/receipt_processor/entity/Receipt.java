package com.fetch_rewards.receipt_processor.entity;

import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Receipt {
    private String retailer;
    private Date purchaseDate;
    private Time purchaseTime;
    private List<Item> items;
    private long total;
}
