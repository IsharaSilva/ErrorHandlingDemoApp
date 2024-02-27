package com.navulia.genericerrorhandler.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDto {
    private final int statusCode;
    private final String message;
}
