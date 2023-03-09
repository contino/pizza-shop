package io.contino.webreceiver;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    public String generate(){
        return UUID.randomUUID().toString();
    }
}
