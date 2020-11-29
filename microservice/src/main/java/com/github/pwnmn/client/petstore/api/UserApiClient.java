package com.github.pwnmn.client.petstore.api;

import com.github.pwnmn.client.petstore.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="${user.name:user}", url="${user.url}", configuration = ClientConfiguration.class)
public interface UserApiClient extends UserApi {
}
