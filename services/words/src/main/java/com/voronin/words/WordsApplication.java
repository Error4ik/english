package com.voronin.words;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordsApplication.class, args);
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
