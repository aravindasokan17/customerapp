package com.test.customer.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.customer.entity.CustomerEntity;
import com.test.customer.repository.CustomerRepo;

@SpringBootTest
public class TestController {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Test
	public void getTest() {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(14);
		customer.setName("test");
        Boolean actualResult = customerRepo.existsById(customer.getId());
        assertThat(actualResult).isTrue();
	}

}
