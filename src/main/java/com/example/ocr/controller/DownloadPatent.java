package com.example.ocr.controller;

import com.example.ocr.service.IDownloadPatent;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class DownloadPatent {

    Logger logger = LoggerFactory.getLogger(DownloadPatent.class);
    final IDownloadPatent downloadPatent;

    public DownloadPatent(IDownloadPatent downloadPatent) {
        this.downloadPatent = downloadPatent;
    }

    @GetMapping("/patent-download/{patId}")
    @ApiOperation(value = "download and ocr the patent",
            notes = "download and ocr the patent",
            consumes = "application/json",
            produces = "application/json",
            response = ResponseEntity.class
    )
    public ResponseEntity<String> download(@PathVariable String patId) throws IOException {
        downloadPatent.processFiles(Arrays.asList(patId));
        return new ResponseEntity<>("File Downloaded successful.", HttpStatus.OK);
    }

    @PostMapping("/patent-download")
    @ApiOperation(value = "download bulk and ocr the patents",
            notes = "download and ocr the patent",
            consumes = "application/json",
            produces = "application/json",
            response = ResponseEntity.class
    )
    public ResponseEntity<String> bulkDownload(@RequestBody List<String> patIds) throws IOException {
        logger.info("List of patIds to analyze " + patIds.size());
        downloadPatent.processFiles(patIds);
        return new ResponseEntity<>("Request Processed, you will get notified..", HttpStatus.OK);
    }


    @GetMapping("/isProcessCompleted")
    @ApiOperation(value = "check the status of download and ocr process",
            notes = "check the status of download and ocr process",
            consumes = "application/json",
            produces = "application/json",
            response = ResponseEntity.class
    )
    public ResponseEntity<Boolean> isProcessCompleted() {
        return new ResponseEntity<>(downloadPatent.isProcessCompleted(), HttpStatus.OK);
    }

}
