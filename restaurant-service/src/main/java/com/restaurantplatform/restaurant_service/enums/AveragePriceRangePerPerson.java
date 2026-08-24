package com.restaurantplatform.restaurant_service.enums;

public enum AveragePriceRangePerPerson {
    FROM_5_TO_10("5-10"),
    FROM_10_TO_20("10-20"),
    FROM_20_TO_30("20-30"),
    FROM_30_TO_40("30-40"),
    FROM_40_TO_50("40-50"),
    OVER_50("50+");

    private final String displayValue;

    AveragePriceRangePerPerson(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return  displayValue;
    }
}
