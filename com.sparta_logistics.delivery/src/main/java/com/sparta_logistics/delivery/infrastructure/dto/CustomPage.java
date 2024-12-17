package com.sparta_logistics.delivery.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomPage<T> {
  private List<T> content;
}