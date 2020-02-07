package org.shm.cinema;

import org.shm.cinema.entities.Film;
import org.shm.cinema.entities.Salle;
import org.shm.cinema.entities.Ticket;
import org.shm.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class AppcinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService iCinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(AppcinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class );
		iCinemaInitService.initVilles();
		iCinemaInitService.initCinema();
		iCinemaInitService.initSalles();
		iCinemaInitService.initPlaces();
		iCinemaInitService.initSeances();
		iCinemaInitService.initCategories();
		iCinemaInitService.initFilms();
		iCinemaInitService.initProjectionFilm();
		iCinemaInitService.initTickets();
	}

}
