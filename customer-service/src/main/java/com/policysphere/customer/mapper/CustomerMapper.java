package com.policysphere.customer.mapper;

import com.policysphere.customer.dto.request.CustomerRequestDTO;
import com.policysphere.customer.dto.request.CustomerUpdateDTO;
import com.policysphere.customer.dto.response.CustomerResponseDTO;
import com.policysphere.customer.entity.Customer;
import com.policysphere.customer.enums.CustomerStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {
        return Customer.builder()
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(dto.getEmail().trim().toLowerCase())
                .phone(dto.getPhone().trim())
                .status(CustomerStatus.ACTIVE)
                .build();
    }

    public void updateEntity(Customer customer, CustomerUpdateDTO dto) {
        customer.setFirstName(dto.getFirstName().trim());
        customer.setLastName(dto.getLastName().trim());
        customer.setEmail(dto.getEmail().trim().toLowerCase());
        customer.setPhone(dto.getPhone().trim());
    }

    public CustomerResponseDTO toResponse(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
