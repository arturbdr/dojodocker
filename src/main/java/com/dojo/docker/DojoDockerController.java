package com.dojo.docker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DojoDockerController {

    @RequestMapping("dojodocker")
    public String dojodocker() {
        return "Servico Rest Dojo Docker funcionando!";
    }
}
