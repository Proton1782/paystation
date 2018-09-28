package paystation.domain;

import java.util.HashMap;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int total = 0;
    public HashMap<Integer, Integer> insertedHash = new HashMap<Integer, Integer>();
    
    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        if(insertedHash.containsKey(coinValue)) {
            int curValue = insertedHash.get(coinValue);
            insertedHash.put(coinValue, curValue+=1);
        }    
        else {
            insertedHash.put(coinValue, 1);
        } 
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }


    
    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        total += insertedSoFar;
        reset();
        return r;
    }

    @Override
    public HashMap<Integer, Integer> cancel() {
        HashMap<Integer, Integer> current = insertedHash;
        reset();
        return current;
    }

    private void reset() {
        timeBought = insertedSoFar = 0;
        insertedHash = new HashMap<Integer, Integer>();
    }
    

    public int empty() {
        int oldTotal = total;
        total = 0;
        return oldTotal;
    }
    

    public int getInserted() {
       return insertedSoFar;
   }
   
    public int getCoinValue(int coinValue) {
        return insertedHash.get(coinValue);
    }
   

    public HashMap<Integer, Integer> getInsertedHash() {
        return insertedHash;
    }
    
}
