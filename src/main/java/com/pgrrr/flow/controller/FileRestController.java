package com.pgrrr.flow.controller;

import com.pgrrr.flow.service.FileService;

import jakarta.validation.Valid;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** 파일 컨트롤러 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    /**
     * 파일을 업로드하는 메서드
     *
     * @param file 요청 파일
     * @return ResponseEntity 201 CREATED
     * @throws IOException 파일 업로드 관련 예외 발생
     */
    @PostMapping
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile file)
            throws IOException {
        fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<String>> loadUploadFileList() {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.loadUploadFileList());
    }
}
