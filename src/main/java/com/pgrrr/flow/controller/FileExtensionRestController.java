package com.pgrrr.flow.controller;

import com.pgrrr.flow.dto.FileExtensionRequestDto;
import com.pgrrr.flow.dto.FileExtensionResponseDto;
import com.pgrrr.flow.service.FileExtensionService;

import jakarta.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 파일 확장자 차단 컨트롤러 */
@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class FileExtensionRestController {

    @NonNull private final FileExtensionService fileExtensionService;

    /**
     * 차단된 확장자 목록을 가져오는 메서드
     *
     * @return FileExtensionResponseDto List
     */
    @GetMapping
    public ResponseEntity<List<FileExtensionResponseDto>> getExtension() {
        return ResponseEntity.status(HttpStatus.OK).body(fileExtensionService.getExtensionList());
    }

    /**
     * 차단 확장자를 추가하는 메서드
     *
     * @param fileExtensionRequestDto 차단 확장자 추가 요청 DTO
     * @return ResponseEntity 200 OK
     */
    @PostMapping
    public ResponseEntity<Void> addExtension(
            @Valid @RequestBody FileExtensionRequestDto fileExtensionRequestDto) {
        fileExtensionService.addExtension(fileExtensionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 차단 확장자 등록 헤제하는 메서드
     *
     * @param name 차단을 해제할 확장자 이름
     * @return ResponseEntity 200 OK
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteExtension(@PathVariable String name) {
        fileExtensionService.removeExtension(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
