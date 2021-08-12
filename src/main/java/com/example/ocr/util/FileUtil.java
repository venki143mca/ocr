package com.example.ocr.util;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    /**
     * Check mime type and crete temp file.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static File getFileByContentType(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = null;
        if (MimeTypeUtils.IMAGE_PNG_VALUE.equals(file.getContentType())) {
            tempFile = File.createTempFile("tempfile_image", ".png");
        } else if ("application/pdf".equals(file.getContentType())) {
            tempFile = File.createTempFile("tempfile_pdf", ".pdf");
        }
        return tempFile;
    }
}
