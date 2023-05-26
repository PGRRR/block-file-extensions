package com.pgrrr.flow.domain;

import com.pgrrr.flow.dto.FileExtensionResponseDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileExtension {

    private Long id;
    private String name;
    private Boolean isStatic;

    public FileExtensionResponseDto toDto() {
        return FileExtensionResponseDto.builder().id(id).name(name).isStatic(isStatic).build();
    }
}
