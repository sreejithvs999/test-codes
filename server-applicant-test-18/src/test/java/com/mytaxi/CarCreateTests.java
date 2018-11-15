package com.mytaxi;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mytaxi.controller.CarController;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MytaxiServerApplicantTestApplication.class)
public class CarCreateTests
{

    @Autowired
    CarController carController;


    @Test
    public void testCreateCar() throws ConstraintsViolationException
    {

        CarDTO carDto = createTestCarDTO();

        final CarDTO carDtoRes = carController.saveCar(carDto);

        assertTrue("Test create car : Car Id is invalid", carDtoRes.getId() > 0);

        Optional<CarDTO> carOpt = carController.getActiveCars().stream().filter(car -> car.getId().equals(carDtoRes.getId())).findFirst();
        assertTrue("Test create car : Car not found", carOpt.isPresent());
        assertTrue("Test create car : Car license plate mismatch", carDto.getLicensePlate().equals(carOpt.get().getLicensePlate()));
    }


    @Test(expected = ConstraintsViolationException.class)
    public void testCreateCarManufacturerError() throws ConstraintsViolationException
    {

        CarDTO carDto = createTestCarDTO();

        carDto.getManufacturer().setId(99999999L);

        carController.saveCar(carDto);
    }


    private static CarDTO createTestCarDTO()
    {
        CarDTO carDto = new CarDTO();
        carDto.setLicensePlate("MUN 8723C");
        carDto.setConvertible(false);
        carDto.setEngineType(EngineType.ELECTRIC);
        ManufacturerDTO manufact = new ManufacturerDTO();
        manufact.setId(2L);
        carDto.setManufacturer(manufact);
        carDto.setRating(4.5);
        carDto.setSeatCount(5);
        return carDto;
    }
}
