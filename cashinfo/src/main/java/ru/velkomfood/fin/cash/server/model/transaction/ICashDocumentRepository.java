package ru.velkomfood.fin.cash.server.model.transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 26.06.17.
 */
public interface ICashDocumentRepository extends CrudRepository<CashDocument, Long> {

    List<CashDocument> findAll();

    CashDocument findCashDocumentById(long id);

    List<CashDocument> findCashDocumentByPostingDate(java.sql.Date postingDate);

    List<CashDocument> findCashDocumentByIdBetween(long low, long high);

    List<CashDocument> findCashDocumentByPostingDateBetween(java.sql.Date fromDate, java.sql.Date toDate);

}
