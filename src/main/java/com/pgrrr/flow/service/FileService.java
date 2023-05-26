package com.pgrrr.flow.service;

import com.pgrrr.flow.domain.FileExtension;
import com.pgrrr.flow.domain.FileInfo;
import com.pgrrr.flow.exception.ErrorCode;
import com.pgrrr.flow.exception.FileTypeCheckException;
import com.pgrrr.flow.mapper.FileExtensionMapper;
import com.pgrrr.flow.mapper.FileMapper;

import lombok.RequiredArgsConstructor;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;
    private final FileExtensionMapper fileExtensionMapper;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension;
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            List<FileExtension> fileExtensionList = fileExtensionMapper.selectExtensionList();
            boolean isAllowedExtension =
                    fileExtensionList.stream()
                            .anyMatch(
                                    fileExtension ->
                                            Objects.equals(fileExtension.getName(), extension));
            if (isAllowedExtension) {
                throw new FileTypeCheckException(ErrorCode.BLOCKED_EXTENSION);
            }
        } else {
            extension = "";
        }
        String savedName = uuid + extension;
        String savedPath = fileDir + savedName;
        FileInfo fileInfo =
                FileInfo.builder()
                        .name(savedName)
                        .originalName(originalFilename)
                        .path(savedPath)
                        .build();
        File uncheckedFile = new File(savedPath);
        multipartFile.transferTo(uncheckedFile);
        try {
            checkFileMimeType(uncheckedFile);
            fileMapper.insertFile(fileInfo);
        } catch (FileTypeCheckException e) {
            if (uncheckedFile.exists()) {
                uncheckedFile.delete();
            }
            throw e;
        }
    }

    public void checkFileMimeType(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        String detectExtension = mimeType.substring(mimeType.lastIndexOf("/") + 1);
        List<FileExtension> fileExtensionList = fileExtensionMapper.selectExtensionList();
        boolean isAllowedExtension =
                fileExtensionList.stream()
                        .anyMatch(
                                fileExtension ->
                                        Objects.equals(fileExtension.getName(), detectExtension));

        if (isAllowedExtension) {
            throw new FileTypeCheckException(ErrorCode.BLOCKED_MIME_TYPE);
        }
    }
}
