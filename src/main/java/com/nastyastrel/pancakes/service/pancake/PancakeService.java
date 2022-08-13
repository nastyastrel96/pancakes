package com.nastyastrel.pancakes.service.pancake;

import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import com.nastyastrel.pancakes.model.pancake.PancakeDto;
import com.nastyastrel.pancakes.model.request.PancakeRequest;

import java.util.List;

public interface PancakeService {
    List<PancakeDto> findAll();

    PancakeDto findById(Long pancakeId);

    void save(PancakeRequest pancakeRequest);

    PancakeDto updatePancake(Long pancakeId, PancakeRequest pancakeRequest);

    void deletePancake(Long pancakeId);

    void addPancakesToOrder(List<Pancake> pancakes, Order order);

    boolean hasValidIngredients(Long pancakeId);

    boolean isHealthyPancake(PancakeDto pancakeDto);

    boolean checkIfPancakeIsVacant(List<Long> pancakesId);
}
