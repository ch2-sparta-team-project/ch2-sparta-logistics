package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.application.dto.CompanyDto;

public interface CompanyClientPort {

  CompanyDto findCompanyInfoByUserId(String userId);
}
