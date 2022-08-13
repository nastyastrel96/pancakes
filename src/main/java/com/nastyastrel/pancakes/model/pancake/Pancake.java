package com.nastyastrel.pancakes.model.pancake;

import com.nastyastrel.pancakes.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pancakes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pancake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long pancakeId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pancake_ingredient",
            joinColumns = @JoinColumn(name = "pancakes_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    public Pancake(Set<Ingredient> ingredients, Order order) {
        this.ingredients = ingredients;
        this.order = order;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pancake pancake = (Pancake) o;
        return Objects.equals(pancakeId, pancake.pancakeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pancakeId);
    }

    @Override
    public String toString() {
        return "Pancake{" +
                "pancakeId=" + pancakeId +
                '}';
    }
}
