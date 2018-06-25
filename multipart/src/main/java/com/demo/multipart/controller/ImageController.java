package com.demo.multipart.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(method = RequestMethod.GET, value = "/image/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename, HttpServletResponse response) {
        try {
            byte[] body = FileUtils.readFileToByteArray(resourceLoader.getResource("file:E:\\resources\\qiguo\\gpf-web-jsyd\\src\\main\\webapp\\common\\image\\menu\\" + filename).getFile());
            return ResponseEntity
                    .ok(body);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image1/{filename:.+}")
    public ResponseEntity<?> getImage1(@PathVariable String filename, HttpServletResponse response) {
        try {
            return ResponseEntity
                    .ok(resourceLoader.getResource("file:E:\\resources\\qiguo\\gpf-web-jsyd\\src\\main\\webapp\\common\\image\\menu\\" + filename));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image2/{filename:.+}")
    public void getImage2(@PathVariable String filename, HttpServletResponse response) {
        OutputStream stream = null;
        try {
            byte[] body = FileUtils.readFileToByteArray(resourceLoader.getResource("file:E:\\resources\\qiguo\\gpf-web-jsyd\\src\\main\\webapp\\common\\image\\menu\\" + filename).getFile());
            stream = response.getOutputStream();
            stream.write(body);
            stream.flush();
            stream.close();
        } catch (Exception e) {

        }finally {
            if(null != stream){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
