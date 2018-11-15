package com.mytaxi.service.driver;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

@Service
public class DefaultCarService implements CarService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;

    private final ManufacturerService manufacturerService;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public DefaultCarService(CarRepository carRepository, ManufacturerService manufacturerService)
    {
        this.carRepository = carRepository;
        this.manufacturerService = manufacturerService;
    }


    @Override
    @Transactional(rollbackFor = ConstraintsViolationException.class)
    public CarDO createCar(CarDO carDo) throws ConstraintsViolationException
    {
        try
        {
            carDo = carRepository.saveAndFlush(carDo);
            entityManager.refresh(carDo);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("DataIntegrityViolationException while creating a car: {}", carDo, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return carDo;
    }


    @Override
    @Transactional(rollbackFor = ConstraintsViolationException.class)
    public CarDO updateCar(CarDO carDo) throws EntityNotFoundException, ConstraintsViolationException
    {
        CarDO car;

        try
        {
            car = getCarById(carDo.getId());
            car.setLicensePlate(carDo.getLicensePlate());
            car.setConvertible(carDo.getConvertible());
            car.setDateUpdated(ZonedDateTime.now());
            car.setRating(carDo.getRating());
            car.setSeatCount(carDo.getSeatCount());
            car.setEngineType(carDo.getEngineType());

            if (carDo.getManufacturer() != null)
            {
                ManufacturerDO manufact = manufacturerService.getManufacturer(carDo.getManufacturer().getId());
                car.setManufacturer(manufact);
            }
            car = carRepository.saveAndFlush(car);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("DataIntegrityViolationException while updating a car: {}", carDo, e);
            throw new ConstraintsViolationException(e.getMessage());
        }

        return car;
    }


    @Override
    public CarDO getCarById(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("CarDO not found for id:" + carId));
    }


    @Override
    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteCar(Long carId) throws EntityNotFoundException
    {
        CarDO car = getCarById(carId);
        if (car.getDeleted().equals(Boolean.TRUE))
        {
            throw new EntityNotFoundException("Car is already deleted.");
        }
        car.setDeleted(true);
    }


    @Override
    @Transactional
    public List<CarDO> getActiveCars()
    {
        return carRepository.findActiveCars(Boolean.FALSE);

    }

}
