package com.fetch_rewards.receipt_processor.service;

import com.fetch_rewards.receipt_processor.entity.Receipt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class ReceiptProcessorService {

    private HashMap<String, Integer> pointMaps;

    public ReceiptProcessorService() {
        pointMaps = new HashMap<>();
    }

    /**
     * Receives receipt object, calculates points, and posts to map
     * @param receipt Receipt object to be registered
     * @return Receipt ID
     */
    public String postReceipt(Receipt receipt) {
        int points = ProcessorUtil.getReceiptPoints(receipt);
        String uuid = UUID.randomUUID().toString();
        pointMaps.put(uuid, points);
        return uuid.toString();
    }

    /**
     * Calls points map with id
     * @param id Receipt ID
     * @return Receipt points
     */
    public int getPoints(String id) {
        return pointMaps.get(id);
    }

}
