package com.test.customer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.customer.entity.AddressEntity;
import com.test.customer.entity.CustomerEntity;
import com.test.customer.model.Customer;
import com.test.customer.repository.AddressRepo;
import com.test.customer.repository.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	AddressRepo addressRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	public List<Customer> getById (Integer id) {
		List<Customer> customerList = new ArrayList<>();
		CustomerEntity customerEO = null;

		Optional<CustomerEntity> customerObject = customerRepo.findById(id);
		if (customerObject.isPresent()) {
			customerEO = customerObject.get();
		}

		if (null != customerEO) {
			List<AddressEntity> addressList = addressRepo.findByCustomer(customerEO);
			if (null != addressList && addressList.size() > 0) {
				for (AddressEntity address : addressList) {
					Customer customer = new Customer();
					customer.setCustomerId(customerEO.getId().toString());
					customer.setName(customerEO.getName());
					customer.setAddress(address.getDetails());
					customerList.add(customer);
				}

			}
		}

		return customerList;
	}
	
	public Customer saveCustomer (Customer customer) {
		CustomerEntity customerEO = customerRepo.findByName(customer.getName());
		if (null != customerEO) {
			customerEO = new CustomerEntity();
		}
		customerEO.setName(customer.getName());
		
		AddressEntity addressEO = new AddressEntity();
		addressEO.setCustomer(customerEO);
		addressEO.setDetails(customer.getAddress());
		Set<AddressEntity> addresses = new HashSet<>();
		addresses.add(addressEO);
		customerEO.setAddress(addresses);
		
		customerRepo.save(customerEO);
		
		return customer;
	}
	
	public List<Customer> uploadAllCustomers(MultipartFile file) {
		List<Customer> customerList = new ArrayList<>();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
			Stream<String> lines = fileReader.lines();
			Map<String, String> map = lines.map(s -> s.split(",")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
			
			for (String key: map.keySet()) {
				Set<AddressEntity> set = new HashSet<>();

				Customer customerDTO = new Customer();
				CustomerEntity customer = new CustomerEntity();
				AddressEntity addressEO = new AddressEntity();

				customer.setName(key);
				customer = customerRepo.save(customer);

				addressEO.setCustomer(customer);
				addressEO.setDetails(map.get(key));
				set.add(addressEO);

				addressRepo.save(addressEO);
				customerDTO.setCustomerId(customer.getId().toString());
				customerDTO.setName(key);
				customerDTO.setAddress(addressEO.getDetails());
				
				customerList.add(customerDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerList;
	}
	
	public String deleteCustomer (Integer id) {
		CustomerEntity customer = null;
		Optional<CustomerEntity> customerObject = customerRepo.findById(id);
		if (customerObject.isPresent()) {
			customer = customerObject.get();
		}

		if (null != customer) {
			List<AddressEntity> addressList = addressRepo.findByCustomer(customer);
			if (null != addressList && addressList.size() > 0) {
				addressRepo.deleteAll(addressList);
			}
			customerRepo.delete(customer);
		} else {
			return "No Customer found to be deleted";			
		}

		return "Customer deleted successfully";
	}

}
