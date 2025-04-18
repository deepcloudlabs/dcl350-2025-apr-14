package com.example.crm.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.CustomerDocument;
import com.example.crm.service.ReactiveCrmService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class ReactiveCrmRestController {
	private final ReactiveCrmService reactiveCrmService;

	public ReactiveCrmRestController(ReactiveCrmService reactiveCrmService) {
		this.reactiveCrmService = reactiveCrmService;
	}

	@GetMapping("/{email}")
	public Mono<CustomerDocument> getCustomerByEmail(@PathVariable String email) {
		return reactiveCrmService.findCustomerByEmail(email);
	}

	@GetMapping(params = { "pageNo", "pageSize" })
	public Flux<CustomerDocument> getCustomers(@RequestParam int pageNo, @RequestParam int pageSize) {
		return reactiveCrmService.findCustomers(pageNo, pageSize);
	}

	@PostMapping
	public Mono<CustomerDocument> acquireCustomer(@RequestBody CustomerDocument customer) {
		return reactiveCrmService.addCustomer(customer);
	}

	@PutMapping("/{email}")
	public Mono<CustomerDocument> updateCustomer(@PathVariable String email, @RequestBody CustomerDocument customer) {
		return reactiveCrmService.updateCustomer(email, customer);
	}
	

	@DeleteMapping("/{email}")
	public Mono<CustomerDocument> releaseCustomerByEmail(@PathVariable String email) {
		return reactiveCrmService.removeCustomerByEmail(email);
	}
}
