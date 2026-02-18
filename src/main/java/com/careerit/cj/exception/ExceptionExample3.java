package com.careerit.cj.exception;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;

import static java.lang.IO.println;

class X implements Closeable {

    @Override
    public void close() throws IOException {
        System.out.println("Close");
    }
    public void showMessage(){
        println("Greeting from show message");
    }

}

public class ExceptionExample3 {

    public static void main(String[] args){

            try(X obj = new X()){
                obj.showMessage();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public static void readJsonData(){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(ExceptionExample3.class.getResourceAsStream("/emp.json"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
