package com.example.module_c;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.module_c.utils.AddressInfo;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        AddressInfo addressInfo = new AddressInfo("DataOrigin.csv");
    }
}