package com.sparta_logistics.delivery.infrastructure.client.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class NominatimClient {

  private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";

  public static double[] getLatLngFromAddress(String address) throws IOException {
    String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
    String requestUrl = String.format("%s?q=%s&format=json&addressdetails=1", NOMINATIM_URL,
        encodedAddress);

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpGet request = new HttpGet(URI.create(requestUrl));
      request.addHeader("User-Agent",
          "Mozilla/5.0 (compatible; NominatimClient/1.0; + http://localhost)");

      try (CloseableHttpResponse response = httpClient.execute(request)) {
        if (response.getCode() == 200) {
          ObjectMapper mapper = new ObjectMapper();
          JsonNode root = mapper.readTree(response.getEntity().getContent());
          if (root.isArray() && !root.isEmpty()) {
            JsonNode location = root.get(0);
            double lat = location.path("lat").asDouble();
            double lon = location.path("lon").asDouble();
            return new double[]{lat, lon};
          }
        }
      }
    }

    return null;
  }
}
