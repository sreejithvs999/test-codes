package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotOnlineException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;

    @PersistenceContext
    private EntityManager entityManager;


    public DefaultDriverService(final DriverRepository driverRepository, CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository
            .findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    /**
     * Map car to driver after necessary checking's.
     * 
     * @param driverId
     * @param carId
     */
    @Override
    @Transactional
    public void updateSelectedCar(long driverId, long carId) throws EntityNotFoundException, ConstraintsViolationException
    {
        DriverDO driver = findDriverChecked(driverId);
        if (driver.getDeleted())
        {
            throw new EntityNotFoundException("Driver is not active.");
        }

        if (!driver.getOnlineStatus().equals(OnlineStatus.ONLINE))
        {
            throw new DriverNotOnlineException("Driver is not in online status.");
        }

        CarDO car = carService.getCarById(carId);
        if (car.getDeleted())
        {
            throw new EntityNotFoundException("Car is not active.");
        }

        Optional<DriverDO> carDriverOpt = findDriverOfCar(carId);
        if (carDriverOpt.isPresent())
        {
            LOG.info("A driver is present for the car={}", carId);
            DriverDO carDriver = carDriverOpt.get();
            if (carDriver.getOnlineStatus().equals(OnlineStatus.ONLINE))
            {
                throw new CarAlreadyInUseException("Car is being used by an online driver.");
            }
            else
            {
                carDriver.getCar().setDriver(null);
                carDriver.setCar(null);
                driverRepository.save(carDriver);
                LOG.info("Car is detached from driver={}", carDriver.getId());
            }
        }

        LOG.info("Mapping car={} to driver={}", carId, driverId);
        driver.setCar(car);
        driverRepository.save(driver);
    }


    private Optional<DriverDO> findDriverOfCar(long carId)
    {
        return driverRepository.findByCar(carId);
    }


    /**
     * Remove mapping of car from a driver.
     * 
     * @param driverId
     * @param carId
     */
    @Override
    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void removeCarSelection(long driverId, long carId) throws EntityNotFoundException, ConstraintsViolationException
    {
        DriverDO driver = findDriverChecked(driverId);
        if (driver.getDeleted())
        {
            throw new EntityNotFoundException("Driver is not active.");
        }

        CarDO car = carService.getCarById(carId);
        if (car.getDeleted())
        {
            throw new EntityNotFoundException("Car is not active.");
        }

        driver.setCar(null);
        driverRepository.save(driver);
    }


    /**
     * Search DriverDO's using attributes.
     * 
     * @param username
     * @param onlineStatus
     * @param licensePlate
     * @param minRating
     */
    @Override
    public List<DriverDO> searchDrivers(String username, OnlineStatus onlineStatus, String licensePlate, double minRating)
    {
        StringBuilder queryStr = new StringBuilder(1024);

        queryStr.append("select d from DriverDO d left outer join d.car c where d.deleted=false ");
        if (!StringUtils.isEmpty(username))
        {
            queryStr.append("and d.username=:username ");
        }
        if (onlineStatus != null)
        {
            queryStr.append("and d.onlineStatus=:onlineStatus ");
        }
        if (!StringUtils.isEmpty(licensePlate))
        {
            queryStr.append("and c.licensePlate=:licensePlate ");
        }
        if (minRating > 0)
        {
            queryStr.append("and c.rating > :minRating");
        }

        TypedQuery<DriverDO> jpaQuery = entityManager.createQuery(queryStr.toString(), DriverDO.class);
        if (!StringUtils.isEmpty(username))
        {
            jpaQuery.setParameter("username", username);
        }
        if (onlineStatus != null)
        {
            jpaQuery.setParameter("onlineStatus", onlineStatus);
        }
        if (!StringUtils.isEmpty(licensePlate))
        {
            jpaQuery.setParameter("licensePlate", licensePlate);
        }
        if (minRating > 0)
        {
            jpaQuery.setParameter("minRating", minRating);
        }
        return jpaQuery.getResultList();
    }
}
