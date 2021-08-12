package com.example.ocr.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IDownloadPatent {
    File downloadFileFromURL(String patId) throws IOException;
    void processFiles(List<String> patIds);
    boolean isProcessCompleted();
}
