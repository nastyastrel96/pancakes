package com.nastyastrel.pancakes.model.request;


import java.util.List;

public record OrderCreateRequest(List<Long> pancakesId, String description) {
    public OrderCreateRequest(List<Long> pancakesId) {
        this(pancakesId, null);
    }
}
