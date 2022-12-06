package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Poste;
import com.forum.ticketgenerator.repository.PosteRepository;
import com.forum.ticketgenerator.service.model.IPosteModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PosteModelService implements IPosteModelService {

    @Autowired
    private PosteRepository posteRepository;

    @Override
    @Transactional
    public List<PosteMatching> searchFromFamilleMetier(FamilleMetier familleMetier, Evenement evenement) {
        List<PosteMatching> postesMatching = new ArrayList<>();

        List<Poste> postes = posteRepository.findByFamilleMetierAndEvenement(familleMetier, evenement);
        postes.forEach(
                poste -> postesMatching.add(PosteMatchingMapper.map(poste.getEntreprise(), poste)));
        Model.getInstance().setPostesMatching(postesMatching);
        return postesMatching;
    }
}
