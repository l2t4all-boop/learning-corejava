package com.careerit.cj.walletapp;

public class InsufficientFundException extends RuntimeException{

    public InsufficientFundException(String message){
        super(message);
    }

    public InsufficientFundException(){
        super("Insufficient fund");
    }
}
