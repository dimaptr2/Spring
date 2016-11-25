package ru.velkomfood.copc.ckm.server.model;

/**
 * Created by dpetrov on 26.08.2016.
 */
public class AccountEntity {

    private String accountNumber;
    private String description;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
