package com.example.demo.controller;

import com.example.demo.model.service.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/file")
public class FileController {
    @Value("filesPath")
    private String filesPath;

    @PostMapping("/upload")
    public Result upload(@RequestParam("FormFile") MultipartFile file, @RequestParam("FileName") String filename) throws IOException {
        File uploadFiles = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static/uploadFiles");
        if (!uploadFiles.exists()) {
            uploadFiles.mkdirs();
        }
        String postfix = filename.split("\\.")[1];
        File currentFile = new File(uploadFiles.getAbsolutePath() + File.separator + UUID.randomUUID() + "." + postfix);
        file.transferTo(currentFile);
        HashMap<String, String> path = new HashMap<>();
        path.put("path", "/" + currentFile.getParentFile().getName() + "/" + currentFile.getName());
        return new Result(200, true, "上传成功", path);
    }
}
