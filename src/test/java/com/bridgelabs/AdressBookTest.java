package com.bridgelabs;

import com.adressbook.AdressBookData;
import com.adressbook.AdressBookException;
import com.adressbook.AdressBookService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AdressBookTest {
    AdressBookService adressBookService = null;

    @Before
    public void setup() throws Exception {
        adressBookService = new AdressBookService();
    }

    /**
     * Purpose : To test whether the number of records present in the database matches with the expected value
     */
    @Test
    public void givenAdressBookInDB_WhenRetrieved_ShouldMatchpersonCount() {
        List<AdressBookData> employeePayrollData = adressBookService.readEmployeePayrollData(AdressBookService.IOService.DB_IO);
        Assert.assertEquals(4, employeePayrollData.size());
    }
    /**
     * Purpose : To test whether the city is updated in the database and is synced with the DB
     *           - Read the values from the database
     *           - Update the city in the database
     *           - Test whether the database is correctly synced or not
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws AdressBookException {
        List<AdressBookData> employeePayrollData = adressBookService.readEmployeePayrollData(AdressBookService.IOService.DB_IO);
        adressBookService.updateEmployeeSalary("Haresh", 87654);
        boolean result = adressBookService.checkAdressBookInSyncWithDB("Haresh");
        Assert.assertTrue(result);
    }
}
