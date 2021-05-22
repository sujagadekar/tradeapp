package com.example.tradeapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        TradeController tradeController=new TradeController();
        Date todaysDate= Calendar.getInstance ().getTime ();
        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");

        //Add new trade T1
        Date maturityDate=sd.parse("10/06/2021");
        Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t1);
        tradeController.printTrade();

        //Add new trade T2
        maturityDate=sd.parse("22/06/2021");
        Trade t2=new Trade("T2",2,"CP-2","B1",maturityDate, todaysDate, 'N');
        tradeController.addTrade(t2);
        tradeController.printTrade();


       //Display trade flow
        System.out.println("\n\n");
        System.out.println("List of trades");
        tradeController.printTrade();
        System.out.println("\n\n");

    }

}

