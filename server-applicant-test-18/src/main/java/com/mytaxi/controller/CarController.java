package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.CarDataMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.CarService;

/**
 * 
 * @author Sreejith VS
 *
 * Rest end point for Car related operations.
 */
@RestController
@RequestMapping("/v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping
    public List<CarDTO> getActiveCars()
    {
        return CarDataMapper.makeCarDTOList(carService.getActiveCars());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO saveCar(@Valid @RequestBody CarDTO carDto) throws ConstraintsViolationException
    {
        carDto.setId(null);
        CarDO carDo = carService.createCar(CarDataMapper.makeCarDO(carDto));
        return CarDataMapper.makeCarDTO(carDo);
    }


    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable Long carId, @Valid @RequestBody CarDTO carDto) throws EntityNotFoundException, ConstraintsViolationException
    {
        carDto.setId(carId);
        CarDO car = carService.updateCar(CarDataMapper.makeCarDO(carDto));
        return CarDataMapper.makeCarDTO(car);
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable Long carId) throws EntityNotFoundException
    {
        carService.deleteCar(carId);
    }
}
