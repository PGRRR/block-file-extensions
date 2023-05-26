package com.pgrrr.flow.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileInfo {
    private Long id;
    private String name;
    private String originalName;
    private String path;
}
