package com.fetch_rewards.receipt_processor.service;

import com.fetch_rewards.receipt_processor.entity.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProcessorUtilTest {

    @Test
    public void getAlphanumericPointsTest() {
        assertEquals(0, ProcessorUtil.getAlphanumericPoints(""));
        assertEquals(7, ProcessorUtil.getAlphanumericPoints("abcdefg"));
        assertEquals(5, ProcessorUtil.getAlphanumericPoints("12345"));
        assertEquals(6, ProcessorUtil.getAlphanumericPoints("abc123"));
        assertEquals(6, ProcessorUtil.getAlphanumericPoints("abc-123"));
    }

    @Test
    public void getRoundDollarPoints() {
        assertEquals(50, ProcessorUtil.getRoundDollarPoints(1));
        assertEquals(0, ProcessorUtil.getRoundDollarPoints(1.25));
        assertEquals(0, ProcessorUtil.getRoundDollarPoints(1123.25));
        assertEquals(50, ProcessorUtil.getRoundDollarPoints(1123));
        assertEquals(0, ProcessorUtil.getRoundDollarPoints(-1.25));
        assertEquals(50, ProcessorUtil.getRoundDollarPoints(1.00));
    }

    @Test
    public void getQuarterMultiplePointsTest() {
        assertEquals(0, ProcessorUtil.getQuarterMultiplePoints(1.01));
        assertEquals(25, ProcessorUtil.getQuarterMultiplePoints(1.25));
        assertEquals(25, ProcessorUtil.getQuarterMultiplePoints(1123.75));
        assertEquals(25, ProcessorUtil.getQuarterMultiplePoints(1123));
        assertEquals(0, ProcessorUtil.getQuarterMultiplePoints(-1.24));
        assertEquals(25, ProcessorUtil.getQuarterMultiplePoints(1.50));
    }

    @Test
    public void getTwoItemPointsTest() {
        ArrayList items = Mockito.mock(ArrayList.class);
        when(items.size()).thenReturn(0);
        assertEquals(0, ProcessorUtil.getTwoItemPoints(items));
        when(items.size()).thenReturn(1);
        assertEquals(0, ProcessorUtil.getTwoItemPoints(items));
        when(items.size()).thenReturn(2);
        assertEquals(5, ProcessorUtil.getTwoItemPoints(items));
        when(items.size()).thenReturn(3);
        assertEquals(5, ProcessorUtil.getTwoItemPoints(items));
        when(items.size()).thenReturn(4);
        assertEquals(10, ProcessorUtil.getTwoItemPoints(items));

    }

    @Test
    public void getCrazyPointsTest() {
        ArrayList<Item> items = new ArrayList<>();
        Item item1 = new Item("", 1);
        items.add(item1);
        assertEquals(0, ProcessorUtil.getCrazyPoints(items));
        Item item2 = new Item("abc", 5);
        items.add(item2);
        assertEquals(1, ProcessorUtil.getCrazyPoints(items));
        Item item3 = new Item("abc3", 25);
        items.add(item3);
        assertEquals(1, ProcessorUtil.getCrazyPoints(items));
        Item item4 = new Item("123456789", 150);
        items.add(item4);
        assertEquals(31, ProcessorUtil.getCrazyPoints(items));
        Item item5 = new Item("123456789", .75);
        items.add(item5);
        assertEquals(31, ProcessorUtil.getCrazyPoints(items));
        Item item6 = new Item("123456789", 12.25);
        items.add(item6);
        assertEquals(32, ProcessorUtil.getCrazyPoints(items));
    }

    @Test
    public void getDatePointsTest() {
        LocalDate date = LocalDate.parse("2022-02-02", DateTimeFormatter.ISO_LOCAL_DATE);
        assertEquals(0, ProcessorUtil.getDatePoints(date));
        date = LocalDate.parse("2022-02-03", DateTimeFormatter.ISO_LOCAL_DATE);
        assertEquals(6, ProcessorUtil.getDatePoints(date));
        date = LocalDate.parse("2022-01-31", DateTimeFormatter.ISO_LOCAL_DATE);
        assertEquals(6, ProcessorUtil.getDatePoints(date));
        date = LocalDate.parse("2022-01-30", DateTimeFormatter.ISO_LOCAL_DATE);
        assertEquals(0, ProcessorUtil.getDatePoints(date));
    }

    @Test
    public void getTimePointsTest() {
        LocalTime time = LocalTime.parse("13:01", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(0, ProcessorUtil.getTimePoints(time));
        time = LocalTime.parse("14:01", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(10, ProcessorUtil.getTimePoints(time));
        time = LocalTime.parse("15:59", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(10, ProcessorUtil.getTimePoints(time));
        time = LocalTime.parse("16:01", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(0, ProcessorUtil.getTimePoints(time));
        time = LocalTime.parse("04:01", DateTimeFormatter.ISO_LOCAL_TIME);
        assertEquals(0, ProcessorUtil.getTimePoints(time));
    }
}
