package com.sktelecom.payment.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
	private final ExRateProvider exRateProvider;
	private final Clock clock;

	public PaymentService(ExRateProvider exRateProvider, Clock clock) {
		this.exRateProvider = exRateProvider; // 관리 책임 설정의 분리.
		this.clock = clock;
	}

	public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
		BigDecimal krw = exRateProvider.getExRate(currency);
		return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, krw, LocalDateTime.now(this.clock));  // data holder, data carrier

	}
}
