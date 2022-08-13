package com.nastyastrel.pancakes.service.pancake;

import com.nastyastrel.pancakes.exception.*;
import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.pancake.*;
import com.nastyastrel.pancakes.model.request.PancakeRequest;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import com.nastyastrel.pancakes.repository.PancakeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PancakeServiceImpl implements PancakeService {
    private final PancakeRepository pancakeRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public List<PancakeDto> findAll() {
        return pancakeRepository.findAll().stream().map(PancakeDto::from).toList();
    }

    @Override
    public PancakeDto findById(Long pancakeId) {
        Pancake pancake = pancakeRepository.findById(pancakeId).orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString()));
        return PancakeDto.from(pancake);
    }

    @Override
    public void save(PancakeRequest pancakeRequest) {
        if (!pancakeRequest.ingredientsId().isEmpty()) {
            Pancake pancake = new Pancake();
            pancakeRequest.ingredientsId().forEach(ingredientId -> {
                Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new NoSuchIdIngredientException(ingredientId.toString()));
                pancake.addIngredient(ingredient);
            });
            pancakeRepository.save(pancake);
        } else {
            throw new NotValidPancakeForCreationException();
        }
    }

    @Override
    public PancakeDto updatePancake(Long pancakeId, PancakeRequest pancakeRequest) {
        if (pancakeRequest.ingredientsId().isEmpty()) {
            throw new NotValidPancakeForCreationException();
        }
        Optional<Pancake> pancakeOptional = pancakeRepository.findById(pancakeId);
        Pancake pancake = pancakeOptional.orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString()));
        if (pancake.getOrder() != null) {
            throw new NotVacantPancakeException();
        }
        Set<Ingredient> updatedIngredients = pancakeRequest.ingredientsId().stream().map(ingredientId -> ingredientRepository.findById(ingredientId).orElseThrow(() -> new NoSuchIdIngredientException(ingredientId.toString()))).collect(Collectors.toSet());
        pancake.setIngredients(updatedIngredients);
        pancakeRepository.save(pancake);
        return PancakeDto.from(pancake);
    }

    @Override
    public void deletePancake(Long pancakeId) {
        Optional<Pancake> pancakeOptional = pancakeRepository.findById(pancakeId);
        Pancake pancake = pancakeOptional.orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString()));
        pancakeRepository.deleteById(pancake.getPancakeId());
    }

    @Override
    public void addPancakesToOrder(List<Pancake> pancakes, Order order) {
        pancakes.forEach(pancake -> {
            pancake.setOrder(order);
            pancakeRepository.save(pancake);
        });
    }

    @Override
    public boolean hasValidIngredients(Long pancakeId) {
        Optional<Pancake> pancakeOptional = pancakeRepository.findById(pancakeId);
        Pancake pancake = pancakeOptional.orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString()));
        return checkValidIngredients(pancake);
    }

    @Override
    public boolean isHealthyPancake(PancakeDto pancakeDto) {
        long healthyIngredients = pancakeDto.ingredients().stream().filter(IngredientDto::healthy).count();
        int allIngredients = pancakeDto.ingredients().size();
        return (double) healthyIngredients / (double) allIngredients * 100.0 > 75.0;
    }

    @Override
    public boolean checkIfPancakeIsVacant(List<Long> pancakesId) {
        return pancakesId.stream().allMatch(pancakeId -> pancakeRepository.findById(pancakeId).orElseThrow(() -> new NoSuchIdPancakeException(pancakeId.toString())).getOrder() == null);
    }

    private boolean checkValidIngredients(Pancake pancake) {
        long baseCount = pancake.getIngredients().stream().filter(ingredient -> ingredient.getCategory().equals(Category.BASE)).count();
        long fillingCount = pancake.getIngredients().stream().filter(ingredient -> ingredient.getCategory().equals(Category.FILLING)).count();
        return baseCount == 1 && fillingCount >= 1;
    }
}
