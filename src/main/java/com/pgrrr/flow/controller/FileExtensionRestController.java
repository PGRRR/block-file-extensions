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

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class FileExtensionRestController {

    @NonNull private final FileExtensionService fileExtensionService;

    @GetMapping
    public ResponseEntity<List<FileExtensionResponseDto>> getExtension() {
        return ResponseEntity.status(HttpStatus.OK).body(fileExtensionService.getExtensionList());
    }

    @PostMapping
    public ResponseEntity<Void> addExtension(
            @Valid @RequestBody FileExtensionRequestDto fileExtensionRequestDto) {
        fileExtensionService.addExtension(fileExtensionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteExtension(@PathVariable String name) {
        fileExtensionService.removeExtension(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
