package com.sktelecom.payment.exrate;

import com.sktelecom.payment.ExRateDate;
import com.sktelecom.payment.payment.ExRateProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class WebApiExRateProvider implements ExRateProvider {
	@Override
	public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
		URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

		String response;
		try {
			HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				response = br.lines().collect(Collectors.joining());
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			ExRateDate data = mapper.readValue(response, ExRateDate.class);
			return data.rates().get("KRW");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
