package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTests {
    @Autowired
    private PartSaleRepository partSaleRepository;
    @Autowired
    private CarSaleRepository carSaleRepository;

    private ModelMapper modelMapper;

    private SaleService saleService;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.saleService = new SaleServiceImpl(this.carSaleRepository,this.partSaleRepository,this.modelMapper);
    }
    @Test
    public void saleService_saleCarWithCorrect_returnCorrect(){
        CarSaleServiceModel carSaleServiceModel = new CarSaleServiceModel();
        carSaleServiceModel.setDiscount(10.0);
        CarSaleServiceModel expectedModel = this.saleService.saleCar(carSaleServiceModel);
        Assert.assertEquals(carSaleServiceModel.getDiscount(),expectedModel.getDiscount());
        Assert.assertEquals(1,this.carSaleRepository.count());
    }
    @Test
    public void saleService_salePartCorrect_returnCorrect(){
        PartSaleServiceModel part = new PartSaleServiceModel();
        part.setQuantity(100);
        part.setDiscount(10.0);
        PartSaleServiceModel actualPart = this.saleService.salePart(part);

        Assert.assertEquals(part.getQuantity(),actualPart.getQuantity());
        Assert.assertEquals(part.getDiscount(),actualPart.getDiscount());
    }
}
