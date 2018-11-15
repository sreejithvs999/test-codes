package com.mytaxi.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;

public class CarDataMapper
{
    public static CarDTO makeCarDTO(CarDO carDo)
    {
        CarDTO dto = new CarDTO();
        dto.setId(carDo.getId());
        dto.setLicensePlate(carDo.getLicensePlate());
        dto.setSeatCount(carDo.getSeatCount());
        dto.setEngineType(carDo.getEngineType());
        dto.setRating(carDo.getRating());
        dto.setConvertible(carDo.getConvertible());
        ManufacturerDO manufacturerDo = carDo.getManufacturer();
        if (manufacturerDo != null)
        {
            ManufacturerDTO manufactDto = new ManufacturerDTO();
            manufactDto.setId(manufacturerDo.getId());
            manufactDto.setName(manufacturerDo.getName());
            manufactDto.setLogo(manufacturerDo.getLogo());
            manufactDto.setCountry(manufacturerDo.getCountry());
            dto.setManufacturer(manufactDto);
        }

        if (carDo.getDriver() != null)
        {
            dto.setDriverName(carDo.getDriver().getUsername());
        }
        return dto;
    }


    public static List<CarDTO> makeCarDTOList(List<CarDO> list)
    {
        return list.stream().map(CarDataMapper::makeCarDTO).collect(Collectors.toList());
    }


    public static CarDO makeCarDO(CarDTO carDto)
    {
        CarDO carDo = new CarDO(carDto.getLicensePlate(), carDto.getSeatCount(), carDto.getConvertible(), carDto.getEngineType());
        carDo.setId(carDto.getId());
        carDo.setRating(carDto.getRating());
        if (carDto.getManufacturer() != null)
        {
            carDo.setManufacturer(new ManufacturerDO());
            carDo.getManufacturer().setId(carDto.getManufacturer().getId());
        }
        return carDo;
    }
}
