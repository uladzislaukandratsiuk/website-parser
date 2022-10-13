package com.website.parser.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiErrorResponse {

    private final String message;
}
