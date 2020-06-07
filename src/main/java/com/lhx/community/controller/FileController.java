package com.lhx.community.controller;

import com.lhx.community.dto.FileDto;
import com.lhx.community.exception.CustomizeErrorCode;
import com.lhx.community.exception.CustomizeException;
import com.lhx.community.provider.AliyunProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;


@Controller
public class FileController {

    @Autowired
    private AliyunProvider aliyunProvider;

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDto uploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDto fileDto = new FileDto();
        try {
            assert file != null;
            String url = aliyunProvider.fileUpload(file.getInputStream(), Objects.requireNonNull(file.getOriginalFilename()));
            fileDto.setUrl(url);
            fileDto.setSuccess(1);
            fileDto.setMessage("上传成功");
        } catch (IOException e) {
            fileDto.setSuccess(0);
            fileDto.setMessage("上传失败");
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_ERROR);
        }
        return fileDto;
    }
}
