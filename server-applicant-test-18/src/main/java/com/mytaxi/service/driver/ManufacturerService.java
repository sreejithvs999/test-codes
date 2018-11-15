package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface ManufacturerService
{

    public ManufacturerDO create(ManufacturerDO manufactDO) throws ConstraintsViolationException;


    public ManufacturerDO getManufacturer(Long id) throws EntityNotFoundException;


    public List<ManufacturerDO> getAllManufacturers();


    public void deleteManufacturer(Long manufacturerId) throws EntityNotFoundException;
}
