package com.example.tradeapp;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TradeControllerTest {

    TradeController tradeController=new TradeController();
    Date todaysDate= Calendar.getInstance ().getTime ();
    SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");

   @BeforeAll
    public void setUp() throws Exception{    }

    @Test
    public void testIfTradeEmpty()
    {
        assertTrue(tradeController.checkIfTradeEmpty());
    }

    @Test
    public void testAddTrade() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2021");
        Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t1);

        assertEquals(1,tradeController.allTrade.size());
    }

    @Test
    public void testGreaterVersion() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2021");
        Trade t2=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t2);

        //Changing Version as 3 and Changing Counter-Party ID to CP-4
        Trade t3=new Trade("T2",5,"CP-4","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t3);
        assertEquals("CP-4",tradeController.allTrade.get("T2").getCounterPartyId());
    }
    @Test
    public void testSameVersion() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2021");
        //Same Version as before and Changing Counter-Party ID to CP-2
        Trade t4=new Trade("T1",1,"CP-2","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t4);
        assertEquals("CP-2",tradeController.allTrade.get("T1").getCounterPartyId());
    }

    //if the lower version is being received by the store it will reject the trade and
    // throw an exception
    @Test
    public void testLowerVersion() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2021");

        Trade t5=new Trade("T3",5,"CP-3","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t5);
        int sizeofList=tradeController.allTrade.size();
        //Adding Another List
        Trade t6=new Trade("T3",1,"CP-2","B1",maturityDate, todaysDate, 'N');

        assertThrows(Exception.class,()->tradeController.addTrade(t6),"1 is less than 5");

    }

    //If the version is same it will override the existing record.
    @Test
    public void testLowerMaturitySameVersion() throws Exception
    {

        Date maturityDate1=sd.parse("20/06/2021");
        Trade t9=new Trade("T6",1,"CP-2","B1",maturityDate1, todaysDate, 'N');
        tradeController.addTrade(t9);
        Date maturityDate=sd.parse("20/06/2021");
        Trade t10=new Trade("T6",1,"CP-2","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t10);
        assertEquals(maturityDate1,tradeController.allTrade.get("T6").getMaturityDate());
    }

    @Test
    public void testGreaterMaturity() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2021");

        Trade t7=new Trade("T4",5,"CP-4","B3",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t7);

        assertEquals(t7,tradeController.allTrade.get("T4"));

    }

    //Store should not allow the trade which has less maturity date then today date.
    @Test
    public void testLowerMaurity() throws Exception
    {
        Date maturityDate=sd.parse("20/06/2020");
        Trade t8=new Trade("T5",1,"CP-4","B3",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t8);
        assertNull(tradeController.allTrade.get("T5"));
    }

    //Store should automatically update expire flag if in a store the trade crosses the maturity date.
    @Test
    public void testExpiry() throws ParseException
    {
        Date maturityDate=sd.parse("20/06/2020");
        Trade t16=new Trade("T10",6,"CP-4","B1",maturityDate, todaysDate, 'N');
        tradeController.allTrade.put("T10",t16); // hardcoded as it need to be tested and the conditio is false
        tradeController.checkExpiredDates();
        assertEquals('Y',tradeController.allTrade.get("T10").getExpired());
    }
}

