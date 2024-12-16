package com.sparta_logistics.company.application.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public interface ProductClient {

}
