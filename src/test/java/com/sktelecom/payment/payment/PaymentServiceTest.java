package com.sktelecom.payment.payment;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

	Clock clock;

	@BeforeEach
	void beforeEach() {
		this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
	}

	@Test
	@DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족했는지 검증")
	void convertedAmount() {
		Payment payment = getPayment(valueOf(500), valueOf(5_000), this.clock), clock;
		getPayment(valueOf(1000), valueOf(10_000), this.clock);
		getPayment(valueOf(2000), valueOf(20_000), this.clock);

		// 원화 환산금액 유효시간 계산
//		Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//		Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
	}

	@Test
	void validUntil() {
		PaymentService paymentService= new PaymentService(new ExRateProviderStub(valueOf(1000)), clock);
		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
		// valid until
		LocalDateTime now = LocalDateTime.now(clock);
		LocalDateTime validUntil = now.plusMinutes(30);

		Assertions.assertThat(payment.getValidUntil()).isEqualTo(validUntil);;

	}

	private static @NonNull Payment getPayment(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) {
		// 준비
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// 환율정보 가져오기
		Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
		// 원화 환산 금액 계산
		Assertions.assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
		return payment;
	}
}