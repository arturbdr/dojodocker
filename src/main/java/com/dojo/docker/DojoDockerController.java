package com.dojo.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DojoDockerController {

    @GetMapping("dojodocker")
    public String dojodocker(final User user) {
        log.info("{}", user);
        return String.format("User passed %s", user.getName());
    }
}
