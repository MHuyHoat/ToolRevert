package com.revert.bedrive.tool.revert.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
// @Table(name = "users")
@Table(name = "cldiusers")
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "percent_convert")
    private String percentConvert;
    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<FileEntryEntity> files;

}