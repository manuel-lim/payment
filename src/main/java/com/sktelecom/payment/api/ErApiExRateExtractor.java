package com.sktelecom.payment.api;

import com.sktelecom.payment.ExRateData;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor {
    @Override
    public BigDecimal extract(String response) {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }
}
