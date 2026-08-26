package com.restaurantplatform.restaurant_service.restaurant;

import com.restaurantplatform.restaurant_service.enums.AveragePriceRangePerPerson;
import com.restaurantplatform.restaurant_service.enums.CuisineType;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecification {

    public static Specification<Restaurant> hasSearchQuery(String query) {

        return (root, criteriaQuery, criteriaBuilder) -> {

            if (query == null || query.isBlank()) {
                return null;
            }

            String search = "%" + query.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            search
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            search
                    )
            );
        };
    }

    public static Specification<Restaurant> hasCuisineType(
            CuisineType cuisineType) {

        return (root, criteriaQuery, criteriaBuilder) -> {

            if (cuisineType == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("cuisineType"),
                    cuisineType
            );
        };
    }

    public static Specification<Restaurant> hasCity(String city) {

        return (root, query, criteriaBuilder) -> {

            if (city == null || city.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("city")),
                    city.toLowerCase()
            );
        };
    }

    public static Specification<Restaurant> hasAveragePriceRange(
            AveragePriceRangePerPerson priceRange) {

        return (root, criteriaQuery, criteriaBuilder) -> {

            if (priceRange == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("averagePriceRangePerPerson"),
                    priceRange
            );
        };
    }

    public static Specification<Restaurant> withinRadius(
            BigDecimal latitude,
            BigDecimal longitude,
            Double radius
    ) {

        return (root, query, criteriaBuilder) -> {

            if (latitude == null || longitude == null || radius == null) {
                return null;
            }

            Expression<Double> distance = criteriaBuilder.function(
                    "calculate_distance",
                    Double.class,
                    root.get("latitude"),
                    root.get("longitude"),
                    criteriaBuilder.literal(latitude.doubleValue()),
                    criteriaBuilder.literal(longitude.doubleValue())
            );

            return criteriaBuilder.lessThanOrEqualTo(
                    distance,
                    radius
            );
        };
    }
}