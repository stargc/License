package com.ehl.heartbeat.controller;

import com.ehl.heartbeat.tool.ServerInfoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

/**
 * @author created by guanchen on 2020/2/20 15:46
 */
@Controller
public class UploadFileController {

    @Value("${upload.file.path}")
    private String filePath;

    @RequestMapping("/uploadFile")
    public String  setLicense(Model model) {
        model.addAttribute("serverInfo", ServerInfoUtil.getServerInfo());
        return "uploadFile";
    }

    @PostMapping("/upload")
    public ModelAndView fileUpload(
            @RequestParam("file") MultipartFile file) {
        ModelAndView model = new ModelAndView();
        try {
            String fileName = file.getOriginalFilename();
            String destFileName = filePath + File.separator + fileName;
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            System.out.println(destFile);
            file.transferTo(destFile);
            model.addObject("fileName",fileName);
            model.addObject("fileMsg","上传成功！");
            model.setViewName("uploadSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            model.addObject("fileMsg","上传失败," + e.getMessage());
        }
        return model;
    }
}
