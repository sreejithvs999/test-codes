package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.ManufacturerMapper;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.ManufacturerService;

/**
 * 
 * Rest end points for Car Manufacturers operations are routed from here.
 * 
 * @author Sreejith VS
 *
 */
@RestController
@RequestMapping("/v1/manufacturer")
public class ManufacturerController
{

    private final ManufacturerService manufacturerService;


    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService)
    {
        this.manufacturerService = manufacturerService;
    }


    @GetMapping()
    public List<ManufacturerDTO> listManufacturers()
    {
        return ManufacturerMapper.makeManufacturerDTOList(manufacturerService.getAllManufacturers());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ManufacturerDTO saveManufacturer(@Valid @RequestBody ManufacturerDTO manufacturerDto) throws ConstraintsViolationException
    {

        ManufacturerDO manufacturerDO = ManufacturerMapper.makeManufacturerDO(manufacturerDto);
        manufacturerDO = manufacturerService.create(manufacturerDO);
        manufacturerDto.setId(manufacturerDO.getId());
        return manufacturerDto;
    }


    @DeleteMapping(value = "/{manufacturerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteManufacturer(@PathVariable Long manufacturerId) throws EntityNotFoundException
    {
        manufacturerService.deleteManufacturer(manufacturerId);
    }
}
