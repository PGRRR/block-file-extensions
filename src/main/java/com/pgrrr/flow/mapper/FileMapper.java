package com.pgrrr.flow.mapper;

import com.pgrrr.flow.domain.FileInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    @Insert(
            "INSERT INTO file_info(original_name, path, name) VALUES (#{originalName}, #{path}, #{name})")
    void insertFile(FileInfo fileInfo);
}
