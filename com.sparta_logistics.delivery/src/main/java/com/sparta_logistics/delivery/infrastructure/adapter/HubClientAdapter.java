package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.HubClientPort;
import com.sparta_logistics.delivery.infrastructure.client.HubClient;
import com.sparta_logistics.delivery.infrastructure.dto.HubDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubSearchRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientAdapter implements HubClientPort {

  private final HubClient hubClient;

  @Override
  public Boolean existsByHubId(String hubId) {
    HubReadResponse hub = hubClient.getHub(UUID.fromString(hubId));
    return hub != null;
  }

  @Override
  public Map<String, HubDto> findHubAll() {
    List<HubReadResponse> hubs = hubClient.getHubs();
    System.out.println(hubs);
    // 경기 남부가 중앙 허브
    /*
    hubMap.put("00b3aa2f-badc-4a80-b580-2096f464827a", HubDto.builder()
        .hubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .name("경기 남부 센터")
        .address("경기도 이천시 덕평로 257-21")
        .centerHubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .isCenter(true)
        .latitude(37.1896213142136)
        .longitude(127.375050006958)
        .build());

    hubMap.put("0db4de98-11c8-42bb-bd1e-1ff6d5c5aabd", HubDto.builder()
        .hubId("0db4de98-11c8-42bb-bd1e-1ff6d5c5aabd")
        .name("경기 북부 센터")
        .address("경기도 고양시 덕양구 권율대로 570")
        .centerHubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .isCenter(false)
        .latitude(37.6403771056018)
        .longitude(126.87379545786)
        .build());

    hubMap.put("ed0469e2-3640-48a9-a847-aba828f6a8ed", HubDto.builder()
        .hubId("ed0469e2-3640-48a9-a847-aba828f6a8ed")
        .name("서울특별시 센터")
        .address("서울특별시 송파구 송파대로 55")
        .centerHubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .isCenter(false)
        .latitude(37.4742027808565)
        .longitude(127.123621185562)
        .build());

    hubMap.put("0fd1197e-6f3f-4231-b3d8-85e66b443b0f", HubDto.builder()
        .hubId("0fd1197e-6f3f-4231-b3d8-85e66b443b0f")
        .name("인천광역시 센터")
        .address("인천 남동구 정각로 29")
        .centerHubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .isCenter(false)
        .latitude(37.4560499608337)
        .longitude(126.705255744089)
        .build());

    hubMap.put("b283b23c-2099-4ced-83dc-410e365e8963", HubDto.builder()
        .hubId("b283b23c-2099-4ced-83dc-410e365e8963")
        .name("강원특별자치도 센터")
        .address("강원특별자치도 춘천시 중앙로 1")
        .centerHubId("00b3aa2f-badc-4a80-b580-2096f464827a")
        .isCenter(false)
        .latitude(37.8800729197963)
        .longitude(127.727907820318)
        .build());

    // 대전이 중앙 허브
    hubMap.put("ee0147aa-42b5-43f1-b944-ecb9b907271a", HubDto.builder()
        .hubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .name("대전광역시 센터")
        .address("대전 서구 둔산로 100")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(true)
        .latitude(36.3503849976553)
        .longitude(127.384633005948)
        .build());

    hubMap.put("5a615071-c41d-4208-9309-d5427fbd18fa", HubDto.builder()
        .hubId("5a615071-c41d-4208-9309-d5427fbd18fa")
        .name("충청북도 센터")
        .address("충북 청주시 상당구 상당로 82")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(36.6353867908159)
        .longitude(127.491428436987)
        .build());

    hubMap.put("96dba43d-4d48-428e-9b4d-a225f6cde530", HubDto.builder()
        .hubId("96dba43d-4d48-428e-9b4d-a225f6cde530")
        .name("충청남도 센터")
        .address("충남 홍성군 홍북읍 충남대로 21")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(36.6590666265439)
        .longitude(126.672978750559)
        .build());

    hubMap.put("fae6f552-6ffd-48ed-bf0b-de5188f35ab7", HubDto.builder()
        .hubId("fae6f552-6ffd-48ed-bf0b-de5188f35ab7")
        .name("세종특별자치시 센터")
        .address("세종특별자치시 한누리대로 2130")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(36.4800579897497)
        .longitude(127.289039408864)
        .build());

    hubMap.put("35c22e88-94a9-4aee-ad9b-e62e0db6c8ce", HubDto.builder()
        .hubId("35c22e88-94a9-4aee-ad9b-e62e0db6c8ce")
        .name("전라남도 센터")
        .address("전남 무안군 삼향읍 오룡길 1")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(34.8174988528003)
        .longitude(126.465423854957)
        .build());

    hubMap.put("f3142311-7d8a-41ff-8688-8ab3cd674b76", HubDto.builder()
        .hubId("f3142311-7d8a-41ff-8688-8ab3cd674b76")
        .name("전북특별자치도 센터")
        .address("전북특별자치도 전주시 완산구 효자로 225")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(35.8194621650578)
        .longitude(127.106396942356)
        .build());

    hubMap.put("5d5512a9-63fb-47e0-a412-34c2c1b937c3", HubDto.builder()
        .hubId("5d5512a9-63fb-47e0-a412-34c2c1b937c3")
        .name("광주광역시 센터")
        .address("광주 서구 내방로 111")
        .centerHubId("ee0147aa-42b5-43f1-b944-ecb9b907271a")
        .isCenter(false)
        .latitude(35.1600994105234)
        .longitude(126.851461925213)
        .build());

    hubMap.put("4d38dbf8-3cc9-429b-8297-b906bcc90d06", HubDto.builder()
        .hubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .name("대구광역시 센터")
        .address("대구 북구 태평로 161")
        .centerHubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .isCenter(true)
        .latitude(35.8758849492106)
        .longitude(128.596129208483)
        .build());

    hubMap.put("ffb47f8a-fe6a-4825-8b04-8bc0bb9d33f9", HubDto.builder()
        .hubId("ffb47f8a-fe6a-4825-8b04-8bc0bb9d33f9")
        .name("부산광역시 센터")
        .address("부산 동구 중앙대로 206")
        .centerHubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .isCenter(false)
        .latitude(35.117605126596)
        .longitude(129.045060216345)
        .build());

    hubMap.put("56a4a600-6f49-49bc-8a12-7b863b61c265", HubDto.builder()
        .hubId("56a4a600-6f49-49bc-8a12-7b863b61c265")
        .name("울산광역시 센터")
        .address("울산 남구 중앙로 201")
        .centerHubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .isCenter(false)
        .latitude(35.5379472830778)
        .longitude(129.311256608093)
        .build());

    hubMap.put("783a8813-e909-47eb-a6a1-f901a26330a5", HubDto.builder()
        .hubId("783a8813-e909-47eb-a6a1-f901a26330a5")
        .name("경상북도 센터")
        .address("경북 안동시 풍천면 도청대로 455")
        .centerHubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .isCenter(false)
        .latitude(36.5761205474728)
        .longitude(128.505722686385)
        .build());

    hubMap.put("01280f59-10de-40b0-8902-49bd8a8fa58d", HubDto.builder()
        .hubId("01280f59-10de-40b0-8902-49bd8a8fa58d")
        .name("경상남도 센터")
        .address("경남 창원시 의창구 중앙대로 300")
        .centerHubId("4d38dbf8-3cc9-429b-8297-b906bcc90d06")
        .isCenter(false)
        .latitude(35.2378032514675)
        .longitude(128.691940442146)
        .build());

     */

    return hubs.stream()
        .map(HubDto::from)
        .collect(Collectors.toMap(
            HubDto::hubId, // 키로 사용할 값
            hub -> hub     // 값으로 사용할 객체
        ));
  }

  @Override
  public Map<String, HubRouteDto> findHubRouteAll() {
    List<HubRouteReadResponse> hubRoutes = hubClient.readHubRoutes();
    return hubRoutes.stream()
        .map(HubRouteDto::from)
        .collect(Collectors.toMap(
            HubRouteDto::sourceHubId,
            hub -> hub
        ));
  }

}
