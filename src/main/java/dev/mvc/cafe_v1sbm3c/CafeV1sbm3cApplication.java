package dev.mvc.cafe_v1sbm3c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc","dev.jpa"}) // ★★★★★★ 패키지 주의 ★★★★★★ 
public class CafeV1sbm3cApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeV1sbm3cApplication.class, args);
    }

}
