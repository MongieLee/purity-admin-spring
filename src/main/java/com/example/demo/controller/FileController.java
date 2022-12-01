package com.example.demo.controller;

import com.example.demo.configuration.EnvConfig;
import com.example.demo.model.service.result.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/file")
public class FileController {
    @Value("filesPath")
    private String filesPath;
    private final EnvConfig envConfig;

    public FileController(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }

    @PostMapping("/upload")
    public JsonResult upload(@RequestParam("FormFile") MultipartFile file, @RequestParam("FileName") String filename) throws IOException {
        String uploadPathFile = envConfig.getUploadFilePath();

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        uploadPathFile += File.separator + format;
        File uploadFolder = new File(uploadPathFile);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        String postfix = filename.split("(\\.)(?!.*\\1)")[1];
        File currentFile = new File(uploadFolder.getAbsolutePath() + File.separator + System.currentTimeMillis() + "." + postfix);
        file.transferTo(currentFile);
        HashMap<String, String> path = new HashMap<>();
        path.put("path", envConfig.getUploadFolder() + format + "/" + currentFile.getName());
        return new JsonResult(200, true, "上传成功", path);
    }
}
