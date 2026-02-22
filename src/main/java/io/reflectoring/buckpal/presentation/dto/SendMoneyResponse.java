package io.reflectoring.buckpal.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendMoneyResponse(boolean success, AccountInfo source, AccountInfo target, String errorMessage) {
}
