package com.sparta_logistics.ai.infrastructure.dto;

import jakarta.servlet.http.Part;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeminiRequest {

  private List<Content> contents;

  public GeminiRequest(String text) {
    Part part = new TextPart(text);
    Content content = new Content(Collections.singletonList(part));
    this.contents = List.of(content);
  }

  @Getter
  @AllArgsConstructor
  private static class Content {
    private List<Part> parts;
  }

  interface Part {

  }
  @Getter
  @AllArgsConstructor
  private static class TextPart implements Part {

    public String text;
  }
}
