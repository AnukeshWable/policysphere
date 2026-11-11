package com.policysphere.customer.service.impl;

import com.policysphere.customer.dto.request.CustomerRequestDTO;
import com.policysphere.customer.dto.request.CustomerUpdateDTO;
import com.policysphere.customer.dto.response.CustomerResponseDTO;
import com.policysphere.customer.entity.Customer;
import com.policysphere.customer.enums.CustomerStatus;
import com.policysphere.customer.exception.CustomerNotFoundException;
import com.policysphere.customer.exception.DuplicateEmailException;
import com.policysphere.customer.mapper.CustomerMapper;
import com.policysphere.customer.repository.CustomerRepository;
import com.policysphere.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        Customer customer = customerMapper.toEntity(request);
        try {
            return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
        } catch (DataIntegrityViolationException ex) {
            // race condition: another request inserted the same email
            throw new DuplicateEmailException(email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        return customerMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO request) {
        Customer customer = findOrThrow(id);
        String email = request.getEmail().trim().toLowerCase();
        if (customerRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
        customerMapper.updateEntity(customer, request);
        try {
            return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException(email);
        }
    }

    @Override
    @Transactional
    public CustomerResponseDTO deactivateCustomer(Long id) {
        Customer customer = findOrThrow(id);
        customer.setStatus(CustomerStatus.INACTIVE);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    private Customer findOrThrow(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
