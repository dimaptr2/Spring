package ru.velkomfood.services.mrp2.core;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AlphaTransformer {

    private final BigDecimal ZERO = new BigDecimal(0.00);
    private final BigDecimal UNIT = new BigDecimal(1.000);
    private final BigDecimal MINUS_UNIT = new BigDecimal(-(1.000));

    private final String PATTERN1 = "Запас";

    public String transformLongValueToAlphaRow(long value, int maximum) {

        StringBuilder sb = new StringBuilder(0);

        String txtValue = String.valueOf(value);
        int len = txtValue.length();

        if ( len < maximum) {
            for (int i = 1; i <= (maximum - len); i++) {
                sb.append("0");
            }
        }

        sb.append(txtValue);

        return sb.toString();
    }

    public String transformMonthToString(int month) {

        StringBuilder sb = new StringBuilder(0);

        String txtMonth = String.valueOf(month);

        if (month >= 1 && month < 10) {
            sb.append("0");
        }

        sb.append(txtMonth);

        return sb.toString();
    }

    public BigDecimal getZERO() {
        return ZERO;
    }

    public BigDecimal getUNIT() {
        return UNIT;
    }

    public BigDecimal getMINUS_UNIT() {
        return MINUS_UNIT;
    }

    public String getPATTERN1() {
        return PATTERN1;
    }

}
