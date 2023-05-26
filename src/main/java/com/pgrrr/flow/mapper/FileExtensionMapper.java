package com.pgrrr.flow.mapper;

import com.pgrrr.flow.domain.FileExtension;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileExtensionMapper {

    @Select("SELECT * FROM file_extension")
    List<FileExtension> selectExtensionList();

    @Insert("INSERT INTO file_extension (name, is_static) VALUES (#{name}, #{isStatic})")
    void insertExtension(FileExtension fileExtension);

    @Select("SELECT COUNT(*) FROM file_extension WHERE is_static = false")
    Integer countExtension();

    @Select("SELECT name FROM file_extension WHERE name = #{name}")
    Optional<String> findExtensionByName(String name);

    @Delete("DELETE FROM file_extension WHERE name = #{name}")
    void deleteExtension(String name);
}
