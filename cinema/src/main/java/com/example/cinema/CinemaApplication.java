package com.example.cinema;

import com.example.cinema.entities.Film;
import com.example.cinema.entities.Salle;
import com.example.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cinema.service.ICinemaInitService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService cinemainitservice;
	@Autowired
	private RepositoryRestConfiguration restconfiguration;
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		restconfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		cinemainitservice.initVilles();
		cinemainitservice.initCinemas();
		cinemainitservice.initSalles();
		cinemainitservice.initPlaces();
		cinemainitservice.initSeances();
		cinemainitservice.initCategories();
		cinemainitservice.initFilms();
		cinemainitservice.initProjections();
		cinemainitservice.initTickets();
		}

	

}
