package com.example.starter.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final ConversionService conversionService;

    @NotNull
    public <T> T requiredConvert(Object source, Class<T> target) {
        return Objects.requireNonNull(this.convert(source, target));
    }

    @Nullable
    public <T> T convert(Object source, Class<T> target) {
        return conversionService.convert(source, target);
    }
}