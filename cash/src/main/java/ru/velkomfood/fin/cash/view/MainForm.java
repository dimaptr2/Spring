package ru.velkomfood.fin.cash.view;

import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;

import java.util.Locale;

/**
 * Created by dpetrov on 03.03.2017.
 */
public class MainForm extends FormLayout {

    private StartUI window;
    private DateField datePricker;

    public MainForm(StartUI window) {
        this.window = window;
    }

    private void addElements() {
        datePricker = new DateField();
        datePricker.setLocale(Locale.getDefault());
    }

}
