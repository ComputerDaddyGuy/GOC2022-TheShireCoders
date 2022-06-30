package lu.goc2022.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.orm.PhishingEventRepository;

@RestController
@AllArgsConstructor
public class PhishingEventController {

	private final PhishingEventRepository phishingEventRepository;

	@GetMapping("/phishing-events")
	public Iterable<PhishingEvent> getAll() {
		return phishingEventRepository.findAll();
	}

	@GetMapping("/phishing-events/:id")
	public Optional<PhishingEvent> getById(@PathVariable("id") Long id) {
		return phishingEventRepository.findById(id);
	}

}
