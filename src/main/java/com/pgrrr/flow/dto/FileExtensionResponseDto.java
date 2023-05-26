package com.pgrrr.flow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileExtensionResponseDto {
    private Long id;
    private String name;
    private Boolean isStatic;
}
