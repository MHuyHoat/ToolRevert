package com.revert.bedrive.tool.revert.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//  @Table(name = "file_entries")
@Table(name = "cldifile_entries")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileEntryEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "file_name")
    private String fileName;
    @Column(name="parent_id")
    private Long parentId;
    @Column(name = "type")
    private String type;
    @Column(name = "extension")
    private String extension;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
     public String path;

}
