package com.example.ocr.service.impl;

import com.example.ocr.service.IImageToText;
import com.example.ocr.service.IPdfToImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class PdfToImage implements IPdfToImage {

    Logger logger = LoggerFactory.getLogger(PdfToImage.class);
    final IImageToText imageToText;

    public PdfToImage(IImageToText imageToText) {
        this.imageToText = imageToText;
    }

    /**
     * Convert Pdf to image then to text.
     *
     * @param newFile
     * @return
     */
    public String convertPdfToText(File newFile) {
        logger.info("Started Processing PDF to Image..");
        StringBuilder out = new StringBuilder();
        try {
            PDDocument document = PDDocument.load(newFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            // Create a temp image file
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                // Create a temp image file
                File tempFile = File.createTempFile("tempfile_" + page, ".png");
                ImageIO.write(bufferedImage, "png", tempFile);
                out.append(imageToText.getDataFromImage(tempFile));
                // Delete temp file
                tempFile.delete();
            }
        } catch (IOException ie) {
            logger.error("Error Occurred while processing pdf to image", ie);
        }
        return out.toString();
    }
}
