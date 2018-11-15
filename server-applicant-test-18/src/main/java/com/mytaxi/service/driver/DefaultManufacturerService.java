package com.mytaxi.service.driver;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

@Service
public class DefaultManufacturerService implements ManufacturerService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultManufacturerService.class);

    private final ManufacturerRepository manufacturerRepository;


    @Autowired
    public DefaultManufacturerService(ManufacturerRepository manufacturerRepository)
    {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    @Transactional(rollbackFor = ConstraintsViolationException.class)
    public ManufacturerDO create(ManufacturerDO manufactDO) throws ConstraintsViolationException
    {
        ManufacturerDO manufacture;

        try
        {
            manufacture = manufacturerRepository.save(manufactDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("DataIntegrityViolationException while creating a manufacturer: {}", manufactDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return manufacture;
    }


    @Override
    public ManufacturerDO getManufacturer(Long id) throws EntityNotFoundException
    {
        return manufacturerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not found Manufacturer entity with id=" + id));
    }


    @Override
    public List<ManufacturerDO> getAllManufacturers()
    {
        List<ManufacturerDO> list = new ArrayList<>();
        manufacturerRepository.findAll().forEach(item -> list.add(item));
        return list;
    }


    @Override
    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteManufacturer(Long manufacturerId) throws EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = getManufacturer(manufacturerId);
        if (manufacturerDO.getDeleted())
        {
            throw new EntityNotFoundException("Manufacturer is already deleted.");
        }

    }

}
