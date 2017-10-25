package com.demo.multipart.controller;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.multipart.model.Plupload;
import com.demo.multipart.utils.PluploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 * Created by thinkpad on 2017/10/25.
 */
@Controller
@RequestMapping("/upload")
public class UploadAction {

    @RequestMapping(value = "/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {

        System.out.println("开始");
        String path = request.getSession().getServletContext().getRealPath("uploaddir");
        String fileName = file.getOriginalFilename();
//        String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("fileUrl", request.getContextPath()+"/uploaddir/"+fileName);

        return "result";
    }

    public static final String FileDir = "uploadfile/";

    /**上传界面*/
    @RequestMapping("/uploadui")
    public String uploadUI() {

        return "login.upload";
    }

    /**上传处理方法*/
    @RequestMapping(value="/plupload", method = RequestMethod.POST)
    public String upload(Plupload plupload, HttpServletRequest request, HttpServletResponse response) {

        //System.out.println(plupload.getChunk() + "===" + plupload.getName() + "---" + plupload.getChunks());

        plupload.setRequest(request);
        //文件存储路径
        File dir = new File(plupload.getRequest().getSession().getServletContext().getRealPath("/") + FileDir);

        System.out.println(dir.getPath());

        try {
            //上传文件
            PluploadUtil.upload(plupload, dir);
            //判断文件是否上传成功（被分成块的文件是否全部上传完成）
            if (PluploadUtil.isUploadFinish(plupload)) {
                System.out.println(plupload.getName() + "----");
            }

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "login.upload";
    }
}
