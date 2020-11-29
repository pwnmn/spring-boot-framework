package com.github.pwnmn.client.petstore.api;

import com.github.pwnmn.client.petstore.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="${store.name:store}", url="${store.url}", configuration = ClientConfiguration.class)
public interface StoreApiClient extends StoreApi {
}
