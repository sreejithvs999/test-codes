package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.EngineType;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = {"license_plate"}))
public class CarDO
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false, unique = true, name = "license_plate")
    @NotNull(message = "License plate cannot be null.")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Seat count cannot be null.")
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean convertible;

    @Column(nullable = false)
    private Double rating = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ManufacturerDO manufacturer;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated = ZonedDateTime.now();

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinTable(
        
        name = "driver_car", joinColumns = {@JoinColumn(name = "car_id")},
        inverseJoinColumns = {@JoinColumn(name = "driver_id")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"driver_id", "car_id"})})
    private DriverDO driver;


    public CarDO()
    {

    }


    public CarDO(String licensePlate, Integer seatCount, Boolean convertible, EngineType engineType)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.engineType = engineType;
        dateUpdated = null;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public void setDateCreated(ZonedDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
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


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDO manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public ZonedDateTime getDateUpdated()
    {
        return dateUpdated;
    }


    public void setDateUpdated(ZonedDateTime dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }


    public DriverDO getDriver()
    {
        return driver;
    }


    public void setDriver(DriverDO driver)
    {
        this.driver = driver;
    }

}
