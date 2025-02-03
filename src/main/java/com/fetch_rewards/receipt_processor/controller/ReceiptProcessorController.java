package com.fetch_rewards.receipt_processor.controller;

import com.fetch_rewards.receipt_processor.entity.GetPointsResponse;
import com.fetch_rewards.receipt_processor.entity.PostReceiptResponse;
import com.fetch_rewards.receipt_processor.entity.Receipt;
import com.fetch_rewards.receipt_processor.service.ReceiptProcessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/receipts")
public class ReceiptProcessorController {

    private final ReceiptProcessorService receiptService;

    public ReceiptProcessorController(ReceiptProcessorService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<PostReceiptResponse> postReceipt(@RequestBody Receipt receipt) {
        try {
            String id = receiptService.postReceipt(receipt);
            return new ResponseEntity<>(new PostReceiptResponse(id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<GetPointsResponse> getPoints(@PathVariable String id) {
        try {
            int points = receiptService.getPoints(id);
            return new ResponseEntity<>(new GetPointsResponse(points), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
