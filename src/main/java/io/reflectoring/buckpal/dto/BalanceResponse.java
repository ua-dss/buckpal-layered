package io.reflectoring.buckpal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceResponse(boolean success, AccountInfoDto account, String errorMessage) {
}
