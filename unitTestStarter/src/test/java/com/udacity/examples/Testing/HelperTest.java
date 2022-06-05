package com.udacity.examples.Testing;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.*;

public class HelperTest {

	@Test
    public void test1(){
        List<String> empNames = Arrays.asList("frank", "udacity");
        final long actual = Helper.getCount(empNames);
        assertEquals(2, actual);
    }
    @Test
    public void verify_getStats(){
        List<Integer> yrsOfExperience = Arrays.asList(13,4,5,6,7,10);
        IntSummaryStatistics stats = Helper.getStats(yrsOfExperience);
        assertEquals(13, stats.getMax());


    }

    @Test
    public void compareArrays(){
        int[] yrs = {10,4,5};
        int[] expected = {10,4,5};
        assertArrayEquals(expected, yrs);
    }
    @Test
    public void testGetMergedList(){
        List<String> empNames = Arrays.asList("Frank", "Patrick", "Neville");
        String actual = Helper.getMergedList(empNames);
        assertEquals("Frank, Patrick, Neville", actual);

    }
}
