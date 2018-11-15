package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{

    private Long id;

    @NotNull(message = "License plate cannot be null.")
    private String licensePlate;

    @Range(min = 2, max = 20, message = "Seat count value not acceptible.")
    @NotNull(message = "Seat count cannot be null.")
    private Integer seatCount;

    private Boolean convertible = Boolean.FALSE;

    private Double rating = 0.0;

    @NotNull(message = "Engine type cannot be null.")
    private EngineType engineType;

    @NotNull
    private ManufacturerDTO manufacturer;

    private String driverName;


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Double getRating()
    {
        return rating;
    }


    public void setRating(Double rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public ManufacturerDTO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDTO manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public String getDriverName()
    {
        return driverName;
    }


    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }
}
