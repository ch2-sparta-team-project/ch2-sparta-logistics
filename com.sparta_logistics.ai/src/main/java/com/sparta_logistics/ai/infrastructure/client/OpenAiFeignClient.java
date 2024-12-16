package com.sparta_logistics.ai.infrastructure.client;

import com.sparta_logistics.ai.infrastructure.config.OpenAiConfig;
import com.sparta_logistics.ai.infrastructure.dto.OpenAiRequest;
import com.sparta_logistics.ai.infrastructure.dto.OpenAiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "OpenAiClient",
    url = "${openai.api.url}",
    configuration = OpenAiConfig.class
)
public interface OpenAiFeignClient {

  @PostMapping
  OpenAiResponse sendOpenAi(@RequestBody OpenAiRequest request);
}
