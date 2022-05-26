package com.mdot.app.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.mdot.app.models.OneMedia;
import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.OneMediaRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.OneMediaRepository;
import com.mdot.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class OneMediaService {
    
 

	@Autowired
	private OneMediaRepository onemediaRepository;

	

	@Autowired
	private UserRepository userRepository;

    
	@Transactional
	public ResponseEntity<?> save(@Valid 	OneMediaRequest onemediaRequest,long id) {
		try {

			OneMedia onemedia = new OneMedia();

            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);              

			onemedia.setId((long) 0);
            onemedia.setUser(user.get());
			onemedia.setTitle(onemediaRequest.getTitle());
			onemedia.setImage(onemediaRequest.getImage());
            onemedia.setDescription(onemediaRequest.getDescription());
            
			onemedia.setStatus(RecordStatus.ACTIVE);
			onemedia = this.onemediaRepository.save(onemedia);


			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", onemedia), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

@Transactional
	public ResponseEntity<?> update(long id, @Valid OneMediaRequest onemediaRequest) {
		try {
			Optional<OneMedia> onemedia = this.onemediaRepository.findById(id);

			if (!onemedia.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Project not found"), HttpStatus.BAD_REQUEST);

				Optional<User> user = userRepository
                .findById(id); 

        if (!user.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);
            
			onemedia.get().setUser(user.get());
			onemedia.get().setTitle(onemediaRequest.getTitle());
			onemedia.get().setImage(onemediaRequest.getImage());
			onemedia.get().setDescription(onemediaRequest.getDescription());
			onemedia.get().setStatus(RecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.onemediaRepository.save(onemedia.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
}

@Transactional
public ResponseEntity<?> delete(long id) {
    try {
        this.onemediaRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }


}

@Transactional
public ResponseEntity<?> listById(long id) {
    try {
        Optional<OneMedia> onemedia = this.onemediaRepository.findById(id);

        if (!onemedia.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", onemedia), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}


/*@Transactional
public ResponseEntity<?> listByUserId(long user_id) {
    try {
        Optional<Media> media = this.mediaRepository.findByUser(user_id);

        if (!media.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", media), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}*/
@Transactional
    public ResponseEntity<?> listByUserId(long id) {
        return new ResponseEntity<>(
                new ApiResponse(true, "", this.onemediaRepository.findByUserIdAndStatus(id, RecordStatus.ACTIVE)),
                HttpStatus.OK);
    }

}




