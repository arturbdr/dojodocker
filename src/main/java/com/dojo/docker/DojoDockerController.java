package com.dojo.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
public class DojoDockerController {

    @RequestMapping("dojodocker")
    public String dojodocker() {


        log.info("funcionando!");
        return "Dojo Docker Ok!";
    }

    @PostConstruct
    public void logOk() {
        log.debug("Log funcionando!");
    }
}
