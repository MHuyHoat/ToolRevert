package com.revert.bedrive.tool.revert.Thread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.boot.jdbc.DataSourceBuilder;

import com.revert.bedrive.tool.revert.Model.FileEntryEntity;
import com.revert.bedrive.tool.revert.Model.UserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Service
@NoArgsConstructor
@Getter
@Setter
public class ThreadRevert implements Runnable {

     private String pathSource = "/home/maihuyhoat/Downloads/source/data/edudigital/uploads";
     private String pathTarget = "/home/maihuyhoat/Downloads/target";
    // private String pathSource = "/media/maihuyhoat/Data1/Dowload/data/edudigital/uploads";
    // private String pathTarget = "/media/maihuyhoat/Data1/Dowload/target";

    private final Logger logger = LoggerFactory.getLogger(ThreadRevert.class);

    private UserEntity user;
    private Double percent=0.0;
    // @Autowired 
    // private DataSource dataSource;
    
     @Bean
     public DataSource dataSource() {
        return DataSourceBuilder.create()
        .driverClassName("com.mysql.cj.jdbc.Driver")
        .url("jdbc:mysql://localhost:3306/clouddig_db")
        .username("admin")
        .password("1223")
        .build();
    }
    
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
      public boolean savePercentConvert(String email,String percent){
      try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
            String sql = "UPDATE `cldiusers` SET `percent_convert` = ? WHERE `email` = ?";
            jdbcTemplate.update(sql, percent,email);
            return true;
        } catch (Exception ex) {
            logger.error("Lỗi thực thi chương trình :" + ex.getMessage());
            return false;
        }
    }
    @Override
    public void run() {
        try {

            File folderUser = new File(this.pathTarget + "/" + user.getEmail());
            folderUser.mkdirs();
            List<FileEntryEntity> files = user.getFiles();
            Integer countFilesRevert = files.size();
            Integer countFilesSuccess = 0;

            LinkedList<FileEntryEntity> queue = new LinkedList<>();

            String pathTargetEntry = this.pathTarget + "/" + user.getEmail();

            for (FileEntryEntity child : files) {
                if ((Long) child.getParentId() == null) {
                    String fileName = child.getName();
                    String extension = child.getExtension();
                    if (extension != null && !fileName.contains(extension)) {
                        fileName += "." + extension;
                    }
                    child.path = pathTargetEntry + "/" + fileName;
                    queue.offer(child);
                }
            }

            while (!queue.isEmpty()) {
                FileEntryEntity file = queue.poll();

                try {
                    if (file.getType().equals("folder")) {
                        File folderFile = new File(file.path);
                        if (folderFile.mkdirs()) {
                            // this.logger.info("Created folder: " + folderFile.getAbsolutePath());
                   
                        } else {
                             this.logger.error("Failed to create folder: " +file.path);
                             countFilesSuccess--;

                        }
                    } else {
                        File entry = new File(this.pathSource + "/" + file.getFileName() + "/" + file.getFileName());
                        File targetEntry = new File(file.path);

                        if (entry.exists()) {
                            try {

                                Files.copy(entry.toPath(), targetEntry.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                // this.logger.info(
                                // "Copied file: " + entry.getAbsolutePath() + " to " + targetEntry.toPath());
                            } catch (IOException e) {
                                this.logger.error("Lỗi coppy file: " + entry.getAbsolutePath() + " to "
                                        + targetEntry.getAbsolutePath() + "\n " + e.getMessage());
                                        countFilesSuccess--;
                            }
                        } else {
                            this.logger.error("Source file does not exist: " + entry.getAbsolutePath());
                            countFilesSuccess--;
                        }
                    }

                    countFilesSuccess++;
                } catch (Exception e) {
                    // TODO: handle exception
                    this.logger.error("Lỗi coppy file: " + file.getName() + file.getExtension() + "( "
                            + this.user.getEmail() + ") \n"+e.getMessage(), e);
                }
                this.percent = (double) countFilesSuccess / countFilesRevert * 100;
       
                System.out.println("Tỉ lệ hoàn thành Revert - " + user.getEmail() + " : " + percent + "%");
                // them child vao queue if (file.getType().equals("folder")) {
         
                if(file.getType().equals("folder")){
              
                    for (FileEntryEntity child : files) {
                       
                        if( (Long) child.getParentId() != null && child.getParentId().equals(file.getId())){
                            String fileName = child.getName();
                            String extension = child.getExtension();
                            if (extension != null && !fileName.contains(extension)) {
                                fileName += "." + extension;
                            }
                            child.path = file.path + "/" + fileName;
                            queue.offer(child);
                        }
                           
                        
                    }
                }

            }
            if(countFilesRevert==countFilesSuccess){
                this.percent=100.0;
            }
            this.user.setPercentConvert(String.format("%.2f", this.percent)+" %");
              this.savePercentConvert(this.user.getEmail(), this.user.getPercentConvert());
            this.logger.info("Đã hoàn thành Revert người dùng : " + user.getEmail()+" : " +  this.user.getPercentConvert());
        } catch (Exception e) {
            // TODO: handle exception
            this.logger.error("Lỗi với người dùng : " + this.user.getEmail() + " \n " + e.getMessage(), e.getMessage());
        }

    }
}
