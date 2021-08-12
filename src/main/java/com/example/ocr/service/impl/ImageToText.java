package com.example.ocr.service.impl;

import com.example.ocr.service.IImageToText;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ImageToText implements IImageToText {

    Logger logger = LoggerFactory.getLogger(ImageToText.class);

    /**
     * Generate text From Image.
     *
     * @param newFile
     * @return
     */
    public String getDataFromImage(File newFile) {
        logger.info("started processing image to text.");
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        String output = "";
        try {
            output = tesseract.doOCR(newFile);
            // Data analysis and convert to domains to store into Database.
            logger.info(output);
        } catch (TesseractException e) {
            logger.error("Error Occurred while processing image to text", e);
        }
        return output;
    }
}
