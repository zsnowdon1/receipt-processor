package com.fetch_rewards.receipt_processor.service;

import com.fetch_rewards.receipt_processor.entity.Receipt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class ReceiptProcessorService {

    private HashMap<String, Integer> pointMaps;

    public ReceiptProcessorService() { }

    public String postReceipt(Receipt receipt) {
        long points = ProcessorUtil.getPoints(receipt);
//        pointMaps.put(new UUID(), points);
        return "";
    }

    public int getPoints(String id) {
        return pointMaps.get(id);
    }

}
