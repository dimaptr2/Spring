package ru.velkomfood.mrp.frontend.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.velkomfood.mrp.frontend.report.view.MainView;

/**
 * Created by dpetrov on 30.06.2016.
 */

@Configuration
public class Configurator {

    @Bean(name = "StartForm")
    public MainView mainView() {
        return new MainView();
    }

}
