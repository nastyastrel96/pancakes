package com.nastyastrel.pancakes.service.report;

import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.pancake.Category;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import com.nastyastrel.pancakes.model.user.Role;
import com.nastyastrel.pancakes.model.user.User;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import com.nastyastrel.pancakes.repository.OrderRepository;
import com.nastyastrel.pancakes.repository.PancakeRepository;
import com.nastyastrel.pancakes.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Set;

@TestConfiguration
public class ReportConfiguration {
    @Bean
    ApplicationRunner reportInit(UserRepository userRepository, OrderRepository orderRepository, PancakeRepository pancakeRepository, IngredientRepository ingredientRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Ingredient ingredient1 = ingredientRepository.save(new Ingredient("Egg", 20.0, Category.BASE, false));
                Ingredient ingredient2 = ingredientRepository.save(new Ingredient("Nutella", 50.0, Category.TOPPING, false));
                Ingredient ingredient3 = ingredientRepository.save(new Ingredient("Raspberry", 150.0, Category.FRUIT, true));
                Ingredient ingredient4 = ingredientRepository.save(new Ingredient("Ice-cream", 70.0, Category.TOPPING, false));
                Ingredient ingredient5 = ingredientRepository.save(new Ingredient("Condensed milk", 80.0, Category.TOPPING, false));
                Ingredient ingredient6 = ingredientRepository.save(new Ingredient("Strawberry", 80.0, Category.FRUIT, true));

                Pancake pancake1 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient2, ingredient6), null));
                Pancake pancake2 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient2, ingredient5), null));
                Pancake pancake3 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient2, ingredient4), null));
                Pancake pancake4 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient3, ingredient5), null));
                Pancake pancake5 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient5, ingredient6), null));
                Pancake pancake6 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient2, ingredient3), null));
                Pancake pancake7 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient3, ingredient5), null));
                Pancake pancake8 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient2, ingredient3), null));
                Pancake pancake9 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient4, ingredient5), null));
                Pancake pancake10 = pancakeRepository.save(new Pancake(Set.of(ingredient1, ingredient5, ingredient6), null));

                userRepository.save(new User(1L, "Anastasiia", "Streltsova", "nastyastrel", "$2a$12$vKv183JoxaQBIsmD1huxROuIqu5H/2MY1zivtjO.5UU4UeRp2i7tK", true, Role.CUSTOMER, null));

                Order order1 = orderRepository.save(new Order(null, "", LocalDateTime.now(), 1L));//
                pancake1.setOrder(order1);
                pancakeRepository.save(pancake1);
                pancake8.setOrder(order1);
                pancakeRepository.save(pancake8);

                Order order2 = orderRepository.save(new Order(null, "", LocalDateTime.now().minusMonths(1), 1L));
                pancake5.setOrder(order2);
                pancakeRepository.save(pancake5);

                Order order3 = orderRepository.save(new Order(null, "", LocalDateTime.now().minusMonths(3), 1L));
                pancake9.setOrder(order3);
                pancakeRepository.save(pancake9);

                Order order4 = orderRepository.save(new Order(null, "", LocalDateTime.now(), 1L));//
                pancake10.setOrder(order4);
                pancakeRepository.save(pancake10);

                Order order5 = orderRepository.save(new Order(null, "", LocalDateTime.now().plusMonths(1), 1L));
                pancake2.setOrder(order5);
                pancakeRepository.save(pancake2);

                Order order6 = orderRepository.save(new Order(null, "", LocalDateTime.now(), 1L));//
                pancake7.setOrder(order6);
                pancakeRepository.save(pancake7);
                pancake4.setOrder(order6);
                pancakeRepository.save(pancake4);

                Order order7 = orderRepository.save(new Order(null, "", LocalDateTime.now().plusMonths(5), 1L));
                pancake6.setOrder(order7);
                pancakeRepository.save(pancake6);

                Order order8 = orderRepository.save(new Order(null, "", LocalDateTime.now(), 1L));//
                pancake3.setOrder(order8);
                pancakeRepository.save(pancake3);
            }
        };
    }
}
