package com.fetch_rewards.receipt_processor.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReceiptProcessorControllerTest {

    @InjectMocks
    private ReceiptProcessorController processorController;

    private String morningJSON = this.readJsonFile("src/test/resources/morning-receipt.json");
    private String simpleJSON = this.readJsonFile("src/test/resources/simple-receipt.json");

    @Test
    public void postReceiptTest() throws Exception {
//        processorController.postReceipt()
    }


    private static String readJsonFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            return null;
        }
    }
}