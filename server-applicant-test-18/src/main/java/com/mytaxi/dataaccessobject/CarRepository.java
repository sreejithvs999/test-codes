package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mytaxi.domainobject.CarDO;

public interface CarRepository extends JpaRepository<CarDO, Long>
{

    @Query("select c from CarDO c join fetch c.manufacturer m left join fetch c.driver d where c.deleted=?1")
    public List<CarDO> findActiveCars(Boolean deleted);
}
