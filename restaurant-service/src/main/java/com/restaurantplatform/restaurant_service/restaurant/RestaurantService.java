package com.restaurantplatform.restaurant_service.restaurant;

import com.restaurantplatform.restaurant_service.enums.AveragePriceRangePerPerson;
import com.restaurantplatform.restaurant_service.enums.CuisineType;
import com.restaurantplatform.restaurant_service.exception.RestaurantNotFoundException;
import com.restaurantplatform.restaurant_service.restaurant.dtos.CreateRestaurantRequest;
import com.restaurantplatform.restaurant_service.restaurant.dtos.RestaurantResponse;
import com.restaurantplatform.restaurant_service.restaurant.dtos.UpdateRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.name());
        restaurant.setDescription(request.description());
        restaurant.setCity(request.city());
        restaurant.setAddress(request.address());
        restaurant.setLatitude(request.latitude());
        restaurant.setLongitude(request.longitude());
        restaurant.setPhone(request.phone());
        restaurant.setAveragePriceRangePerPerson(request.averagePriceRangePerPerson());
        restaurant.setCurrency(request.currency());
        restaurant.setCuisineType(request.cuisineType());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return mapToResponse(savedRestaurant);
    }

    public RestaurantResponse getRestaurantById(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant with id " + id + " not found"
                        )
                );

        return mapToResponse(restaurant);
    }

    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RestaurantResponse updateRestaurant(
            Long id,
            UpdateRestaurantRequest request
    ) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant with id " + id + " not found"
                        )
                );

        restaurant.setName(request.name());
        restaurant.setDescription(request.description());
        restaurant.setCity(request.city());
        restaurant.setAddress(request.address());
        restaurant.setLatitude(request.latitude());
        restaurant.setLongitude(request.longitude());
        restaurant.setPhone(request.phone());
        restaurant.setAveragePriceRangePerPerson(request.averagePriceRangePerPerson());
        restaurant.setCurrency(request.currency());
        restaurant.setCuisineType(request.cuisineType());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return mapToResponse(updatedRestaurant);
    }

    public void deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant with id " + id + " not found"
                        )
                );

        restaurantRepository.delete(restaurant);
    }

    private RestaurantResponse mapToResponse(Restaurant restaurant) {

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCity(),
                restaurant.getAddress(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                restaurant.getPhone(),
                restaurant.getAveragePriceRangePerPerson(),
                restaurant.getCurrency(),
                restaurant.getCuisineType(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    public List<RestaurantResponse> searchRestaurants(String query) {

        return restaurantRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        query,
                        query
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<RestaurantResponse> getRestaurantsByCuisine(
            CuisineType cuisineType
    ) {

        return restaurantRepository.findByCuisineType(cuisineType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<RestaurantResponse> searchRestaurants(
            String query,
            String city,
            CuisineType cuisineType,
            AveragePriceRangePerPerson priceRange
    ) {

        Specification<Restaurant> specification =
                Specification
                        .where(RestaurantSpecification.hasSearchQuery(query))
                        .and(RestaurantSpecification.hasCity(city))
                        .and(RestaurantSpecification.hasCuisineType(cuisineType))
                        .and(RestaurantSpecification.hasAveragePriceRange(priceRange));

        return restaurantRepository.findAll(specification)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
