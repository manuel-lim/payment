package com.sktelecom.payment.payment;

import java.math.BigDecimal;

public interface ExRateProvider {
	BigDecimal getExRate(String currency);
}
