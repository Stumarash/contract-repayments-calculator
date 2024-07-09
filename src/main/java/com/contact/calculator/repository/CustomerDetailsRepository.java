package com.contact.calculator.repository;

import com.contact.calculator.model.entities.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Long> {
    CustomerDetailsEntity findByEmail(String email);
}
