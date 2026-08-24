package com.restaurantplatform.restaurant_service.restaurant.dtos;

import com.restaurantplatform.restaurant_service.enums.AveragePriceRangePerPerson;
import com.restaurantplatform.restaurant_service.enums.CuisineType;
import com.restaurantplatform.restaurant_service.enums.Currency;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RestaurantResponse(

        Long id,
        String name,
        String description,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        String phone,
        AveragePriceRangePerPerson averagePriceRangePerPerson,
        Currency currency,
        CuisineType cuisineType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
