package com.github.pwnmn.client.petstore.api;

import com.github.pwnmn.client.petstore.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="${pet.name:pet}", url="${pet.url}", configuration = ClientConfiguration.class)
public interface PetApiClient extends PetApi {
}
