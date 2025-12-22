package com.sktelecom.payment.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
	private final ExRateProvider exRateProvider;

	public PaymentService(ExRateProvider exRateProvider) {
		this.exRateProvider = exRateProvider; // 관리 책임 설정의 분리.
	}

	public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
		BigDecimal krw = exRateProvider.getExRate(currency);

		BigDecimal convertedAmount = foreignCurrencyAmount.multiply(krw);
		LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);
		return new Payment(orderId, currency, foreignCurrencyAmount, krw, convertedAmount, validUntil);
	}
}
