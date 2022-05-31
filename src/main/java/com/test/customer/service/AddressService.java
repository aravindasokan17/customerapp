package com.test.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.customer.repository.AddressRepo;

@Service
public class AddressService {
	
	@Autowired
	AddressRepo addressRepo;

	
}
