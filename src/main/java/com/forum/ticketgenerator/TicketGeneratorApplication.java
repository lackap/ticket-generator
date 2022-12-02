package com.forum.ticketgenerator;

import com.forum.ticketgenerator.service.PdfGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketGeneratorApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketGeneratorApplication.class, args);
	}

}
