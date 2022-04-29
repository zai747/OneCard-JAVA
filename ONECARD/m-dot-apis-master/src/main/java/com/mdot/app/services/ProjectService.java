package com.mdot.app.services;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.mdot.app.models.Project;
import com.mdot.app.models.RecordStatus;
import com.mdot.app.models.User;
import com.mdot.app.payloads.requests.ProjectRequest;
import com.mdot.app.payloads.responses.ApiResponse;
import com.mdot.app.repositories.ProjectRepository;
import com.mdot.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ProjectService {
    


	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

    
	@Transactional
	public ResponseEntity<?> save(@Valid 	ProjectRequest projectRequest,long id) {
		try {

			Project project = new Project();

            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);              

			project.setId((long) 0);
            project.setUser(user.get());
			project.setTitle(projectRequest.getTitle());
			project.setImage(projectRequest.getImage());
            project.setDescription(projectRequest.getDescription());
            
			project.setStatus(RecordStatus.ACTIVE);
            project = this.projectRepository.save(project);


			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", project), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}		

	}

@Transactional
	public ResponseEntity<?> update(long id, @Valid ProjectRequest projectRequest) {
		try {
			Optional<Project> project = this.projectRepository.findById(id);

			if (!project.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			project.get().setTitle(projectRequest.getTitle());
			project.get().setImage(projectRequest.getImage());
			project.get().setDescription(projectRequest.getDescription());
			project.get().setStatus(RecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.projectRepository.save(project.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
}

@Transactional
public ResponseEntity<?> delete(long id) {
    try {
        this.projectRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }


}

@Transactional
public ResponseEntity<?> listById(long id) {
    try {
        Optional<Project> project = this.projectRepository.findById(id);

        if (!project.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", project), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}


@Transactional
public ResponseEntity<?> listByUserId(long user_id) {
    try {
        Optional<Project> project = this.projectRepository.findByUser(user_id);

        if (!project.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(true, "", project), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
    }
}


}




