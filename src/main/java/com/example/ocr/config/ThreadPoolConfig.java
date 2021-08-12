package com.example.ocr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor getExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    }
}
