package com.sktelecom.payment;

import com.sktelecom.payment.exrate.CachedExRateProvider;
import com.sktelecom.payment.exrate.WebApiExRateProvider;
import com.sktelecom.payment.payment.ExRateProvider;
import com.sktelecom.payment.payment.ExRateProviderStub;
import com.sktelecom.payment.payment.PaymentService;
import java.math.BigDecimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan  // spring이 필요한 bean 정보
public class ObjectFactoryTest {
	@Bean
	public PaymentService paymentService() {
		return new PaymentService(exRateProvider());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new ExRateProviderStub(BigDecimal.valueOf(1_000));
	}

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}
}