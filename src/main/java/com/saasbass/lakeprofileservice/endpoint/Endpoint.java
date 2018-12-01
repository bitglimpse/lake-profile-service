package com.saasbass.lakeprofileservice.endpoint;

import com.saasbass.lakeprofileservice.domain.LakeProfile;
import com.saasbass.lakeprofileservice.domain.LakeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/lake-profile")
public class Endpoint {

    @Autowired
    LakeProfileRepository lakeProfileRepository;

    @RequestMapping(value = "/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLakeProfile(@PathVariable Long profileId) {
        Optional<LakeProfile> lakeProfile = lakeProfileRepository.findById(profileId);
        if (!lakeProfile.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(lakeProfile, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLakeProfile(@RequestBody LakeProfile lakeProfile) throws InterruptedException {
        LakeProfile savedProfile = new LakeProfile();
        Thread.sleep(2000); // This simulates real-world database latency
        savedProfile.setId(lakeProfileRepository.save(lakeProfile).getId());
        return new ResponseEntity(savedProfile, HttpStatus.ACCEPTED);
    }
}
