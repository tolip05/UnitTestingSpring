package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {
    @Autowired
    private PartRepository partRepository;
    private PartService partService;
    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
    }

    @Test
    public void partService_savePartWithCorrectValue_returnCorrect() {

        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel actualPart = this.partService.savePart(partServiceModel);
        Assert.assertEquals(partServiceModel.getName(), actualPart.getName());
        Assert.assertEquals(partServiceModel.getPrice(), actualPart.getPrice());

    }

    @Test(expected = Exception.class)
    public void partService_savePartWithNotCorrectValue_returnException() {

        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(null);
        PartServiceModel actualPart = this.partService.savePart(partServiceModel);
    }

    @Test
    public void partService_findByIdCorrectValue_returnCorrect() {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel part = this.partService.savePart(partServiceModel);
        PartServiceModel actualPart = this.partService.findPartById(part.getId());
        Assert.assertEquals(part.getId(), actualPart.getId());
        Assert.assertEquals(part.getName(), actualPart.getName());
        Assert.assertEquals(part.getPrice(), actualPart.getPrice());
    }

    @Test(expected = Exception.class)
    public void partService_findByIdNotCorrectValue_returnException() {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel part = this.partService.savePart(partServiceModel);
        PartServiceModel actualPart = this.partService.findPartById(part.getId() + "id");

    }

    @Test
    public void partService_deletePartById_returnTrue() {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel part = this.partService.savePart(partServiceModel);
        PartServiceModel deletePart = this.partService.deletePart(part.getId());
        Assert.assertEquals(part.getId(), deletePart.getId());
        Assert.assertEquals(part.getName(), deletePart.getName());
        Assert.assertEquals(0, this.partRepository.count());
    }

    @Test(expected = Exception.class)
    public void partService_deletePartByIdWithNotCorrectId_returnException() {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel part = this.partService.savePart(partServiceModel);
        PartServiceModel deletePart = this.partService.deletePart(part.getId() + "id");

    }

    @Test
    public void partService_editPartWithCorrectValue_returnCorrect() {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName("Name");
        partServiceModel.setPrice(BigDecimal.valueOf(10.0));
        PartServiceModel partEntity = this.partService.savePart(partServiceModel);

        PartServiceModel expected = new PartServiceModel();
        expected.setId(partEntity.getId());
        expected.setName("name");
        expected.setPrice(BigDecimal.valueOf(1.0));
        PartServiceModel actualPart = this.partService.editPart(expected);
        Assert.assertEquals(expected.getId(),actualPart.getId());
        Assert.assertEquals(expected.getName(),actualPart.getName());
        Assert.assertEquals(expected.getPrice(),actualPart.getPrice());
    }

}
