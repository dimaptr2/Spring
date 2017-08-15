package ru.velkomfood.dms.cache.controller;

import org.springframework.stereotype.Component;

@Component
public class AlphaTransformator {

    public String transformToExternalForm(long value, int maxSize) {

        StringBuilder sb = new StringBuilder(0);
        String externalValue = String.valueOf(value);

        if (maxSize > externalValue.length()) {

            int times = maxSize - externalValue.length();
            for (int i = times; i > 0; i--) {
                sb.append("0");
            }
            sb.append(externalValue);

        } else if (maxSize == externalValue.length()) {
            sb.append(externalValue);
        }

        return sb.toString();
    }

    public long transformToInternalForm(String value) {
        return Long.parseLong(value);
    }

}
