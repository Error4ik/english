package com.voronin.nouns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NounsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NounsApplication.class, args);
    }

    /**
     * RestTemplate bean.
     *
     * @return RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
