package com.example.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema.dao.VilleRepository;
import com.example.cinema.dao.CategorieRepository;
import com.example.cinema.dao.CinemaRepository;
import com.example.cinema.dao.FilmRepository;
import com.example.cinema.dao.PlaceRepository;
import com.example.cinema.dao.ProjectionRepository;
import com.example.cinema.dao.SalleRepository;
import com.example.cinema.dao.SeanceRepository;
import com.example.cinema.dao.TicketRepository;
import com.example.cinema.entities.Ville;
import com.example.cinema.entities.Categorie;
import com.example.cinema.entities.Cinema;
import com.example.cinema.entities.Film;
import com.example.cinema.entities.Place;
import com.example.cinema.entities.Projection;
import com.example.cinema.entities.Salle;
import com.example.cinema.entities.Seance;
import com.example.cinema.entities.Ticket;

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
	private TicketRepository ticketRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	

	
	
	
	
	@Override
	public void initVilles() {
		Stream.of("MERRAKECH","ASSILA","Rabat","Tanger").forEach(nameVille->{
			Ville ville=new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
		Stream.of("megarama","RIALTO","CHAHRAZADE").forEach(nameCinema->{
			Cinema cinema=new Cinema();
			cinema.setName(nameCinema);
			cinema.setNombreSalles(3+(int)(Math.random()*7));
			cinema.setVille(v);
			cinemaRepository.save(cinema);
			
		});
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initSalles() {
		
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=0;i<cinema.getNombreSalles();i++) {
				Salle salle=new Salle();
				salle.setName("salle"+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(20+(int)(Math.random()*10));
				salleRepository.save(salle);
			}
			
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initSeances() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance= new Seance();
			try {
				seance.setHeureDebut(df.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
				
			});
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initProjections() {
		double[] prices=new double[] {30,50,70,80,};
		List<Film> films=filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinema().forEach(cim->{
				cim.getSalles().forEach(salle->{
					int index=new Random().nextInt(films.size());
					Film film=films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection projection= new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});

				});
			});
		});;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initFilms() {
		double[] durees=new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories=categorieRepository.findAll();
		Stream.of("SPIDER MAN","NEED FOR SPEED","LINE OF DUTY","EXTRACTION").forEach(fil->{
			Film film=new Film();
			film.setTitre(fil);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(fil.replace(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
			
			
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlace();i++) {
				Place place=new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
				
			}
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Fiction","Drame","Comedy").forEach(cat->{
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
			
		});
		// TODO Auto-generated method stub
		
	}

}
