package com.restaurantplatform.restaurant_service.restaurant;

import com.restaurantplatform.restaurant_service.restaurant.dtos.CreateRestaurantRequest;
import com.restaurantplatform.restaurant_service.restaurant.dtos.RestaurantResponse;
import com.restaurantplatform.restaurant_service.restaurant.dtos.UpdateRestaurantRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest request
    ) {

        RestaurantResponse response =
                restaurantService.createRestaurant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable Long id
    ) {

        RestaurantResponse response =
                restaurantService.getRestaurantById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {

        return ResponseEntity.ok(
                restaurantService.getAllRestaurants()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantRequest request
    ) {

        RestaurantResponse response =
                restaurantService.updateRestaurant(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id
    ) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.noContent().build();
    }
}