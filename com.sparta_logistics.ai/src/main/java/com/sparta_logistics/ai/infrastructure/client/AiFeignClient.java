package com.sparta_logistics.ai.infrastructure.client;

import com.sparta_logistics.ai.infrastructure.dto.GeminiRequest;
import com.sparta_logistics.ai.infrastructure.dto.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "AiClient",
    url = "${gemini.api.url}"
)
public interface AiFeignClient {
  @PostMapping
  GeminiResponse sendGemini(
      @RequestParam("key") String key,
      @RequestBody GeminiRequest request
  );
}
