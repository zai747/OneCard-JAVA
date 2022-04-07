package com.mdot.app.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.mdot.app.models.Friend;
import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.WideRecordStatus;
import com.mdot.app.payloads.requests.FriendRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.FriendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class FriendService {
    


	@Autowired
	private FriendRepository friendRepository;

    
	@Transactional
	public ResponseEntity<?> save(@Valid FriendRequest friendRequest) {
		try {
			Friend friend = new Friend();

			friend.setId((long) 0);
			
		
		
			friend.setStatus(WideRecordStatus.ACTIVE);
			

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", friend), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

@Transactional
	public ResponseEntity<?> update(long id, @Valid FriendRequest friendRequest) {
		try {
			Optional<Friend> friend = this.friendRepository.findById(id);

			if (!friend.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			
			friend.get().setStatus(WideRecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.friendRepository.save(friend.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
}

@Transactional
public ResponseEntity<?> delete(long id) {
    try {
        this.friendRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }


}

@Transactional
public ResponseEntity<?> listById(long id) {
    try {
        Optional<Friend> friend = this.friendRepository.findById(id);

        if (!friend.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", friend), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}
}




