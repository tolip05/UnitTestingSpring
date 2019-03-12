package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {
    @Autowired
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private ModelMapper modelMapper;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.customerService =
                new CustomerServiceImpl(this.customerRepository,this.modelMapper);
    }
    @Test
    public void customerServiceSaveCustomerWithCorrect_returnCorrect(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer = this.customerService.saveCustomer(customer);
        Assert.assertEquals(customer.getName(),actualCustomer.getName());
        Assert.assertEquals(customer.getBirthDate(),actualCustomer.getBirthDate());
        Assert.assertEquals(1,this.customerRepository.count());
    }
    @Test(expected = Exception.class)
    public void customerServiceSaveWithNullValue_returnException(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName(null);
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer = this.customerService.saveCustomer(customer);
    }
    @Test
    public void customerService_findByIdCorrect_returnCorrect(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer =
                this.customerService.saveCustomer(customer);
        CustomerServiceModel customerById = this.customerService.findCustomerById(actualCustomer.getId());
        Assert.assertEquals(customerById.getId(),actualCustomer.getId());
        Assert.assertEquals(customerById.getName(),actualCustomer.getName());
        Assert.assertEquals(customerById.getBirthDate(),actualCustomer.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_findByIdNotCorrect_returnException(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer =
                this.customerService.saveCustomer(customer);
        CustomerServiceModel customerById = this.customerService.findCustomerById(actualCustomer.getId() + "id");

    }
    @Test
    public void customerService_deleteCustomer_returnCorrect(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer =
                this.customerService.saveCustomer(customer);
        CustomerServiceModel deleteCustomer =
                this.customerService.deleteCustomer(actualCustomer.getId());
        Assert.assertEquals(deleteCustomer.getId(),actualCustomer.getId());
        Assert.assertEquals(0,this.customerRepository.count());
    }
    @Test
    public void customerService_editCorrectCustomer_returnCorrect(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer =
                this.customerService.saveCustomer(customer);
        CustomerServiceModel expected = new CustomerServiceModel();
        expected.setId(actualCustomer.getId());
        expected.setName("Gosho");
        expected.setBirthDate(LocalDate.now());
        expected.setYoungDriver(false);
        CustomerServiceModel editCustomer = this.customerService.editCustomer(expected);

        Assert.assertEquals(expected.getId(),editCustomer.getId());
        Assert.assertEquals(expected.getName(),editCustomer.getName());
        Assert.assertEquals(expected.getBirthDate(),editCustomer.getBirthDate());
        Assert.assertEquals(expected.isYoungDriver(),editCustomer.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void customerService_editWithNonCorrectIdCustomer_returnException(){
        CustomerServiceModel customer = new CustomerServiceModel();
        customer.setName("Pesho");
        customer.setBirthDate(LocalDate.now());
        customer.setYoungDriver(true);
        CustomerServiceModel actualCustomer =
                this.customerService.saveCustomer(customer);
        CustomerServiceModel expected = new CustomerServiceModel();
        expected.setId(actualCustomer.getId() + "id");
        expected.setName("Gosho");
        expected.setBirthDate(LocalDate.now());
        expected.setYoungDriver(false);
        CustomerServiceModel editCustomer = this.customerService.editCustomer(expected);

    }
}
