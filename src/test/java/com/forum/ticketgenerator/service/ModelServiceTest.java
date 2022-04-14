package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ModelService.class})
@EnableConfigurationProperties
public class ModelServiceTest {

    @Autowired
    private ModelService modelService;

    @Test
    public void test_nominal() throws IOException {
        File entreprisesFile = new File("src/test/resources/in/entreprises.csv");
        Map<String, Entreprise> entreprises = modelService.loadEntreprises(entreprisesFile.getAbsolutePath());
        Assertions.assertThat(entreprises.size()).isEqualTo(4);
    }

    @Test
    public void test_without_stand() throws IOException {
        File entreprisesFile = new File("src/test/resources/in/entreprises_without_stand.csv");
        Map<String, Entreprise> entreprises = modelService.loadEntreprises(entreprisesFile.getAbsolutePath());
        Assertions.assertThat(entreprises.size()).isEqualTo(2);
        Assertions.assertThat(entreprises.get("PIERRE").getStand() == null || "".equals(entreprises.get("PIERRE").getStand()));
    }
}
