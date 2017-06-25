package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 23.06.17.
 */
public interface IUserRepository extends CrudRepository<User, String> {

    List<User> findAll();
    List<User> findUserById(String uid);

}
