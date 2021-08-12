package com.example.ocr.service.impl;

import com.example.ocr.service.IDownloadPatent;
import com.example.ocr.service.IPdfToImage;
import com.example.ocr.util.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class DownloadPatentService implements IDownloadPatent {

    Logger logger = LoggerFactory.getLogger(DownloadPatentService.class);

    final
    IPdfToImage pdfToImage;

    final
    ThreadPoolExecutor executor;

    public DownloadPatentService(IPdfToImage pdfToImage, ThreadPoolExecutor executor) {
        this.pdfToImage = pdfToImage;
        this.executor = executor;
    }

    /**
     * Process Patents.
     * @param patIds
     */
    public void processFiles(List<String> patIds) {
//        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        for (String patId : patIds) {
            executor.submit(() -> {
                logger.info("Thread started for patent Processing : " + patId);
                File pdFFile = downloadFileFromURL(patId);
                pdfToImage.convertPdfToText(pdFFile);
                Thread.sleep(1000);
                return null;
            });
        }

    }

    /**
     * Download Patent
     *
     * @param patId
     * @return
     * @throws IOException
     */
    public File downloadFileFromURL(String patId) {
        String url = formatURL(patId);
        File downloadedFile = new File(patId + ".pdf");
        try {
            FileUtils.copyURLToFile(
                    new URL(url),
                    downloadedFile,
                    Constants.CONNECT_TIMEOUT,
                    Constants.READ_TIMEOUT);
        } catch (IOException e) {
            logger.error("Error occured", e);
        }
        return downloadedFile;
    }

    /**
     * Format the URL
     *
     * @param patID
     * @return
     */
    private String formatURL(String patID) {
        String output = "";
        if (StringUtils.hasText(patID)) {
            String thirdPart = "";
            patID = patID.substring(patID.indexOf("-") + 1, patID.lastIndexOf("-"));

            if (patID.substring(0, patID.length() - 5).length() == 2) {
                thirdPart = "0" + patID.substring(0, patID.length() - 5);
            } else {
                thirdPart = patID.substring(0, patID.length() - 5);
            }
            output = "https://pdfpiw.uspto.gov/" + patID.substring(patID.length() - 2, patID.length()) + "/" + patID.substring(patID.length() - 5, patID.length() - 2) + "/" + thirdPart + "/1.pdf";
        }
        return output;
    }

    public boolean isProcessCompleted() {
        return executor==null || executor.getActiveCount()==0?true:false;
    }

}
