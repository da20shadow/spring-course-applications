package com.cashcard.models.entities;

import org.springframework.data.annotation.Id;

public record CashCard(@Id Long id, Double amount) {
}
