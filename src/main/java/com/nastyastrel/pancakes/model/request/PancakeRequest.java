package com.nastyastrel.pancakes.model.request;


import java.util.List;


public record PancakeRequest(List<Long> ingredientsId) {
}
