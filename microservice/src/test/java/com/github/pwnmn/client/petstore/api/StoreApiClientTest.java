package com.github.pwnmn.client.petstore.api;

import com.github.pwnmn.client.petstore.model.Order;
import com.github.pwnmn.client.petstore.model.Pet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoreApiClientTest {

    @Autowired
    private StoreApiClient storeApiClient;

    @Test
    public void getPetById_ValidId_200_ReturnPet() throws Exception {
        var orderId = 1l;
        ResponseEntity<Order> petResponse = storeApiClient.getOrderById(orderId);
        assertEquals(HttpStatus.OK, petResponse.getStatusCode());

        var order = petResponse.getBody();

        assertEquals(orderId, order.getId());
        assertEquals(100, order.getQuantity());
        assertEquals("placed", order.getStatus().getValue());
        assertEquals(true, order.getComplete());
    }

    // Add further tests...

}
