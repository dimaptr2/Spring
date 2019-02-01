package ru.velkomfood.sap.tv.server4.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SumView implements Serializable {

    private BigDecimal quantity;
    private BigDecimal inProcess;

    public SumView() {
    }

    public SumView(BigDecimal quantity, BigDecimal inProcess) {
        this.quantity = quantity;
        this.inProcess = inProcess;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getInProcess() {
        return inProcess;
    }

    public void setInProcess(BigDecimal inProcess) {
        this.inProcess = inProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SumView sumView = (SumView) o;
        return quantity.equals(sumView.quantity) &&
                inProcess.equals(sumView.inProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, inProcess);
    }

    @Override
    public String toString() {
        return "SumView{" +
                "quantity=" + quantity +
                ", inProcess=" + inProcess +
                '}';
    }

}
