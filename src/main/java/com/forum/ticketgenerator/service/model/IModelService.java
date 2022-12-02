package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.constants.ApplicationConstants;
import com.forum.ticketgenerator.mapper.EntrepriseMapper;
import com.forum.ticketgenerator.mapper.FormationMapper;
import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.utils.EncodingUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public interface IModelService {

    public List<PosteMatching> searchFromFormation(String nomCentre, String intituleFormation) throws IOException;
}
