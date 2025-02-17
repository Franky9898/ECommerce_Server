package com.ECommerceProject;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ECommerceProject.model.Ordine;
import com.ECommerceProject.model.Utente;
import com.ECommerceProject.repository.OrdineRepository;
import com.ECommerceProject.repository.ProdottoRepository;
import com.ECommerceProject.repository.UtenteRepository;

@Component
public class DataLoader implements CommandLineRunner
{
    @Autowired
	private UtenteRepository userRepository;

    @Autowired
    private OrdineRepository ordineRepository;
    
    @Autowired
    private ProdottoRepository prodottoRepository;


	/**
	 * Metodo eseguito al termine dell'avvio dell'applicazione. Se il database è vuoto, inserisce alcuni utenti di esempio.
	 */
	@Override
	public void run(String... args) throws Exception
	{
		if (userRepository.count() == 0)
		{
			Utente adminUser = new Utente("nome1", 
					"cognome1", 
					101010100L, 
					"mario.rossi@example.com", 
					"password1" 
			);
			userRepository.save(adminUser);

			Utente user3 = new Utente("nome3", 
					"cognome3", 
					101010102L, 
					"mario.rossi1@example.com", 
					"password1" 
			);
			userRepository.save(user3);

			Utente user2 = new Utente("nome2", 
					"cognome2", 
					101010101L, 
					"mario.rossi2@example.com", 
					"password2"
			);
			userRepository.save(user2);
			
			Ordine ordine1 = new Ordine();
	        // Imposta una data nel passato (1 giorno fa)
	        ordine1.setDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
	        ordine1.setTotale(30.0);
	        ordine1.setUtente(user3);
	        ordine1.setProdotti(Arrays.asList(prodottoRepository.findById(1L).get(), prodottoRepository.findById(2L).get()));
	        ordineRepository.save(ordine1);

	        Ordine ordine2 = new Ordine();
	        // Imposta una data nel passato (2 giorni fa)
	        ordine2.setDate(new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000));
	        ordine2.setTotale(20.0);
	        ordine2.setUtente(user3);
	        ordine2.setProdotti(Arrays.asList(prodottoRepository.findById(1L).get()));
	        ordineRepository.save(ordine2);
	        
	        System.out.println("Dati caricati: utente e ordini.");
		}
	}
}