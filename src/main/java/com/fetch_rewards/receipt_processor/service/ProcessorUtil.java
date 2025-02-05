package com.fetch_rewards.receipt_processor.service;


import com.fetch_rewards.receipt_processor.entity.Item;
import com.fetch_rewards.receipt_processor.entity.Receipt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProcessorUtil {

    public static int getReceiptPoints(Receipt receipt) {
        int points = 0;
        points += ProcessorUtil.getAlphanumericPoints(receipt.getRetailer());
        points += ProcessorUtil.getRoundDollarPoints(receipt.getTotal());
        points += ProcessorUtil.getQuarterMultiplePoints(receipt.getTotal());
        points += ProcessorUtil.getTwoItemPoints(receipt.getItems());
        points += ProcessorUtil.getCrazyPoints(receipt.getItems());
        points += ProcessorUtil.getDatePoints(receipt.getPurchaseDate());
        points += ProcessorUtil.getTimePoints(receipt.getPurchaseTime());
        return points;
    }


    // One point for every alphanumeric character in the retailer name.
    static int getAlphanumericPoints(String retailer) {
        int points = 0;
        for(int i = 0; i < retailer.length(); i++) {
            if(Character.isLetterOrDigit(retailer.charAt(i))) {
                points += 1;
            }
        }
        System.out.println("getAlphanumericPoints: " + points);
        return points;
    }

    // 50 points if the total is a round dollar amount with no cents.
    static int getRoundDollarPoints(double total) {
        if(total % 1 == 0) {
            System.out.println("getRoundDollarPoints: 50");
            return 50;
        }
        System.out.println("getRoundDollarPoints: 0");
        return 0;
    }

    // 25 points if the total is a multiple of 0.25.
    static int getQuarterMultiplePoints(double total) {
        if(total % .25 == 0) {
            System.out.println("getQuarterMultiplePoints: 25");
            return 25;
        }
        System.out.println("getQuarterMultiplePoints: 0");
        return 0;
    }

    // 5 points for every two items on the receipt.
    static int getTwoItemPoints(List<Item> items) {
        int sizeMult = items.size() / 2;
        System.out.println("getTwoItemPoints: " + sizeMult * 5);
        return sizeMult * 5;
    }

    // If the trimmed length of the item description is a multiple of 3,
    // multiply the price by 0.2 and round up to the nearest integer.
    // The result is the number of points earned.
    static int getCrazyPoints(List<Item> items) {
        int points = 0;
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int trimmedLength = item.getShortDescription().trim().length();
            if(trimmedLength % 3 == 0) {
                points += (int) Math.round(item.getPrice() * .2);
            }
        }
        System.out.println("getCrazyPoints: " + points);
        return points;
    }

    // 6 points if the day in the purchase date is odd.
    static int getDatePoints(LocalDate date) {
        int points = date.getDayOfMonth() % 2 != 0 ? 6 : 0;
        System.out.println("getDatePoints: " + points);
        return points;
    }

    static int getTimePoints(LocalTime time) {
        // Could be 13, 59 if you want 4:00 to count
        LocalTime start = LocalTime.of(14, 0);
        LocalTime end = LocalTime.of(16, 0);
        int points = (time.isBefore(end) && time.isAfter(start)) ? 10 : 0;
        System.out.println("getDatePoints: " + points);
        return points;
    }

}
