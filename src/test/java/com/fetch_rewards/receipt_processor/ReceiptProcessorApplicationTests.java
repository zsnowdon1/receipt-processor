package com.fetch_rewards.receipt_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch_rewards.receipt_processor.controller.ReceiptProcessorController;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptProcessorApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ReceiptProcessorController receiptProcessorController;
	public Receipt morningReceipt;
	public Receipt simpleReceipt;

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

		String morningReceiptJson = objectMapper.writeValueAsString(morningReceipt);
		String simpleReceiptJson = objectMapper.writeValueAsString(simpleReceipt);
		String receipt109Json = objectMapper.writeValueAsString(receipt109);
		String receipt28Json = objectMapper.writeValueAsString(receipt28);

		String responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(morningReceiptJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
		PostReceiptResponse morningPostResponse = objectMapper.readValue(responseBody, PostReceiptResponse.class);

		responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(simpleReceiptJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
		PostReceiptResponse simplePostResponse = objectMapper.readValue(responseBody, PostReceiptResponse.class);

		responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(receipt109Json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
		PostReceiptResponse receipt109PostResponse = objectMapper.readValue(responseBody, PostReceiptResponse.class);

		responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(receipt28Json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
		PostReceiptResponse receipt28PostResponse = objectMapper.readValue(responseBody, PostReceiptResponse.class);

		responseBody = mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(receipt28Json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}

}
