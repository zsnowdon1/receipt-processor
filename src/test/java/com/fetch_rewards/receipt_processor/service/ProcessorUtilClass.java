package com.fetch_rewards.receipt_processor.service;

import com.fetch_rewards.receipt_processor.entity.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProcessorUtilClass {

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
        assertEquals(0, ProcessorUtil.getAlphanumericPoints(""));
    }

    @Test
    public void getDatePointsTest() {
        assertEquals(0, ProcessorUtil.getDatePoints(null));
    }

    @Test
    public void getTimePointsTest() {
        assertEquals(0, ProcessorUtil.getTimePoints(""));
    }
}
