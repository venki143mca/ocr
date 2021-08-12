package com.example.ocr.controller;

import com.example.ocr.service.IImageToText;
import com.example.ocr.service.IPdfToImage;
import com.example.ocr.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * OCR analysis created for reading the images or pdf using Tesseract.
 */
@RestController
public class OcrAnalysis {

    Logger logger = LoggerFactory.getLogger(OcrAnalysis.class);

    final IImageToText imageToText;
    final IPdfToImage pdfToImage;

    public OcrAnalysis(IImageToText imageToText, IPdfToImage pdfToImage) {
        this.imageToText = imageToText;
        this.pdfToImage = pdfToImage;
    }

    /**
     * Upload an image. service can be used from front end.
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Request received for converting a file to text. file name: " + file.getName());
        String out = null;
        // create temp file based on the type.
        File tempFile = null;
        tempFile = FileUtil.getFileByContentType(file);
        if (tempFile == null) {
            return new ResponseEntity("Something wrong with the file.", HttpStatus.BAD_REQUEST);
        }
        file.transferTo(tempFile);
        // based on file type do the image processing.
        if (MimeTypeUtils.IMAGE_PNG_VALUE.equals(file.getContentType())) {
            out = imageToText.getDataFromImage(tempFile);
        } else if ("application/pdf".equals(file.getContentType())) {
            out = pdfToImage.convertPdfToText(tempFile);
        }
        tempFile.deleteOnExit();
        return new ResponseEntity(out, HttpStatus.OK);
    }

    /**
     * can read image based on the file name.
     * File should be available in classpath.
     * File can be fetched from any file management system as well.
     *
     * @return
     */
    @GetMapping("/read/{fileName}")
    public ResponseEntity<String> getInfo(@PathVariable String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File newFile = new File(classLoader.getResource(fileName).getFile());
        Path path = newFile.toPath();
        String mimeType = Files.probeContentType(path);
        String out = null;

        if (MimeTypeUtils.IMAGE_PNG_VALUE.equals(mimeType)) {
            out = imageToText.getDataFromImage(newFile);
        } else if ("application/pdf".equals(mimeType)) {
            out = pdfToImage.convertPdfToText(newFile);
        }
        newFile.deleteOnExit();
        return new ResponseEntity(out, HttpStatus.OK);
    }

}