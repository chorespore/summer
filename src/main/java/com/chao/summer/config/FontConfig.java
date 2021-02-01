package com.chao.summer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//@Configuration
@Import({ScheduleConfig.class})
public class FontConfig {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FontConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // @Bean
    public String registerNewFont() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:" + "static/fonts/simsun.ttc");
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try (InputStream inputStream = resource.getInputStream()) {
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            graphicsEnvironment.registerFont(newFont);
            return newFont.getFontName();
        }
    }

    @Bean
    public String loadText() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:" + "db/migration/V1.0__User.sql");

        InputStream inputStream = resource.getInputStream();
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        String str = sb.toString();
        System.out.println("输出" + str);

        return str;
    }
}

