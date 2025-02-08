package com.fetch_rewards.receipt_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch_rewards.receipt_processor.controller.ReceiptProcessorController;
import com.fetch_rewards.receipt_processor.entity.GetPointsResponse;
import com.fetch_rewards.receipt_processor.entity.PostReceiptResponse;
import com.fetch_rewards.receipt_processor.entity.Receipt;
import com.fetch_rewards.receipt_processor.service.ProcessorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptProcessorApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ReceiptProcessorController receiptProcessorController;

	@Test
	public void testGetEndpoint() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Receipt morningReceipt = objectMapper.readValue(new File("src/test/resources/morning-receipt.json"), Receipt.class);
		Receipt simpleReceipt = objectMapper.readValue(new File("src/test/resources/simple-receipt.json"), Receipt.class);
		Receipt receipt109 = objectMapper.readValue(new File("src/test/resources/109-receipt.json"), Receipt.class);
		Receipt receipt28 = objectMapper.readValue(new File("src/test/resources/28-receipt.json"), Receipt.class);
		assertEquals(15, ProcessorUtil.getReceiptPoints(morningReceipt));
		assertEquals(31, ProcessorUtil.getReceiptPoints(simpleReceipt));
		assertEquals(109, ProcessorUtil.getReceiptPoints(receipt109));
		assertEquals(28, ProcessorUtil.getReceiptPoints(receipt28));

		assertThat(receiptProcessorController).isNotNull();

		PostReceiptResponse morningPostResponse = this.postReceipt(morningReceipt);
		PostReceiptResponse simplePostResponse = this.postReceipt(simpleReceipt);
		PostReceiptResponse receipt109PostResponse = this.postReceipt(receipt109);
		PostReceiptResponse receipt28PostResponse = this.postReceipt(receipt28);


		GetPointsResponse morningGetResponse = this.getPoints(morningPostResponse.getId());
		GetPointsResponse simpleGetResponse = this.getPoints(simplePostResponse.getId());
		GetPointsResponse receipt109GetResponse = this.getPoints(receipt109PostResponse.getId());
		GetPointsResponse receipt28GetResponse = this.getPoints(receipt28PostResponse.getId());

		assertEquals(15, morningGetResponse.getPoints());
		assertEquals(31, simpleGetResponse.getPoints());
		assertEquals(109, receipt109GetResponse.getPoints());
		assertEquals(28, receipt28GetResponse.getPoints());


	}

	private PostReceiptResponse postReceipt(Receipt receipt) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String receiptJson = objectMapper.writeValueAsString(receipt);

		String responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(receiptJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return objectMapper.readValue(responseBody, PostReceiptResponse.class);
	}

	private GetPointsResponse getPoints(String receiptId) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String responseBody = mockMvc.perform(get("/receipts/{id}/points", receiptId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getContentAsString();
		return objectMapper.readValue(responseBody, GetPointsResponse.class);
	}

}
