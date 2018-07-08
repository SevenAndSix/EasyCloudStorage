package com.easycloudstorage.pojo;

import com.easycloudstorage.service.filemodule.FileService;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {

    public static String uploadFiles(MultipartFile[] files, HttpServletRequest request, FileService fileService){

        boolean flag=true;
        for (MultipartFile file:files
             ) {
            String temp=uploadOneFile(file,request,fileService);
            flag &= temp.equals("success");
        }
        if (flag==true)return "success";
        else return "failed";
    }
    public static String uploadOneFile(MultipartFile file, HttpServletRequest request,FileService fileService){

        String name=file.getOriginalFilename();
        HttpSession session=request.getSession();
        Directory dir=(Directory) session.getAttribute("currentDir");
        com.easycloudstorage.pojo.NormalFile uploadFile=new com.easycloudstorage.pojo.NormalFile();

        uploadFile.setName(name);
        String size=Long.toString(file.getSize());
        uploadFile.setSize(size+"kb");
        uploadFile.setType(file.getContentType());
        uploadFile.setLastMovedTime(dir.getLastMovedTime());
        uploadFile.setOwnerId(dir.getOwnerId());
        uploadFile.setParentDirId(dir.getDirId());
        uploadFile.setRealPath(dir.getRealPath()+"\\"+name);
        fileService.insertFile(uploadFile);

        User user=(User) request.getSession().getAttribute("user");
        user=new User();
        user.setUserName("why");
        java.io.File userDir=new File("D:\\EasyCloudStorageData\\"+user.getUserName());
        int BufferSize=4096;
        byte buffer[]=new byte[BufferSize];
        try {
            if (!userDir.exists())
            userDir.mkdir();
            File temp=new File(userDir,name);
            file.transferTo(temp);
//            FileOutputStream writer=new FileOutputStream(temp);
//            InputStream reader=file.getInputStream();
//
//            int number=0;
//            while ((number=reader.read(buffer))!=-1){
//
//                writer.write(buffer, 0, number);
//
//            }
//            writer.flush();
//            writer.close();
//            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
        return "success";  //上传成功则跳转至此success.jsp页
    }


}
