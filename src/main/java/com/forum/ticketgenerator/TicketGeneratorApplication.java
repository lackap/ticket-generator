package com.forum.ticketgenerator;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.PdfGenerationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TicketGeneratorApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

	public static void main(String[] args) {
		try {
			String path = null;
			if (	args.length > 0 && StringUtils.isNotEmpty(args[0])) {
				path = args[0];
				LOGGER.info("Path parameter : " + path);
			} else {
				path = new File(new File("toto").getAbsolutePath()).getParent();
				LOGGER.info("Path automatique 1 : " + path);
 			}
			ModelService modelService = new ModelService();
			Model.getInstance().setEntrepriseFile(path + "\\entreprises.csv");
			Model.getInstance().setFormationsFile(path + "\\formations.csv");

		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(TicketGeneratorApplication.class, args);
	}

}
