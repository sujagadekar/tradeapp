package com.example.tradeapp;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

public class TradeController {
    HashMap<String,Trade> allTrade=new HashMap<String,Trade>();

    public boolean checkIfTradeEmpty()
    {
        return allTrade.isEmpty();
    }

    public void checkVersion(Trade t,int version) //throws Exception
    {
        if(t.getVersion()< version)
        {
           System.out.println(t.getVersion()+" is less than "+ version);

        }

    }

    //Check if maturityDate with currenDate
    public boolean checkMaturityDate(Date maturityDate, Date CurrentDate)
    {
        if(CurrentDate.compareTo(maturityDate)>0)
            return false;
        return true;
    }

    public void checkExpiredDates()
    {

        Date currentDate=new Date();
        Stream<Trade> tradeStream = allTrade.values().stream();
        Stream<String> tradeKeys = allTrade.keySet().stream();

        for(String strKey : allTrade.keySet() ){
            if(currentDate.compareTo(allTrade.get(strKey).getMaturityDate())>0)
            {
                Trade t=allTrade.get(strKey);
                t.setExpired('Y');
                allTrade.replace(strKey, t);
            }
        }

    }


    //adding new Trade
    public void addTrade(Trade T) throws Exception
    {
        if(allTrade.containsKey(T.getTradeId()))
        {
            checkVersion(T, allTrade.get(T.getTradeId()).getVersion());

            if(checkMaturityDate(T.getMaturityDate(), allTrade.get(T.getTradeId()).getMaturityDate()))
            {
                allTrade.replace(T.getTradeId(), T);
                System.out.println(T.getTradeId()+" is added to the Store");
            }
            else
            {
                System.out.println("Not able to add "+T.getTradeId()+" in the store as maturity date is lower than current date");
            }
        }
        else
        {

            if(checkMaturityDate(T.getMaturityDate(), T.getCreatedDate()))
            {

                allTrade.put(T.getTradeId(), T);
                System.out.println(T.getTradeId()+" is added to the Store");

            }
            else
            {
                System.out.println("Not able to add "+T.getTradeId()+" in the store as maturity date is lower than current date");
            }
        }

    }

    public Trade getTrade(String tId) throws Exception
    {
        if(allTrade.containsKey(tId))
            return allTrade.get(tId);
        throw new Exception ("Trade with "+tId+" not Found");

    }

    public void printTrade()
    {
        for(String tId : allTrade.keySet())
        {
            System.out.println(allTrade.get(tId).toString());
        }
    }
}
