package ru.velkomfood.dms.cache.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDetailRepository extends CrudRepository<Detail, Long> {

    List<Detail> findAll();
    Detail findDetailById(long id);
    List<Detail> findDetailByIdBetween(long low, long high);
    List<Detail> findDetailByIdAndCharacteristicLike(long id, String characteristic);
    List<Detail> findDetailByCharacteristicLike(String characteristic);

}
