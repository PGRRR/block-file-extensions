package com.pgrrr.flow.dto;

import com.pgrrr.flow.domain.FileExtension;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileExtensionRequestDto {

    @Size(min = 1, max = 20, message = "확장자 입력 길이는 1에서 20 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9.]*$", message = "확장자는 영문 대소문자, 숫자 및 마침표만 입력할 수 있습니다.")
    private String name;

    @NotNull private Boolean isStatic;

    public FileExtension toEntity() {
        return FileExtension.builder().name(name).isStatic(isStatic).build();
    }
}
