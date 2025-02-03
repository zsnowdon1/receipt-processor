package com.fetch_rewards.receipt_processor.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Item {
    private String shortDescription;
    private double price;
}
