package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {
    @Autowired
    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;
    private SupplierService supplierService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.supplierService =
                new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
    }

    @Test
    public void supplierService_saveSupplierWithCorrectValues_returnCorrect() {
        SupplierService supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
        SupplierServiceModel toSaved = new SupplierServiceModel();
        toSaved.setName("Pesho");
        toSaved.setImporter(true);
        SupplierServiceModel actual = supplierService.saveSupplier(toSaved);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void suppliersService_saveSupplierWithNullValues_ThrowException() {
        SupplierService supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
        SupplierServiceModel toSaved = new SupplierServiceModel();
        toSaved.setName(null);
        toSaved.setImporter(true);
        supplierService.saveSupplier(toSaved);
    }

    @Test
    public void supplierService_editSupplierWithCorectValues_returnCorect() {

        SupplierServiceModel toSaved = new SupplierServiceModel();
        toSaved.setName("Pesho");
        toSaved.setImporter(true);
        SupplierServiceModel supplierServiceModel = this.supplierService.saveSupplier(toSaved);
        supplierServiceModel.setName("Kosta");
        supplierServiceModel.setImporter(false);
        SupplierServiceModel supplierServiceModelEdit =
                this.supplierService.editSupplier(supplierServiceModel);

        Assert.assertEquals(supplierServiceModel.getId(), supplierServiceModelEdit.getId());
        Assert.assertEquals(supplierServiceModel.getName(), supplierServiceModelEdit.getName());
        Assert.assertEquals(supplierServiceModel.isImporter(), supplierServiceModelEdit.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierTest_deleteEntityCorrect_expectedException() {
        SupplierServiceModel toSaved = new SupplierServiceModel();
        toSaved.setName("Kosta");
        toSaved.setImporter(true);
        SupplierServiceModel supplierServiceModel = this.supplierService.saveSupplier(toSaved);
        SupplierServiceModel deleteSupplier = this.supplierService.deleteSupplier(supplierServiceModel.getId());
        this.supplierService.findSupplierById(deleteSupplier.getId());
    }

    @Test
    public void supplierTest_deleteEntityCorrect_CorrectAnswer() {
        SupplierServiceModel toSaved = new SupplierServiceModel();
        toSaved.setName("Kosta");
        toSaved.setImporter(true);
        SupplierServiceModel supplierServiceModel = this.supplierService.saveSupplier(toSaved);
        SupplierServiceModel deleteSupplier =
                this.supplierService.deleteSupplier(supplierServiceModel.getId());
        Assert.assertEquals(0,this.supplierRepository.count());
    }
}
