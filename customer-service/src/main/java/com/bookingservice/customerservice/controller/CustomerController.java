package com.bookingservice.customerservice.controller;

import com.bookingservice.customerservice.exception.BusinessException;
import com.bookingservice.customerservice.model.Customer;
import com.bookingservice.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/findByIdentifier/{id}")
    public Customer getByUserIdentifier(@PathVariable("id") String id) throws BusinessException {
        return customerService.getByUserIdentifier(id);
    }

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getById(@PathVariable("id") Integer id) throws BusinessException {
        return customerService.getById(id);
    }

    @PostMapping("/")
    public Customer createCustomer(@RequestBody Customer customer) throws BusinessException {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/")
    public Customer updateCustomer(@RequestBody Customer customer) throws BusinessException {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteCustomer(@PathVariable("id") Integer id) throws BusinessException {
        return customerService.deleteCustomer(id);
    }

}
