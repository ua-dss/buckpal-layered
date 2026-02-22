package io.reflectoring.buckpal.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceResponse(boolean success, AccountInfo account, String errorMessage) {
}
