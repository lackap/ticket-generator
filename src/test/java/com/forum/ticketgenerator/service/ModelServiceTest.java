package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.service.model.csv.CsvModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CsvModelService.class})
@EnableConfigurationProperties
public class ModelServiceTest {

    @Autowired
    private CsvModelService modelService;

    @Test
    public void test_entreprises_nominal() throws IOException {
        File entreprisesFile = new File("src/test/resources/in/entreprises.csv");
        //Map<String, Entreprise> entreprises = Model..see(entreprisesFile.getAbsolutePath());
        //Assertions.assertThat(entreprises.size()).isEqualTo(4);
    }

    //@Test
    public void test_entreprises_without_stand() throws IOException {
        File entreprisesFile = new File("src/test/resources/in/entreprises_without_stand.csv");
    }

    //@Test
    public void test_entreprises_full() throws IOException {
        File entreprisesFile = new File("src/test/resources/in/entreprises_full.csv");
    }

    //@Test
    public void test_formations_full() throws IOException {
        File formationsFile = new File("src/test/resources/in/formations_full.csv");
    }
}
