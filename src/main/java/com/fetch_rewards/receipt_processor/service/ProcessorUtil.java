package com.fetch_rewards.receipt_processor.service;


import com.fetch_rewards.receipt_processor.entity.Item;

import java.util.Date;
import java.util.List;

public class ProcessorUtil {

    // One point for every alphanumeric character in the retailer name.
    public static int getAlphanumericPoints(String retailer) {
        int points = 0;
        for(int i = 0; i < retailer.length(); i++) {
            if(Character.isLetterOrDigit(retailer.charAt(i))) {
                points += 1;
            }
        }
        return points;
    }

    // 50 points if the total is a round dollar amount with no cents.
    public static int getRoundDollarPoints(double total) {
        if(total % 1 == 0) {
            return 50;
        }
        return 0;
    }

    // 25 points if the total is a multiple of 0.25.
    public static int getQuarterMultiplePoints(double total) {
        if(total % .25 == 0) {
            return 25;
        }
        return 0;
    }

    // 5 points for every two items on the receipt.
    public static int getTwoItemPoints(List<Item> items) {
        int sizeMult = items.size() / 2;
        return sizeMult * 5;
    }

    // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
    public static int getCrazyPoints(List<Item> items) {
        return 0;
    }

    // 6 points if the day in the purchase date is odd.
    public static int getDatePoints(Date date) {
        return 0;
    }

    public static int getTimePoints(String time) {
        return 0;
    }

}
