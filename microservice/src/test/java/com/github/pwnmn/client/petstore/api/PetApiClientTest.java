package com.github.pwnmn.client.petstore.api;

import com.github.pwnmn.client.petstore.model.Pet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PetApiClientTest {

    @Autowired
    private PetApiClient petApiClient;

    @Test
    public void getPetById_ValidId_200_ReturnPet() throws Exception {
        ResponseEntity<Pet> petResponse = petApiClient.getPetById(100l);
        assertEquals(HttpStatus.OK, petResponse.getStatusCode());

        var pet = petResponse.getBody();

        assertEquals("dog", pet.getName());
        assertEquals(100, pet.getId());
        assertEquals("available", pet.getStatus().getValue());
    }

    // Add further tests...

}
