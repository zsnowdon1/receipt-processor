package com.fetch_rewards.receipt_processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch_rewards.receipt_processor.entity.Receipt;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorUtilTest {

    @Test
    public void getReceiptPointsTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Receipt morningReceipt = objectMapper.readValue(new File("src/test/resources/morning-receipt.json"), Receipt.class);
        Receipt simpleReceipt = objectMapper.readValue(new File("src/test/resources/simple-receipt.json"), Receipt.class);
        assertEquals(15, ProcessorUtil.getReceiptPoints(morningReceipt));
        assertEquals(31, ProcessorUtil.getReceiptPoints(simpleReceipt));
    }

}
