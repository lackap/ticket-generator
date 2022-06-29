package com.forum.ticketgenerator;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.ModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TicketGeneratorApplication {

	public static void main(String[] args) {
		try {
			String path = null;
			if (	args.length > 0 && StringUtils.isNotEmpty(args[0])) {
				path = args[0];
			} else {
				path = new File(new File("toto").getAbsolutePath()).getParent();
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
