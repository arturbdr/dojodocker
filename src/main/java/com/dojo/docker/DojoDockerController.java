package com.dojo.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class DojoDockerController {

    @GetMapping("dojodocker")
    public String dojodocker(final User user) {
        log.info("{}", user);
        return String.format("User passed %s", user.getName());
    }

    @GetMapping("dojodocker-slow")
    public String dojodockerslow(final User user) {
        log.info("{}", user);
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("User passed %s", user.getName());
    }

    @GetMapping("consume-memory")
    public void consuming() {
        List<Object> destroyerArrayList = new ArrayList<>();

        while (1 == 1) {
            byte b[] = new byte[1048576];
            destroyerArrayList.add(b);
            log.info("free memory: {}", Runtime.getRuntime());

            final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            log.info("HeapMemoryMax: {}", memoryBean.getHeapMemoryUsage().getMax());
            log.info("HeapMemoryUsed: {}", memoryBean.getHeapMemoryUsage().getUsed());
            log.info("HeapMemoryInit: {}", memoryBean.getHeapMemoryUsage().getInit());
        }
    }
}
