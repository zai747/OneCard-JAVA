package com.mdot.app.services;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import com.mdot.app.models.Friend;
import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.FriendRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.FriendRepository;
import com.mdot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class FriendService {
    


	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private UserRepository userRepository;

    
	@Transactional
	public ResponseEntity<?> save(long id, Long fid) {
		try {

			Friend friend = new Friend();

          
            Optional<User> user = userRepository.findById(id);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

				Optional<User> friendUser = userRepository.findById(fid);

				if (!friendUser.isPresent())
					return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);
		

			friend.setId((long) 0);
            friend.setUser(user.get());
			friend.setFriend(friendUser.get());
		
	
		    friend.setStatus(RecordStatus.ACTIVE);
			friend = this.friendRepository.save(friend);

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

				Optional<User> user = userRepository
                .findById(id); 

        if (!user.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			friend.get().setUser(user.get());
			friend.get().setStatus(RecordStatus.ACTIVE);

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

@Transactional
	public ResponseEntity<?> list() {
		try {
			List<Friend> list = this.friendRepository.findAll();
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByUserAndStatus(long id, String status) {
		try {
			RecordStatus recordStatus = RecordStatus.ACTIVE;

			switch (status) {
			case "INACTIVE":
				recordStatus = RecordStatus.INACTIVE;
				break;
			case "DELETED":
				recordStatus = RecordStatus.DELETED;
				break;
			default:      
				recordStatus = RecordStatus.ACTIVE;
				break;
			}

			List<Friend> list = this.friendRepository.findByUserIdAndStatus(id, recordStatus);
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
    public ResponseEntity<?> listByUserId(long id) {
        return new ResponseEntity<>(
                new ApiResponse(true, "", this.friendRepository.findByUserIdAndStatus(id, RecordStatus.ACTIVE)),
                HttpStatus.OK);
    }
}



