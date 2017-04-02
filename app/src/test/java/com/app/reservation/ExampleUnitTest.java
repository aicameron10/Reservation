package com.app.reservation;

import com.app.reservation.model.Customers;
import static org.junit.Assert.*;

import com.google.gson.Gson;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void shouldParseCustomer() {
        String json =
                "  {\n" +
                "    \"customerFirstName\": \"Marilyn\",\n" +
                "    \"customerLastName\": \"Monroe\",\n" +
                "    \"id\": 0\n" +
                "  }";
        Customers cus = new Gson().fromJson(json, Customers.class);

        assertEquals(cus.getCustomerFirstName(),"Marilyn");

    }



}