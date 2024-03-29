/**
 * 
 */
package com.pavan.cards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pavan.cards.config.CardsServiceConfig;
import com.pavan.cards.model.Cards;
import com.pavan.cards.model.Customer;
import com.pavan.cards.model.Properties;
import com.pavan.cards.repository.CardsRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class CardsController {
	private static final Logger logger = LoggerFactory.getLogger(CardsController.class);
	
	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	CardsServiceConfig cardsConfig;
	
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestHeader("pavanbank-correlation-id") String correlationId, @RequestBody Customer customer) {
		logger.info("getCardDetails() method started");
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
		logger.info("getCardDetails() method ended");
		if (cards != null) {
			return cards;
		} else {
			return null;
		}

	}
	
	@GetMapping("/cards/properties")
	public String getAccoutsProperties() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
				cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
		return ow.writeValueAsString(properties);
	}

}
