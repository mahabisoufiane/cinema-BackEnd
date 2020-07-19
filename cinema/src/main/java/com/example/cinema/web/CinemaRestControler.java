package com.example.cinema.web;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.cinema.dao.FilmRepository;
import com.example.cinema.dao.TicketRepository;
import com.example.cinema.entities.Film;
import com.example.cinema.entities.Ticket;

import lombok.Data;
@RestController
@CrossOrigin("http://localhost:4200")
public class CinemaRestControler {
	@Autowired
	private FilmRepository filmrepository;
	@Autowired
	private TicketRepository ticketrepository;
	@GetMapping(path="/imageFilm/{id}",produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable (name="id")Long id) throws Exception {
		Film f=filmrepository.findById(id).get();
		String photoname=f.getPhoto();
		File file=new File(System.getProperty("user.home")+"/cinema/images/"+photoname);
		Path path=Paths.get(file.toURI());
		return Files.readAllBytes(path);
		
	}
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketform) {
		List<Ticket> listTickets=new ArrayList<>();
		ticketform.getTickets().forEach(idTicket->{
			Ticket ticket=ticketrepository.findById(idTicket).get();
			ticket.setNomClient(ticketform.getNomClient());
			ticket.setReserve(true);
			ticketrepository.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;
		
	}
}
@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> tickets=new ArrayList<>();
	
}
