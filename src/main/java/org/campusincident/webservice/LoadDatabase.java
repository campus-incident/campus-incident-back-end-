package org.campusincident.webservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import org.campusincident.webservice.category.Category;
import org.campusincident.webservice.category.CategoryRepository;
import org.campusincident.webservice.geolocation.Geolocation;
import org.campusincident.webservice.geolocation.GeolocationRepository;
import org.campusincident.webservice.incident.Incident;
import org.campusincident.webservice.incident.IncidentRepository;
import org.campusincident.webservice.location.Location;
import org.campusincident.webservice.location.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class LoadDatabase {
	
  Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(
		  LocationRepository repoLocation,
		  GeolocationRepository repoGeolocation,
		  IncidentRepository repoIncident,
		  CategoryRepository repoCategory
		  ) {
    return (args) -> {

    	Geolocation lille1 = repoGeolocation.save(new Geolocation(50.609361d, 3.141449d));
    	Geolocation lille3 = repoGeolocation.save(new Geolocation(50.627748d, 3.126055d));
    	Geolocation m5avant = repoGeolocation.save(new Geolocation(50.609577d, 3.136766d));
    	Geolocation m5derriere = repoGeolocation.save(new Geolocation(50.609762d, 3.136478d));
    	Geolocation peripherique = repoGeolocation.save(new Geolocation(50.608014d, 3.134397d));
    	
    	Category catMobilier = new Category();
    	catMobilier.setName("mobilier");
    	catMobilier = repoCategory.save(catMobilier);
    	
    	Category catEspaceVert = new Category();
    	catEspaceVert.setName("espace vert");
    	catEspaceVert = repoCategory.save(catEspaceVert);
    	
    	Location campusLille1 = new Location();
    	campusLille1.setCenter(lille1);
    	campusLille1.setName("Campus Lille 1");
    	campusLille1.setRadius(700d);
    	campusLille1.setTzName("Europe/Paris");
    	campusLille1 = repoLocation.save(campusLille1);
    	logger.info("added location campus lille 1 " + campusLille1.toString());
    	
    	Location campusLille3 = new Location();
    	campusLille3.setCenter(lille3);
    	campusLille3.setName("Campus Lille 3");
    	campusLille3.setRadius(500d);
    	campusLille3.setTzName("Europe/Paris");
    	campusLille3 = repoLocation.save(campusLille3);
    	logger.info("added location campus lille 3 " + campusLille1.toString());
    	
    	Incident incident1 = new Incident();
    	incident1.setAuthor("bob@me.com");
    	incident1.setLocation(campusLille1);
    	incident1.setStatus("En attente");
    	incident1.setCreatedAt(LocalDateTime.parse("2020-02-21T12:22:59").atZone(ZoneId.of("Europe/Paris")).toInstant());
    	incident1.setTitle("Poubelle détruite");
    	incident1.setCategories(Arrays.asList(catMobilier));
    	incident1.setGeolocation(m5avant);
    	incident1 = repoIncident.save(incident1);
    	
    	Incident incident2 = new Incident();
    	incident2.setAuthor("bob@me.com");
    	incident2.setLocation(campusLille1);
    	incident2.setStatus("En attente");
    	incident2.setCreatedAt(LocalDateTime.parse("2020-02-19T08:55:42").atZone(ZoneId.of("Europe/Paris")).toInstant());
    	incident2.setTitle("Panneau au sol");
    	incident2.setCategories(Arrays.asList(catMobilier, catEspaceVert));
    	incident2.setGeolocation(peripherique);
    	incident2 = repoIncident.save(incident2);
    	
    	Incident incident3 = new Incident();
    	incident3.setAuthor("marie@me.com");
    	incident3.setLocation(campusLille1);
    	incident3.setStatus("Réparé");
    	incident3.setCreatedAt(LocalDateTime.parse("2020-02-21T17:05:14").atZone(ZoneId.of("Europe/Paris")).toInstant());
    	incident3.setTitle("Vitre cassée");
    	incident3.setDescription("Des éclats de verre sont présents sur le sol.");
    	incident3.setGeolocation(m5avant);
    	incident3 = repoIncident.save(incident3);
    	
    	Incident incident4 = new Incident();
    	incident4.setAuthor("jean@me.com");
    	incident4.setLocation(campusLille1);
    	incident4.setStatus("En attente");
    	incident4.setCreatedAt(LocalDateTime.parse("2020-02-23T11:59:31").atZone(ZoneId.of("Europe/Paris")).toInstant());
    	incident4.setTitle("Panneau tordu");
    	incident4.setGeolocation(m5derriere);
    	incident4.setCategories(Arrays.asList(catEspaceVert));
    	incident4 = repoIncident.save(incident4);
    };
  }
}