package com.mdot.app.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.Socialmedia;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.SocialmediaRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.SocialMediaRepository;
import com.mdot.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class SocialmediaService {
    


	@Autowired
	private SocialMediaRepository socialmediaRepository;

    @Autowired
    private UserRepository userRepository;

    
	@Transactional
	public ResponseEntity<?> save(@Valid SocialmediaRequest socialmediaRequest,long id) {
		try {

			Socialmedia socialmedia = new Socialmedia();

            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);              

			socialmedia.setId((long) 0);
            socialmedia.setUser(user.get());
			socialmedia.setInstagram(socialmediaRequest.getInstagram());
			socialmedia.setFacebook(socialmediaRequest.getFacebook());
            socialmedia.setTwitter(socialmediaRequest.getTwitter());
            socialmedia.setSnapchat(socialmediaRequest.getSnapchat());
            socialmedia.setLinkedin(socialmediaRequest.getLinkedin());
            socialmedia.setPinterest(socialmediaRequest.getPinterest());
            socialmedia.setTwitch(socialmediaRequest.getTwitch());
			socialmedia.setStatus(RecordStatus.ACTIVE);
            socialmedia = this.socialmediaRepository.save(socialmedia);


			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", socialmedia), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

@Transactional
	public ResponseEntity<?> update(long id, @Valid SocialmediaRequest socialmediaRequest) {
		try {
			Optional<Socialmedia> socialmedia = this.socialmediaRepository.findById(id);

			if (!socialmedia.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

                
                socialmedia.get().setInstagram(socialmediaRequest.getInstagram());
                socialmedia.get().setFacebook(socialmediaRequest.getFacebook());
                socialmedia.get().setTwitter(socialmediaRequest.getTwitter());
                socialmedia.get().setSnapchat(socialmediaRequest.getSnapchat());
                socialmedia.get().setLinkedin(socialmediaRequest.getLinkedin());
                socialmedia.get().setPinterest(socialmediaRequest.getPinterest());
                socialmedia.get().setTwitch(socialmediaRequest.getTwitch());
			    socialmedia.get().setStatus(RecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.socialmediaRepository.save(socialmedia.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
}

@Transactional
public ResponseEntity<?> delete(long id) {
    try {
        this.socialmediaRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }


}

@Transactional
public ResponseEntity<?> listById(long id) {
    try {
        Optional<Socialmedia> socialmedia = this.socialmediaRepository.findById(id);

        if (!socialmedia.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", socialmedia), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}
}




