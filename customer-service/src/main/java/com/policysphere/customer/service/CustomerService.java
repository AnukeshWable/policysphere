package com.policysphere.customer.service;

import com.policysphere.customer.dto.request.CustomerRequestDTO;
import com.policysphere.customer.dto.request.CustomerUpdateDTO;
import com.policysphere.customer.dto.response.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    CustomerResponseDTO getCustomerById(Long id);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO request);

    CustomerResponseDTO deactivateCustomer(Long id);
}
