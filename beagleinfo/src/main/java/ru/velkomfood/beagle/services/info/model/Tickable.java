package ru.velkomfood.beagle.services.info.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Tickable extends CrudRepository<Tick, Long> {

    List<Tick> findTickByCurrentDateBetweenAndCurrentTimeBetween(
            java.sql.Date fromDate,
            java.sql.Date toDate,
            java.sql.Time timeLow,
            java.sql.Time timeHigh
    );

}
