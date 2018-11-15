package com.mytaxi.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.ManufacturerDO;

/**
 * 
 * DAO for Manufacturer data.
 * 
 * @author Sreejith VS
 *
 */
public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{

}
