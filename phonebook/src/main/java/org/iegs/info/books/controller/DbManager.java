package org.iegs.info.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by petrovdmitry on 18.02.17.
 */
@Component
public class DbManager {

    @Autowired
    private DataSource dataSource;

}
