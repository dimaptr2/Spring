package ru.velkomfood.fin.cash.server.model.master;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dpetrov on 30.06.17.
 */
public interface IChannelRepository extends CrudRepository<Channel, String> {

    List<Channel> findAll();
    Channel findChannelById(String id);

}
