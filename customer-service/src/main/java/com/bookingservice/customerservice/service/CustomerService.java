package com.bookingservice.customerservice.service;

import com.bookingservice.customerservice.exception.BusinessException;
import com.bookingservice.customerservice.model.Customer;
import com.bookingservice.customerservice.repository.CustomerRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public Customer getByUserIdentifier(String id) throws BusinessException {
        if (id == null || id.length() == 0) throw new BusinessException("Identifier not valid");
        return customerRepository.findByUserIdentifier(id);
    }

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public Customer getById(Integer id) throws BusinessException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) throw new BusinessException("No customer with givn id!");
        return customer;
    }

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public Customer createCustomer(Customer customer) throws BusinessException {
        if (customer.getFirstName() == null || customer.getLastName() == null ||
        customer.getFirstName().length() == 0 ||customer.getLastName().length() == 0)
            throw new BusinessException("Customer data not valid!");
        return customerRepository.save(customer);
    }

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public Customer updateCustomer(Customer customer) throws BusinessException {
        if (customer.getFirstName() == null || customer.getLastName() == null ||
                customer.getFirstName().length() == 0 ||customer.getLastName().length() == 0)
            throw new BusinessException("Customer data not valid!");
        return customerRepository.save(customer);
    }

    @CircuitBreaker(name = "customerdb")
    @Bulkhead(name = "customerdb")
    @Retry(name = "customerdb")
    @RateLimiter(name = "customerdb")
    public boolean deleteCustomer(Integer id) throws BusinessException {
        Customer c = customerRepository.findById(id).get();
        if (c == null) throw new BusinessException("Customer with given id doesn't exist!");
        customerRepository.delete(c);
        return true;
    }

}
