package com.smartfood.backend.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class Base64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String fileName;

    public Base64DecodedMultipartFile(byte[] imgContent, String fileName) {
        if (imgContent == null || imgContent.length == 0) {
            throw new IllegalArgumentException("Image content cannot be null or empty");
        }
        this.imgContent = imgContent;
        this.fileName = fileName != null ? fileName : "uploaded.jpg";
    }

    @Override
    public String getName() {
        return "file";
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return "image/jpeg"; // 如果你想支持其他类型可以扩展
    }

    @Override
    public boolean isEmpty() {
        return imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(imgContent);
        }
    }
}
