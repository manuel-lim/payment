package com.sktelecom.payment.exrate;

import com.sktelecom.payment.api.*;
import com.sktelecom.payment.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

@Component
public class WebApiExRateProvider implements ExRateProvider {
	final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
	public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

		return apiTemplate.getExRate(url);
	}
}
