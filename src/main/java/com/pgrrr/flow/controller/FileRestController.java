package com.pgrrr.flow.controller;

import com.pgrrr.flow.service.FileService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Void> uploadFile(@Valid @RequestParam MultipartFile file)
            throws IOException {
        fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
