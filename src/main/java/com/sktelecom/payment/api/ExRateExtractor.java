package com.sktelecom.payment.api;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal extract(String response);
}
