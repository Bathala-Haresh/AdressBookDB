package com.adressbook;

import java.util.List;

public class AdressBookService {
    private final AdressBookDBService adressBookDBService;
    private List<AdressBookData> adressBookData;

    public AdressBookService() {
        adressBookDBService = AdressBookDBService.getInstance();
    }

    public enum IOService {
        DB_IO;
    }


    /**
     * * Purpose : To get the list of employee payroll from the database
     */
    public List<AdressBookData> readEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            this.adressBookData = adressBookDBService.readData();
        return this.adressBookData;
    }
}
