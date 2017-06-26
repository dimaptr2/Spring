package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 26.06.17.
 */

public interface ICompanyRepository extends CrudRepository<Company, String> {

    List<Company> findAll();
    Company findCompanyById(String id);

}
