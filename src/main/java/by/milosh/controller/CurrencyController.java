package by.milosh.controller;

import by.milosh.model.Exchange;
import by.milosh.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping(path = "/currencies")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(path = "/exchange")
    public ResponseEntity<BigDecimal> getCurrency(@ModelAttribute @Valid Exchange exchange) {
        BigDecimal value = currencyService.getCurrency(exchange);
        return ResponseEntity.ok()
                .body(value);
    }
}
