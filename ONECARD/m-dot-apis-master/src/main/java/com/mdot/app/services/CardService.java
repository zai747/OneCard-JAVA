package com.mdot.app.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.mdot.app.models.Card;
import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.CardRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.CardRepository;
import com.mdot.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class CardService {
    


	@Autowired
	private CardRepository cardRepository;

    @Autowired UserRepository userRepository;

    
	@Transactional
	public ResponseEntity<?> save(@Valid 	CardRequest cardRequest,long id) {
		try {

			Card card = new Card();

            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);              

			card.setId((long) 0);
            card.setUser(user.get());
			card.setCardid(cardRequest.getCardid());
			card.setStatus(RecordStatus.ACTIVE);
			card = this.cardRepository.save(card);


			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", card), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

@Transactional
public ResponseEntity<?> delete(long id) {
    try {
        this.cardRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }


}

@Transactional
public ResponseEntity<?> listById(long id) {
    try {
        Optional<Card> card = this.cardRepository.findById(id);

        if (!card.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", card), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }



}}




