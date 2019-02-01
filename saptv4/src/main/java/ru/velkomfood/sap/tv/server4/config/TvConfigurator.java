package ru.velkomfood.sap.tv.server4.config;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

@Configuration
public class TvConfigurator {

    private static final Logger LOG = LoggerFactory.getLogger(TvConfigurator.class);

    private Properties sapDestination;
    private Properties jdbcQueries;
    private Properties watchPoints;

    public TvConfigurator() {
        sapDestination = new Properties();
        jdbcQueries = new Properties();
        watchPoints = new Properties();
    }

    @PostConstruct
    public void prepare() {

        try (InputStream is1 = getClass().getResourceAsStream("/sap.properties")) {
            sapDestination.load(is1);
        } catch (IOException ioe1) {
            LOG.error(ioe1.getMessage());
        }

        try (InputStream is2 = getClass().getResourceAsStream("/query-statements.properties")) {
            jdbcQueries.load(is2);
        } catch (IOException ioe2) {
            LOG.error(ioe2.getMessage());
        }

        try (InputStream is3 = getClass().getResourceAsStream("/time-range.properties")) {
            watchPoints.load(is3);
        } catch (IOException ioe3) {
            LOG.error(ioe3.getMessage());
        }

    }

    @Bean
    @Qualifier("timeFormatter")
    public DateTimeFormatter createDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("kk:mm:ss");
    }

    @Bean
    @Qualifier("mainLogger")
    public Logger getLoggerInstance() {
        return LoggerFactory.getLogger("main-logger");
    }

    @Bean
    @Qualifier("sapProperties")
    public Properties getSapDestination() {
        return sapDestination;
    }

    @Bean
    @Qualifier("jdbcQueryProperties")
    public Properties getJdbcQueries() {
        return jdbcQueries;
    }

    @Bean
    @Qualifier("watchProperties")
    public Properties getWatchPoints() {
        return watchPoints;
    }


}
