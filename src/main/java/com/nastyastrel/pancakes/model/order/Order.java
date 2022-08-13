package com.nastyastrel.pancakes.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nastyastrel.pancakes.model.pancake.Pancake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<Pancake> pancakes = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;

    @Column(name = "users_id")
    private Long userId;

    public Order(Long orderId, List<Pancake> pancakes, LocalDateTime creationDate) {
        this.orderId = orderId;
        this.pancakes = pancakes;
        this.creationDate = creationDate;
    }

    public Order(List<Pancake> pancakes, String description, LocalDateTime creationDate, Long userId) {
        this.pancakes = pancakes;
        this.description = description;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                '}';
    }
}
