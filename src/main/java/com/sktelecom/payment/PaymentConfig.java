package com.sktelecom.payment;

import com.sktelecom.payment.api.ApiTemplate;
import com.sktelecom.payment.api.ErApiExRateExtractor;
import com.sktelecom.payment.api.SimpleApiExecutor;
import com.sktelecom.payment.exrate.CachedExRateProvider;
import com.sktelecom.payment.exrate.RestTemplateExRateProvider;
import com.sktelecom.payment.payment.ExRateProvider;
import com.sktelecom.payment.exrate.WebApiExRateProvider;
import com.sktelecom.payment.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
//@ComponentScan  // spring이 필요한 bean 정보
public class PaymentConfig {
	@Bean
	public PaymentService paymentService() {
		return new PaymentService(exRateProvider(), clock());
	}

	@Bean
	public ApiTemplate apiTemplate() {
		return new ApiTemplate(new SimpleApiExecutor(), new ErApiExRateExtractor());
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new JdkClientHttpRequestFactory());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new RestTemplateExRateProvider(restTemplate());
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