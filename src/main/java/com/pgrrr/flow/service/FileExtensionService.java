package com.pgrrr.flow.service;

import static java.util.stream.Collectors.toList;

import com.pgrrr.flow.domain.FileExtension;
import com.pgrrr.flow.dto.FileExtensionRequestDto;
import com.pgrrr.flow.dto.FileExtensionResponseDto;
import com.pgrrr.flow.exception.DuplicateException;
import com.pgrrr.flow.exception.ErrorCode;
import com.pgrrr.flow.exception.ExceedException;
import com.pgrrr.flow.mapper.FileExtensionMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileExtensionService {
    @NonNull private final FileExtensionMapper fileExtensionMapper;

    public List<FileExtensionResponseDto> getExtensionList() {
        return fileExtensionMapper.selectExtensionList().stream()
                .map(FileExtension::toDto)
                .collect(toList());
    }

    @Transactional
    public void addExtension(FileExtensionRequestDto fileExtensionRequestDto) {
        if (isExtensionLimitExceeded() && !fileExtensionRequestDto.getIsStatic()) {
            throw new ExceedException(ErrorCode.EXCEED_EXTENSION_LIMIT);
        }
        isExtensionExist(fileExtensionRequestDto.getName());
        fileExtensionMapper.insertExtension(fileExtensionRequestDto.toEntity());
    }

    private void isExtensionExist(String name) {
        fileExtensionMapper
                .findExtensionByName(name)
                .ifPresent(
                        extension -> {
                            throw new DuplicateException(ErrorCode.DUPLICATE_EXTENSION);
                        });
    }

    private boolean isExtensionLimitExceeded() {
        return fileExtensionMapper.countExtension() >= 10;
    }

    @Transactional
    public void removeExtension(String name) {
        fileExtensionMapper.deleteExtension(name);
    }
}
