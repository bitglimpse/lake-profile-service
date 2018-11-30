package com.saasbass.lakeprofileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lake-profile")
public class Endpoint {

    @Autowired
    LakeProfileRepository lakeProfileRepository;

    @RequestMapping(value = "/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLakeProfile(@PathVariable Long profileId) {
        return new ResponseEntity(lakeProfileRepository.findById(profileId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLakeProfile(@RequestBody LakeProfile lakeProfile) {
        LakeProfile savedProfile = new LakeProfile();
        savedProfile.setId(lakeProfileRepository.save(lakeProfile).getId());
        return new ResponseEntity(savedProfile, HttpStatus.ACCEPTED);
    }
}
