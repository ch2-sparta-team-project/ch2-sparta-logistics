package com.sparta_logistics.order.application.port;


import com.sparta_logistics.order.application.dto.CreateDeliveryReq;

public interface DeliveryClientPort {

  String createDelivery(CreateDeliveryReq createDeliveryReq);
}
