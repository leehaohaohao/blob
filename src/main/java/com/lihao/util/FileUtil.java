package com.lihao.util;


import com.lihao.constants.StringConstants;
import com.lihao.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static String[] fileBookLoad(MultipartFile file,String path){
        String name = file.getOriginalFilename();
        InputStream in = null;
        BufferedOutputStream out = null;
        String filename;
        try {
            in = file.getInputStream();
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            filename = UUID.randomUUID() + name.substring(name.lastIndexOf("."));
            out = new BufferedOutputStream(new FileOutputStream(path  + filename));
            byte[] bytes = new byte[1024 * 100];
            int readCount = 0;
            while ((readCount = in.read(bytes)) != -1) {
                out.write(bytes, 0, readCount);
            }
            out.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        String []ss = new String[2];
        //TODO 还需优化,static不能写死
        int startIndex = path.indexOf("/static");
        String result = path.substring(startIndex).replace("/static","");
        ss[0]= StringConstants.URI+result+filename;
        ss[1]=path+filename;
        return ss;
    }
    public static boolean removeFile(String filePath) throws GlobalException {
        File file = new File(filePath);
        if(file.exists() && file.isFile()){
            if(file.delete()){
                logger.info("文件上传成功！");
                return true;
            }else{
                throw new GlobalException("文件处理失败！");
            }
        }else{
            throw new GlobalException("文件不存在！");
        }
    }
}
