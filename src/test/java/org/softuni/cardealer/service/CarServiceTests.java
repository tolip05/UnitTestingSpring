package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {
    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarService carService;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.carService = new CarServiceImpl(this.carRepository,this.modelMapper);
    }

    @Test
    public void carService_saveCarWithCorrectValue_returnCorrect(){
        PartServiceModel partServiceModel = new PartServiceModel();
        List<PartServiceModel> parts = new ArrayList<>();

        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setMake("Make");
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        serviceModel.setParts(parts);
        CarServiceModel expectedModel = new CarServiceModel();
        expectedModel.setMake("Make");
        expectedModel.setModel("Model");
        expectedModel.setParts(parts);
        expectedModel.setTravelledDistance(555L);
        CarServiceModel actualModel = this.carService.saveCar(serviceModel);
        Assert.assertEquals(expectedModel.getMake(),actualModel.getMake());
        Assert.assertEquals(expectedModel.getModel(),actualModel.getModel());
        Assert.assertEquals(expectedModel.getParts(),actualModel.getParts());
    }
    @Test(expected = Exception.class)
    public void carService_saveCarWithNotCorrectValue_returnException() {
        PartServiceModel partServiceModel = new PartServiceModel();
        List<PartServiceModel> parts = new ArrayList<>();

        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        serviceModel.setParts(parts);
        CarServiceModel actualModel = this.carService.saveCar(serviceModel);
    }
    @Test
    public void carService_findByIdCorrectValueId_returnCorect(){
        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setMake("Make");
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        CarServiceModel model = this.carService.saveCar(serviceModel);
        CarServiceModel actual = this.carService.findCarById(model.getId());
        Assert.assertEquals(model.getModel(),actual.getModel());
        Assert.assertEquals(model.getMake(),actual.getMake());
        Assert.assertEquals(model.getTravelledDistance(),actual.getTravelledDistance());

    }
    @Test(expected = Exception.class)
    public void carService_findById_WithNotCorrectId_returnException(){
        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setMake("Make");
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        CarServiceModel model = this.carService.saveCar(serviceModel);
        CarServiceModel actual = this.carService.findCarById(model.getId()+"string");
    }
    @Test
    public void carService_deleteCarCorrect_returnTrue(){
        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setMake("Make");
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        CarServiceModel model = this.carService.saveCar(serviceModel);
        CarServiceModel carServiceModel = this.carService.deleteCar(model.getId());
        Assert.assertEquals(0,this.carRepository.count());
    }
    @Test(expected = Exception.class)
    public void carService_deleteCarWithNotCorectId_returnException(){
        CarServiceModel serviceModel = new CarServiceModel();
        serviceModel.setMake("Make");
        serviceModel.setModel("Model");
        serviceModel.setTravelledDistance(555L);
        CarServiceModel model = this.carService.saveCar(serviceModel);
        CarServiceModel carServiceModel = this.carService.deleteCar(model.getId() + "id");
    }
    @Test
    public void carService_testEditCarCorrect_returnCorrect(){
        CarServiceModel carServiceModel = new CarServiceModel();
        carServiceModel.setModel("Model");
        carServiceModel.setMake("Make");
        carServiceModel.setTravelledDistance(555L);
        carServiceModel.setParts(new ArrayList<PartServiceModel>());
        CarServiceModel saveCar = this.carService.saveCar(carServiceModel);
        CarServiceModel carEdit = new CarServiceModel();
        carEdit.setId(saveCar.getId());
        carEdit.setMake("make");
        carEdit.setModel("model");
        carEdit.setTravelledDistance(666L);
        carEdit.setParts(new ArrayList<PartServiceModel>());
        CarServiceModel actualCar = this.carService.editCar(carEdit);
        Assert.assertEquals(carEdit.getId(),actualCar.getId());
        Assert.assertEquals(carEdit.getMake(),actualCar.getMake());
        Assert.assertEquals(carEdit.getModel(),actualCar.getModel());
        Assert.assertEquals(carEdit.getTravelledDistance(),actualCar.getTravelledDistance());
    }
}
