package com.senyitte.customerservice.services;

import com.senyitte.customerservice.dtos.CustomerRequest;
import com.senyitte.customerservice.dtos.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest dto);
    CustomerResponse updateCustomer(Long id, CustomerRequest dto);
    CustomerResponse getCustomer(Long id);
    List<CustomerResponse> getAllCustomers();
    void deleteCustomer(Long id);

}
