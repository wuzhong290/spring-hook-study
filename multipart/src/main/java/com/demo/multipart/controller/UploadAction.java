package com.demo.multipart.controller;
import com.alibaba.fastjson.JSON;
import com.demo.multipart.model.Plupload;
import com.demo.multipart.utils.PluploadUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2017/10/25.
 */
@Controller
@RequestMapping("/upload")
public class UploadAction {
    public static final String FileDir = "uploadfile/";

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

    /**上传界面*/
    @RequestMapping("/uploadui")
    public String uploadUI() {

        return "login.upload";
    }

    /**上传处理方法*/
    @RequestMapping(value="/plupload", method = RequestMethod.POST)
    @ResponseBody
    public void upload(Plupload plupload, HttpServletRequest request, HttpServletResponse response) {

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
    }

    /**上传处理方法*/
    @RequestMapping(value="/pluploadtest", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE, request.getContentType())){
            Map<String, String[]> map =  request.getParameterMap();
            try {
                response.getWriter().write(JSON.toJSONString(map));
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(StringUtils.contains( request.getContentType(), MediaType.MULTIPART_FORM_DATA_VALUE)){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
            for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {
                System.out.println(entry.getKey());
                List<MultipartFile> multipartFiles = entry.getValue();
                try {
                    MultipartFile multipartFile = multipartFiles.get(0);
                    System.out.println(URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8") +":"+multipartFile.getName());
                    List<String> readlines = IOUtils.readLines(multipartFile.getInputStream());
                    for (String line : readlines){
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Map<String, String[]> parameterMap =  request.getParameterMap();
            try {
                System.out.println(URLDecoder.decode(JSON.toJSONString(JSON.toJSONString(parameterMap)), "UTF-8"));
                response.getWriter().write(JSON.toJSONString(parameterMap));
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(StringUtils.contains( request.getContentType(), MediaType.APPLICATION_OCTET_STREAM_VALUE)){
            //头部需要设置Content-Type = application/octet-stream
            try {
                List<String> files = IOUtils.readLines(request.getInputStream());
                response.getWriter().write(JSON.toJSONString(files));
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
