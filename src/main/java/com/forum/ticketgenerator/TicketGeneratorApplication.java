package com.forum.ticketgenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.forum.ticketgenerator.model.Entreprise;
import com.forum.ticketgenerator.model.Formation;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.SearchService;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TicketGeneratorApplication {

	public static void main(String[] args) {
		try {
			Map<String, Entreprise> entreprises = ModelService.loadEntreprises("F:\\Tech\\ticket-generator\\src\\test\\resources\\in\\entreprises.csv");
			Map<String, Formation> formations = ModelService.loadFormations("F:\\Tech\\ticket-generator\\src\\test\\resources\\in\\formations.csv");
			Model.getInstance().setEntreprises(entreprises);
			Model.getInstance().setFormations(formations);

		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(TicketGeneratorApplication.class, args);
	}

}
