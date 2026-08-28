package com.restaurantplatform.restaurant_service.restaurant;

import com.restaurantplatform.restaurant_service.enums.CuisineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>,
                                              JpaSpecificationExecutor<Restaurant> {

    List<Restaurant> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description
    );

    List<Restaurant> findByCuisineType(CuisineType cuisineType);
    @Query(value = """
        SELECT *
        FROM restaurants
        WHERE ST_DWithin(
            location::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
            :radius
        )
        ORDER BY ST_Distance(
            location::geography,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        )
        """, nativeQuery = true)
    List<Restaurant> findNearbyRestaurants(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius
    );

    @Query(value = """
        SELECT *
        FROM restaurants r
        WHERE
            (
                :query IS NULL
                OR :query = ''
                OR LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))
            )
        AND (
                :city IS NULL
                OR :city = ''
                OR LOWER(r.city) = LOWER(:city)
            )
        AND (
                :cuisineType IS NULL
                OR r.cuisine_type = :cuisineType
            )
        AND (
                :priceRange IS NULL
                OR r.average_price_range_per_person = :priceRange
            )
        AND (
                :latitude IS NULL
                OR :longitude IS NULL
                OR :radius IS NULL
                OR ST_DWithin(
                    r.location::geography,
                    ST_SetSRID(
                        ST_MakePoint(:longitude, :latitude),
                        4326
                    )::geography,
                    :radius
                )
            )
        ORDER BY
            CASE
                WHEN :latitude IS NOT NULL
                 AND :longitude IS NOT NULL
                 AND :radius IS NOT NULL
                THEN ST_Distance(
                    r.location::geography,
                    ST_SetSRID(
                        ST_MakePoint(:longitude, :latitude),
                        4326
                    )::geography
                )
                ELSE NULL
            END
        """, nativeQuery = true)
    List<Restaurant> searchRestaurants(
            @Param("query") String query,
            @Param("city") String city,
            @Param("cuisineType") String cuisineType,
            @Param("priceRange") String priceRange,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius
    );
}
