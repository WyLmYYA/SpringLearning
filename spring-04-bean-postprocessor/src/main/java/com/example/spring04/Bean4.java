package com.example.spring04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @time: 2022/11/26
 * @author: yuanyongan
 * @description:
 */

@Slf4j
@Component
@ConfigurationProperties(prefix = "java")
public class Bean4 {
    private String name;

    private String version;

    public String toString(){
        return "name: " + name + "version:" + version;
    }
}
