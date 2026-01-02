package com.sktelecom.payment.exrate;

import com.sktelecom.payment.ExRateDate;
import com.sktelecom.payment.api.ApiExecutor;
import com.sktelecom.payment.api.ErApiExRateExtractor;
import com.sktelecom.payment.api.ExRateExtractor;
import com.sktelecom.payment.api.SimpleApiExecutor;
import com.sktelecom.payment.payment.ExRateProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class WebApiExRateProvider implements ExRateProvider {
	@Override
	public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
		return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());
	}

	private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		String response;
		try {
			response = apiExecutor.execute(uri);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			return exRateExtractor.extract(response);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
