package com.sparta_logistics.delivery.application.util;

public class HaversineUtil {

  // 지구 반지름 (단위: km)
  private static final double EARTH_RADIUS_KM = 6371.0;

  /**
   * 두 지점 간의 거리 계산 (단위: km)
   *
   * @param lat1 첫 번째 지점의 위도
   * @param lon1 첫 번째 지점의 경도
   * @param lat2 두 번째 지점의 위도
   * @param lon2 두 번째 지점의 경도
   * @return 두 지점 간의 거리 (km 단위)
   */
  public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    // 위도와 경도를 라디안 값으로 변환
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);

    double radLat1 = Math.toRadians(lat1);
    double radLat2 = Math.toRadians(lat2);

    // Haversine 공식 적용
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
        + Math.cos(radLat1) * Math.cos(radLat2)
        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS_KM * c; // 거리 반환
  }
}
