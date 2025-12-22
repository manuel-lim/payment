package com.sktelecom.payment.payment;

import static java.math.BigDecimal.valueOf;

import com.sktelecom.payment.ObjectFactory;
import com.sktelecom.payment.ObjectFactoryTest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=ObjectFactoryTest.class)
class PaymentServiceSpringTest {

	@Autowired PaymentService paymentService;

	@Test
	@DisplayName("prepare 메서드가 요구사항 3가지를 잘 충족했는지 검증")
	void convertedAmount() throws IOException {

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// 원화 환산금액 유효시간 계산
		Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000L));
		Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000L));
	}
}