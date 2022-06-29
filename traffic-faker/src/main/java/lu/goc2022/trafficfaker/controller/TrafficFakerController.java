package lu.goc2022.trafficfaker.controller;

import lu.goc2022.trafficfaker.model.TrafficFakerRequest;
import lu.goc2022.trafficfaker.service.TrafficFakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TrafficFakerController {

    @Autowired
    private TrafficFakerService trafficFakerService;

    Logger logger = LoggerFactory.getLogger(TrafficFakerController.class);

    @RequestMapping(value="/traffic-faker", method= RequestMethod.POST)
    public ResponseEntity generateFakeTraffic(@RequestBody TrafficFakerRequest request) {
        logger.info("Begin generateFakeTraffic...");

        trafficFakerService.generateFakeTraffic(request.getUrl());

        HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

        logger.info("End generateFakeTraffic...");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
