package com.revert.bedrive.tool.revert;



import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import com.revert.bedrive.tool.revert.Model.UserEntity;
import com.revert.bedrive.tool.revert.Service.FileEntryService;
import com.revert.bedrive.tool.revert.Service.UserService;
import com.revert.bedrive.tool.revert.Thread.ThreadRevert;



@SpringBootApplication
public class RevertApplication {

    @Autowired
    public UserService userService;

    @Autowired
    public FileEntryService fileEntryService;
    private String pathTarget = "/home/maihuyhoat/Downloads/target";
    // private String pathTarget = "/media/maihuyhoat/Data1/Dowload/target";
    private final Logger logger = LoggerFactory.getLogger(RevertApplication.class);


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
	public static void main(String[] args) {
		
        ApplicationContext context = SpringApplication.run(RevertApplication.class, args);
		RevertApplication main= context.getBean(RevertApplication.class);
		 try {
            File folder = new File(main.pathTarget);
            if (folder.exists() && folder.isDirectory()) {
                deleteFolder(folder);
                folder.mkdir();
            } else {
                folder.mkdir();
            }
            main.logger.info("Đã xóa mọi thứ trong =>" + main.pathTarget);
            List<UserEntity> users = main.userService.getAllUser();
             ExecutorService executorService = Executors.newFixedThreadPool(20);
            for (UserEntity user : users) {
                // bat dau thread voi moi user o day
                try {
                    ThreadRevert threadRevert = new ThreadRevert();
                     threadRevert.setUser(user);
                     executorService.submit(threadRevert);
                   
                } catch (Exception ex) {
                    main.logger.error("Lỗi thực thi chương trình :" + ex.getMessage());

                }
            }
			executorService.shutdown();
			SpringApplication.exit(context);
            
        } catch (Exception e) {
            // TODO: handle exception
            main.logger.error(main.pathTarget);
        }
		
	}

}
