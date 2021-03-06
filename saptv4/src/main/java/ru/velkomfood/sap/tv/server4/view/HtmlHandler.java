package ru.velkomfood.sap.tv.server4.view;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.velkomfood.sap.tv.server4.controller.DbProcessor;
import ru.velkomfood.sap.tv.server4.model.MaterialTotals;
import ru.velkomfood.sap.tv.server4.model.SumView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@Controller
public class HtmlHandler {

    private DbProcessor dbProcessor;

    private Logger logger;
    private Properties timeRange;
    private DateTimeFormatter timeFormatter;

    @Autowired
    public HtmlHandler(DbProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    @Autowired
    @Qualifier("mainLogger")
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Autowired
    @Qualifier("watchProperties")
    public void setTimeRange(Properties timeRange) {
        this.timeRange = timeRange;
    }

    @Autowired
    @Qualifier("timeFormatter")
    public void setTimeFormatter(DateTimeFormatter timeFormatter) {
        this.timeFormatter = timeFormatter;
    }

// HTML request section

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("currentDate", transformDateToRussianFormat(LocalDate.now()));
        return "index";
    }

    @GetMapping("/deadline-shipments")
    public String readCurrentShipments(Model model, HttpServletResponse response) {

        LocalDate currentDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate.toString());
        java.sql.Time[] timeRange = chooseTimeRange(LocalTime.now());

        List<MaterialTotals> queryResult = dbProcessor.selectShipmentsAtDateTimeBetween(
                java.sql.Date.valueOf(currentDate.toString()), timeRange[0], timeRange[1]
        );

        List<TotalsHtmlView> resultingList = createResultingList(queryResult);
        BigDecimal[] totalSums = readTotalSumsFromDatabase(sqlDate, sqlDate);

        // Prepare the HTML template
        String dateAndTimeInfo = "Упаковка продукции на " + transformDateToRussianFormat(currentDate);
        model.addAttribute("dateAndInfo", dateAndTimeInfo);
        model.addAttribute("time1", timeRange[0]);
        model.addAttribute("time2", timeRange[1]);
        createHtmlTemplateWithParameters(resultingList, model, totalSums, response);

