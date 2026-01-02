package com.sktelecom.payment;

import com.sktelecom.payment.payment.Payment;
import com.sktelecom.payment.payment.PaymentService;
import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
		PaymentService paymentService = beanFactory.getBean(PaymentService.class);
		Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
		System.out.println(payment);
	}
}
