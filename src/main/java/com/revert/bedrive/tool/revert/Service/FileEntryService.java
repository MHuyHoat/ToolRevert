package com.revert.bedrive.tool.revert.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revert.bedrive.tool.revert.Model.FileEntryEntity;
import com.revert.bedrive.tool.revert.Repository.FileEntryRepository;

@Service
public class FileEntryService {
    @Autowired
    public FileEntryRepository fileEntryRepository;
    public List<FileEntryEntity> getAllFiles(){
        return fileEntryRepository.findAll();
    }
    public List<FileEntryEntity> getSubEntries(Long id){
        return fileEntryRepository.getSubEntries(id);
    }
}
