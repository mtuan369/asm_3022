package com.example.asm.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;

    public String getString(String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        String value = getString(name, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    public Double getDouble(String name, double defaultValue) {
        String value = getString(name, String.valueOf(defaultValue));
        return Double.parseDouble(value);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String value = getString(name, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    public Date getDate(String name, String pattern) {
        String value = getString(name, "");
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File save(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            File dir = new File(request.getServletContext().getRealPath(path));
            if (!dir.exists()) dir.mkdirs();
            try {
                File savedFile = new File(dir, file.getOriginalFilename());
                file.transferTo(savedFile);
                return savedFile;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}