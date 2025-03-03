package com.revert.bedrive.tool.revert.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revert.bedrive.tool.revert.Model.FileEntryEntity;

public interface FileEntryRepository extends JpaRepository<FileEntryEntity, Long> {
      @Query("SELECT f FROM FileEntryEntity f WHERE f.parentId = :id")
      public List<FileEntryEntity> getSubEntries(@Param("id") Long id);
}
