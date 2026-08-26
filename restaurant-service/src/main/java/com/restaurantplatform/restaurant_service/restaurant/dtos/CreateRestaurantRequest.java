package com.restaurantplatform.restaurant_service.restaurant.dtos;

import com.restaurantplatform.restaurant_service.enums.AveragePriceRangePerPerson;
import com.restaurantplatform.restaurant_service.enums.CuisineType;
import com.restaurantplatform.restaurant_service.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRestaurantRequest(

        @NotBlank
        String name,

        String description,

        @NotBlank
        String city,

        @NotBlank
        String address,

        BigDecimal latitude,

        BigDecimal longitude,

        String phone,

        @NotNull
        AveragePriceRangePerPerson averagePriceRangePerPerson,

        @NotNull
        Currency currency,

        @NotNull
        CuisineType cuisineType
) {
}