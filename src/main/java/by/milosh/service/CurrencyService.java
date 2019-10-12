package by.milosh.service;

import by.milosh.model.Exchange;
import by.milosh.model.Rate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyService {

    final static String ALL_RATES = "http://www.nbrb.by/API/ExRates/Rates?Periodicity=0";
    final static String BYN = "BYN";

    public BigDecimal getCurrency(Exchange exchange) {
        String currencyNameFrom = exchange.getFrom();
        String currencyNameTo = exchange.getTo();
        if (currencyNameFrom.equals(BYN) && !currencyNameTo.equals(BYN)) {
            Rate to = getRateFromList(currencyNameTo);
            return fromBYN(to).multiply(exchange.getAmount());
        } else if (!currencyNameFrom.equals(BYN) && currencyNameTo.equals(BYN)) {
            Rate from = getRateFromList(currencyNameFrom);
            return toBYN(from).multiply(exchange.getAmount());
        } else {
            Rate from = getRateFromList(currencyNameFrom);
            Rate to = getRateFromList(currencyNameTo);
            BigDecimal value = convert(from, to);
            return value.multiply(exchange.getAmount());
        }
    }

    private Rate getRateFromList(String currencyName) {
        return getAllRates().stream().filter(rate -> rate.getCurAbbreviation().equals(currencyName)).findFirst()
                .orElse(new Rate());
    }

    private List<Rate> getAllRates() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Rate>> rates = restTemplate.exchange(
                ALL_RATES,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rate>>() {
                }
        );
        return rates.getBody();
    }

    private BigDecimal convert(Rate from, Rate to) {
        BigDecimal fromVALUE = (from.getCurOfficialRate().divide(new BigDecimal(from.getCurScale()), 4, RoundingMode.HALF_UP));
        BigDecimal toVALUE = (to.getCurOfficialRate().divide(new BigDecimal(to.getCurScale()), 4, RoundingMode.HALF_UP));
        return fromVALUE.divide(toVALUE, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal fromBYN(Rate from) {
        return new BigDecimal(1).divide(from.getCurOfficialRate(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(from.getCurScale()));
    }

    private BigDecimal toBYN(Rate to) {
        return to.getCurOfficialRate().divide(new BigDecimal(to.getCurScale()), 4, RoundingMode.HALF_UP);
    }
}
