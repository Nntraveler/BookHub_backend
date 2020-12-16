package com.bookhub.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.bookhub.config.ALiYunOSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OSSService {

    @Autowired
    private ALiYunOSSConfig aLiYunOSSConfig;

    public String uploadFile(MultipartFile image){

        String endpoint=aLiYunOSSConfig.getEndpoint();
        String accessKeyId=aLiYunOSSConfig.getAccessKeyId();
        String accessKeySecret=aLiYunOSSConfig.getAccessKeySecret();
        String bucketName=aLiYunOSSConfig.getBucketName();
        String fileHost=aLiYunOSSConfig.getFolder();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String fileName=image.getOriginalFilename();
        String fileUrl =fileHost + "/" + (dateStr + "/" + uuid ) + "-" + fileName;
        PutObjectResult result=null;
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            InputStream inputStream=image.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            // 上传文件
            result = client.putObject(new PutObjectRequest(bucketName, fileUrl, inputStream, objectMetadata ));
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            client.shutdown();
        }
        if (result != null) {
            System.out.println(result);
            return aLiYunOSSConfig.getWebUrl() +"/"+ fileUrl;
        }
        return null;
    }

    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
