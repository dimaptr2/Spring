package ru.velkomfood.beagle.info.model;

import java.util.List;

/**
 * Created by dpetrov on 21.04.17.
 */

public interface IEventDAO {

    List<Event> getAmount(String mode, int year1, int year2,
                        String month1, String month2,
                        int day1, int day2);

}
