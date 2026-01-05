package com.lollo.menuplanner.util;


public class Capitalize {

    private Capitalize(){}

    public static String capitalizeFirstLetter(String alter){

        if(alter == null || alter.isEmpty()){
            return alter;
        }

        return alter.substring(0, 1).toUpperCase() + alter.substring(1);
    }
}
