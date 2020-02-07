package org.shm.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.shm.cinema.dao.CategorieRepository;
import org.shm.cinema.dao.CinemaRepository;
import org.shm.cinema.dao.FilmRepository;
import org.shm.cinema.dao.PlaceRepository;
import org.shm.cinema.dao.ProjectionFilmRepository;
import org.shm.cinema.dao.SalleRepository;
import org.shm.cinema.dao.SeanceRepository;
import org.shm.cinema.dao.TicketRepository;
import org.shm.cinema.dao.VilleRepository;
import org.shm.cinema.entities.Categorie;
import org.shm.cinema.entities.Cinema;
import org.shm.cinema.entities.Film;
import org.shm.cinema.entities.Place;
import org.shm.cinema.entities.ProjectionFilm;
import org.shm.cinema.entities.Salle;
import org.shm.cinema.entities.Seance;
import org.shm.cinema.entities.Ticket;
import org.shm.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
	
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionFilmRepository projectionFilmRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		// TODO Auto-generated method stub
		Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger", "Beni Mellal", "Settat").forEach(nameVille -> {
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinema() {
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(v -> {
			Stream.of("MegaRama", "IMAX", "FOUNOUN", "CHAHRAZAD", "DAOULIZ").forEach(nameCinema -> {
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalles(3 + (int)(Math.random()*7));
				cinema.setVille(v);
				
				cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaRepository.findAll().forEach(cinema -> {
			for (int i = 0; i < cinema.getNombreSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle " + (i + 1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15 + (int)(Math.random()*20));
				salleRepository.save(salle);
			}
		});
	}

	@Override
	public void initPlaces() {
		// TODO Auto-generated method stub
		salleRepository.findAll().forEach(salle -> {
			for (int i = 0; i < salle.getNombrePlace(); i++) {
				Place place = new Place();
				place.setNumero(i + 1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00", "15:00","17:00", "19:00", "21:00").forEach(s -> {
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("Histoire", "Actions", "Fiction", "Drama", "Animation").forEach(cat -> {
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		
		double[] durees = new double[] {1, 1.5, 2, 2.5, 3};
		
		List<Categorie> categories = categorieRepository.findAll();
		
		Stream.of("Game of Thrones", "Spider man", "Iron Man", "Cat Women", "12 Hommes en colaires", "Forrset Gump", "Green Book", "La ligne Verte", "Le Parin", "Le Seigneur des anneaux")
		.forEach(titreFilm -> {
			Film film = new Film();
			film.setTitre(titreFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll(" ", "")+".png");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
	}

	@Override
	public void initProjectionFilm() {
		// TODO Auto-generated method stub
		double[] prices = new double[] {30, 50, 60, 70, 90, 100};
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville -> {
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
					seanceRepository.findAll().forEach(seance -> {
						ProjectionFilm projectionFilm = new ProjectionFilm();
						projectionFilm.setDateProjection(new Date());
						projectionFilm.setFilm(film);
						projectionFilm.setPrix(prices[new Random().nextInt(prices.length)]);
						projectionFilm.setSalle(salle);
						projectionFilm.setSeance(seance);
						projectionFilmRepository.save(projectionFilm);
					});
				});
			});
		});
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionFilmRepository.findAll().forEach(projection -> {
			projection.getSalle().getPlaces().forEach(place -> {
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjectionFilm(projection);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
	}

}
