package com.lollo.menuplanner.exception;

public class DuplicateMealsException extends RuntimeException {

    public DuplicateMealsException(String mealName) {
        super("Meal with name " + mealName + " already exists");
    }
}
