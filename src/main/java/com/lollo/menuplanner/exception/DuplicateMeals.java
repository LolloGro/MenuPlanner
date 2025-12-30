package com.lollo.menuplanner.exception;

public class DuplicateMeals extends RuntimeException{

    public DuplicateMeals(String mealName){
        super("Meal with name " + mealName + " already exists");
    }
}
