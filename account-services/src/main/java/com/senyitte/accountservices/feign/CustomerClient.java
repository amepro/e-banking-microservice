package com.senyitte.accountservices.feign;

import com.senyitte.accountservices.customer.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("/api/customers/{id}")
    Customer getCustomer(@PathVariable Long id);
}
