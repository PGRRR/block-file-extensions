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

    /**
     * 차단 확장자 리스트 반환 메서드
     *
     * @return FileExtensionResponseDto List
     */
    public List<FileExtensionResponseDto> getExtensionList() {
        return fileExtensionMapper.selectExtensionList().stream()
                .map(FileExtension::toDto)
                .collect(toList());
    }

    /**
     * 차단 확장자 추가 메서드
     *
     * @param fileExtensionRequestDto 차단 확장자 추가 요청 DTO
     */
    @Transactional
    public void addExtension(FileExtensionRequestDto fileExtensionRequestDto) {
        if (isExtensionLimitExceeded() && !fileExtensionRequestDto.getIsStatic()) {
            throw new ExceedException(ErrorCode.EXCEED_EXTENSION_LIMIT);
        }
        isExtensionExist(fileExtensionRequestDto.getName());
        fileExtensionMapper.insertExtension(fileExtensionRequestDto.toEntity());
    }

    /**
     * 차단 확장자 중복 확인 메서드
     *
     * @param name 차단 확장자 요청 이름
     */
    private void isExtensionExist(String name) {
        fileExtensionMapper
                .findExtensionByName(name)
                .ifPresent(
                        extension -> {
                            throw new DuplicateException(ErrorCode.DUPLICATE_EXTENSION);
                        });
    }

    /**
     * 커스텀 차단 확장자 최대 개수 확인 메서드
     *
     * @return boolean 최대 초과 여부
     */
    private boolean isExtensionLimitExceeded() {
        return fileExtensionMapper.countExtension() >= 10;
    }

    /**
     * 차단 확장자 등록 해제 메서드
     *
     * @param name 차단을 해제할 확장자 이름
     */
    @Transactional
    public void removeExtension(String name) {
        fileExtensionMapper.deleteExtension(name);
    }
}
