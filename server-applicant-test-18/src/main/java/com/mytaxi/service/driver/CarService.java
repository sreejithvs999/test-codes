package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface CarService
{

    public CarDO createCar(CarDO carDo) throws ConstraintsViolationException;


    public CarDO updateCar(CarDO carDo) throws EntityNotFoundException, ConstraintsViolationException;


    public CarDO getCarById(Long carId) throws EntityNotFoundException;


    public void deleteCar(Long carId) throws EntityNotFoundException;


    public List<CarDO> getActiveCars();

}
