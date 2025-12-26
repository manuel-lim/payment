package com.sktelecom.payment;

import com.sktelecom.payment.exrate.CachedExRateProvider;
import com.sktelecom.payment.payment.ExRateProvider;
import com.sktelecom.payment.exrate.WebApiExRateProvider;
import com.sktelecom.payment.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
//@ComponentScan  // spring이 필요한 bean 정보
public class PaymentConfig {
	@Bean
	public PaymentService paymentService() {
		return new PaymentService(exRateProvider(), clock());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new WebApiExRateProvider();
	}

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}
}