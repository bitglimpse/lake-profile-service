package com.saasbass.lakeprofileservice.endpoint;

import com.saasbass.lakeprofileservice.domain.LakeProfile;
import com.saasbass.lakeprofileservice.domain.LakeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.util.Optional;

@RestController
@RequestMapping("/lake-profile")
public class Endpoint {

    @Autowired
    LakeProfileRepository lakeProfileRepository;

    private int addLakeProfileRequestCount = 0;
    private int getLakeProfileRequestCount = 0;

    @RequestMapping(value = "/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLakeProfile(@PathVariable Long profileId) throws SocketException {
        // Simulate a temporary socket exception caused by temporary server overload
        if (getLakeProfileRequestCount < 2) {
            getLakeProfileRequestCount++;
            throw new SocketException();
        }

        Optional<LakeProfile> lakeProfile = lakeProfileRepository.findById(profileId);
        if (!lakeProfile.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        getLakeProfileRequestCount = 0;
        return new ResponseEntity(lakeProfile, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLakeProfile(@RequestBody LakeProfile lakeProfile) throws InterruptedException, SocketException {
        // Simulate a temporary socket exception caused by temporary server overload
        if (addLakeProfileRequestCount < 2) {
            addLakeProfileRequestCount++;
            throw new SocketException();
        }

        LakeProfile savedProfile = new LakeProfile();
        Thread.sleep(2000); // This simulates real-world database latency
        savedProfile.setId(lakeProfileRepository.save(lakeProfile).getId());
        addLakeProfileRequestCount = 0;
        return new ResponseEntity(savedProfile, HttpStatus.ACCEPTED);
    }
}
