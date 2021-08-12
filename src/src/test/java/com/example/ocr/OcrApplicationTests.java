package com.example.ocr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OcrApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void imageReadTest()
            throws Exception {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("i94.png");
        final MockMultipartFile mfile = new MockMultipartFile("file", "test.png", "image/png", inputStream);
        mockMvc.perform(multipart("/upload").file(mfile))
                .andExpect(status().isOk());
    }

    @Test
    public void pdfReadTest()
            throws Exception {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scansmpl.pdf");
        final MockMultipartFile mfile = new MockMultipartFile("file", "scansmpl.pdf", "application/pdf", inputStream);
        mockMvc.perform(multipart("/upload").file(mfile))
                .andExpect(status().isOk());
    }

    @Test
    public void noFileReadTest()
            throws Exception {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scansmpl.pdf");
        final MockMultipartFile mfile = new MockMultipartFile("file", "scansmpl", "application", inputStream);
        mockMvc.perform(multipart("/upload").file(mfile))
                .andExpect(status().isBadRequest());
    }

}
