package com.sktelecom.payment.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExRateProvider {
	BigDecimal getExRate(String currency) throws IOException;
}
