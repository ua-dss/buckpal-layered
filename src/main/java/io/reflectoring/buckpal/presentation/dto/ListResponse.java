package io.reflectoring.buckpal.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListResponse(boolean success, List<AccountInfo> accounts, String errorMessage) {
}
