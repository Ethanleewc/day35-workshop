package com.example.server.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.server.models.Order;
import com.example.server.services.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path="/order", produces=MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="*")
public class OrderController {

	private Logger logger = Logger.getLogger(OrderController.class.getName());

	@Autowired
	private OrderService orderSvc;

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postOrder(@RequestBody String payload) {

		logger.info("New order: %s".formatted(payload));

		Order order = Order.toOrder(payload);

		String orderId = orderSvc.createOrder(order);
		JsonObject resp = Json.createObjectBuilder()
			.add("orderId", orderId)
			.build();

		return ResponseEntity.ok(resp.toString());
	}
}