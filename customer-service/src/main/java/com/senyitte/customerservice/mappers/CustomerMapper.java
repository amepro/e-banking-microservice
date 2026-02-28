package com.senyitte.customerservice.mappers;


import com.senyitte.customerservice.dtos.CustomerRequest;
import com.senyitte.customerservice.dtos.CustomerResponse;
import com.senyitte.customerservice.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toDto(Customer entity) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(entity.getId());
        customerResponse.setFirstName(entity.getFirstName());
        customerResponse.setLastName(entity.getLastName());
        customerResponse.setEmail(entity.getEmail());
        customerResponse.setCreatedAt(entity.getCreatedAt());
        customerResponse.setUpdatedAt(entity.getUpdatedAt());
        return customerResponse;
    }


    public Customer toEntity(CustomerRequest dto) {
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

}
