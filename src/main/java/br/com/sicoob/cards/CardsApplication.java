package br.com.sicoob.cards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardsApplication {
	private static final Logger logger = LoggerFactory.getLogger(CardsApplication.class);
	public static void main(String[] args) {
		logger.info("servidor foi iniciado com sucesso. port 80");
		SpringApplication.run(CardsApplication.class, args);

	}

}
