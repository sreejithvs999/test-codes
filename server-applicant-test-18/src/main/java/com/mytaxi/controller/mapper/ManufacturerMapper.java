package com.mytaxi.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.ManufacturerDO;

public class ManufacturerMapper
{

    public static ManufacturerDO makeManufacturerDO(ManufacturerDTO dto)
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO();
        manufacturerDO.setName(dto.getName());
        manufacturerDO.setCountry(dto.getCountry());
        manufacturerDO.setLogo(dto.getLogo());
        return manufacturerDO;
    }


    public static List<ManufacturerDTO> makeManufacturerDTOList(List<ManufacturerDO> list)
    {
        return list.stream().map(ManufacturerMapper::makeManufacturerDTO).collect(Collectors.toList());
    }


    private static ManufacturerDTO makeManufacturerDTO(ManufacturerDO manufacturerDo)
    {
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(manufacturerDo.getId());
        dto.setName(manufacturerDo.getName());
        dto.setLogo(manufacturerDo.getLogo());
        dto.setCountry(manufacturerDo.getCountry());
        return dto;
    }
}
