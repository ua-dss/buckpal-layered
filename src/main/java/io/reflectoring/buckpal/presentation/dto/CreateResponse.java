package io.reflectoring.buckpal.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateResponse(boolean success, AccountInfo account, String errorMessage) {
}
