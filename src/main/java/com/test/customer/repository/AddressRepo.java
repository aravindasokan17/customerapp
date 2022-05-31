package com.test.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.test.customer.entity.AddressEntity;
import com.test.customer.entity.CustomerEntity;

@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Integer>, JpaSpecificationExecutor<AddressEntity> {
	
	List<AddressEntity> findByCustomer(CustomerEntity customer);

}
