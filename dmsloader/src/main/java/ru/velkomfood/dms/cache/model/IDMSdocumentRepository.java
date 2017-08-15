package ru.velkomfood.dms.cache.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDMSdocumentRepository extends CrudRepository<DMSdocument, Long> {

    List<DMSdocument> findAll();
    DMSdocument findDMSdocumentById(long id);
    List<DMSdocument> findDMSdocumentByIdBetween(long low, long high);

}
