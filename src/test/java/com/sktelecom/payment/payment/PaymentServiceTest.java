package com.sktelecom.payment.payment;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

import com.sktelecom.payment.exrate.WebApiExRateProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

	@Test
	@DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족했는지 검증")
	void convertedAmount() throws IOException {
		Payment payment = getPayment(valueOf(500), valueOf(5_000));
		getPayment(valueOf(1000), valueOf(10_000));
		getPayment(valueOf(2000), valueOf(20_000));

		// 원화 환산금액 유효시간 계산
		Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
		Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
	}

	private static @NonNull Payment getPayment(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
		// 준비
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// 환율정보 가져오기
		Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
		// 원화 환산 금액 계산
		Assertions.assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
		return payment;
	}
}