        return "shipments";
    }

    @GetMapping("/debts")
    public String readInformationAboutDebts(Model model, HttpServletResponse response) {

        LocalDate today = LocalDate.now();

        String dateAndTimeInfo = "Задолженность по отгрузке";
        model.addAttribute("dateAndInfo", dateAndTimeInfo);
        LocalDate[] dates = new LocalDate[3];
        dates[0] = today.minusDays(2);
        dates[1] = today.minusDays(1);
        dates[2] = today;
        String txtDate1 = transformDateToRussianFormat(dates[0]);
        String txtDate2 = transformDateToRussianFormat(dates[1]);
        LocalTime timeValue = LocalTime.now();
        // Additional information about tomorrow, evening
        if (timeValue.isAfter(LocalTime.parse("18:00:00", timeFormatter))) {
            txtDate2 = txtDate2 + "<br> и " + transformDateToRussianFormat(dates[2]) + " с 20:00 по 23:59";
        }

        List<MaterialTotals> materialTotals = takeInformationAboutDebts(dates, timeValue);
        List<TotalsHtmlView> results = createResultingList(materialTotals);
        BigDecimal[] sums = calculateSumOfList(results);
        // Add the attributes to the HTML template
        model.addAttribute("date1", txtDate1);
        model.addAttribute("date2", txtDate2);
        createHtmlTemplateWithParameters(results, model, sums, response);

        return "debts";
    }

    // private section

    private String transformDateToRussianFormat(LocalDate date) {
        String[] txtDate = date.toString().split("-");
        return (txtDate[2] + "." + txtDate[1] + "." + txtDate[0]);
    }

    private java.sql.Time[] chooseTimeRange(LocalTime moment) {

        final LocalTime watchPoint = LocalTime.parse("18:00:00", timeFormatter);
        java.sql.Time[] sqlTimeRange = new java.sql.Time[2];

        if (moment.isBefore(watchPoint)) {
            sqlTimeRange[0] = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.low1")));
            sqlTimeRange[1] = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.high1")));
        } else {
            sqlTimeRange[0] = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.low2")));
            sqlTimeRange[1] = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.high2")));
        }

        return sqlTimeRange;
    }

    private String createTimeAsString(String time) {
        if (time.length() <= 5) {
            time += ":00";
        }
        return time;
    }

    private List<TotalsHtmlView> createResultingList(List<MaterialTotals> totalsList) {
        final List<TotalsHtmlView> viewList = new ArrayList<>();
        totalsList.forEach(row -> viewList.add(createTotalsView(row)));
        totalsList.clear();
        return viewList;
    }

    private TotalsHtmlView createTotalsView(MaterialTotals materialTotals) {
        return new TotalsHtmlView(
                String.valueOf(materialTotals.getId()),
                materialTotals.getDate(),
                materialTotals.getTime(),
                materialTotals.getDescription(),
                materialTotals.getUom(),
                materialTotals.getPacked(),
                materialTotals.getQuantity(),
                materialTotals.getInProcess(),
                materialTotals.getTransferred()
        );
    }

    // Get the totals values from the database
    private BigDecimal[] readTotalSumsFromDatabase(java.sql.Date low, java.sql.Date high) {

        BigDecimal[] values = {BigDecimal.ZERO, BigDecimal.ZERO};

        List<SumView> sumViewList = dbProcessor.selectSumsBetweenDates(low, high);
        if (!sumViewList.isEmpty()) {
            if (sumViewList.get(0).getQuantity() != null) {
                values[0] = sumViewList.get(0).getQuantity();
            }
            if (sumViewList.get(0).getInProcess() != null) {
                values[1] = sumViewList.get(0).getInProcess();
            }
        }

        return values;
    }

    private List<MaterialTotals> takeInformationAboutDebts(LocalDate[] localDates, LocalTime localTime) {

        final LocalTime origin = LocalTime.parse("18:00:00", timeFormatter);
        java.sql.Date sqlDate1 = java.sql.Date.valueOf(localDates[0].toString());
        java.sql.Date sqlDate2 = java.sql.Date.valueOf(localDates[1].toString());

        List<MaterialTotals> totals = dbProcessor.selectShipmentsAtDate(sqlDate1, sqlDate2);

        if (localTime.isAfter(origin)) {
            java.sql.Date sqlDate3 = java.sql.Date.valueOf(localDates[2]);
            java.sql.Time time1 = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.low1")));
            java.sql.Time time2 = java.sql.Time.valueOf(createTimeAsString(timeRange.getProperty("time.high1")));
            List<MaterialTotals> additionalList = dbProcessor
                    .selectShipmentsAtDateTimeBetween(sqlDate3, time1, time2);
            totals.addAll(additionalList);
        }

        return totals;
    }

    // Get sums at date from the database
    private BigDecimal[] calculateSumOfList(List<TotalsHtmlView> viewList) {

        BigDecimal[] sumValues = new BigDecimal[2];
        // initialize the values
        BigDecimal quantity = BigDecimal.ZERO;
        BigDecimal inProcess = BigDecimal.ZERO;

        Iterator<TotalsHtmlView> iterator = viewList.iterator();
        while (iterator.hasNext()) {
            TotalsHtmlView view = iterator.next();
            quantity = quantity.add(view.getQuantity());
            inProcess = inProcess.add(view.getInProcess());
        }
        // rounding to 2 decimal places
        sumValues[0] = quantity.setScale(2, RoundingMode.HALF_UP);
        sumValues[1] = inProcess.setScale(2, RoundingMode.HALF_UP);

        return sumValues;
    }

    // Create the general HTML parameters for /deadline-shipments
    private void createHtmlTemplateWithParameters(
            List<TotalsHtmlView> infoList, Model model, BigDecimal[] sums, HttpServletResponse response) {
        addSumsToHtmlView(model, sums);
        addResultingListIntoHtmlView(infoList, model, response);
    }

    // Add quantity to the template of page
    private void addSumsToHtmlView(Model model, BigDecimal[] sums) {
        model.addAttribute("sumQuantity", sums[0]);
        model.addAttribute("sumInProcess", sums[1]);
    }

    // Add the resulting attributes to the page
    private void addResultingListIntoHtmlView(List<TotalsHtmlView> result, Model model, HttpServletResponse response) {
        model.addAttribute("resultingList", result);
        // auto refresh the page every 2 minutes
        response.addHeader("Refresh", "120");
    }

}
