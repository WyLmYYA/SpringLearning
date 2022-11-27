package com.example.spring05.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @time: 2022/11/27
 * @author: yuanyongan
 * @description:
 */

@Component
@Slf4j
public class Bean2 {
    public Bean2(){
        log.info("我被Spring管理啦");
    }
}
