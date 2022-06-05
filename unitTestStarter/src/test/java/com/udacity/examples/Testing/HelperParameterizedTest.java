package com.udacity.examples.Testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HelperParameterizedTest {
    private String inp;
    private String out;


    public HelperParameterizedTest(String inp, String out) {
        super();
        this.inp = inp;
        this.out = out;
    }

    @Parameterized.Parameters
    public static Collection initData() {
        String empNames[][] = {{"sareeta","sareeta"},{"john","jhn"}};
        return Arrays.asList(empNames);

    }


    /**
     * without parameters
     */
    @Test
    public void verify_number_is_the_same(){
        assertEquals(inp, out);
    }
}
