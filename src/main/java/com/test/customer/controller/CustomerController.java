package com.test.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.customer.model.Customer;
import com.test.customer.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/{id}")
	public List<Customer> getCustomer (@PathVariable Integer id) {
		return customerService.getById(id);
	}
	
	@PostMapping("/create")
	public Customer createUser (@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
	
	@PostMapping("/upload")
	public List<Customer> uploadCustomer (@RequestParam("file") MultipartFile file) {
		return customerService.uploadAllCustomers(file);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser (@PathVariable Integer id) {
		return customerService.deleteCustomer(id);
	}

}
