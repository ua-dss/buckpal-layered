package io.reflectoring.buckpal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendMoneyResponse(boolean success, AccountInfoDto source, AccountInfoDto target, String errorMessage) {
}
