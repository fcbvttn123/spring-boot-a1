package ca.sheridancollege.vutran.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.vutran.beans.Podcast;
import ca.sheridancollege.vutran.beans.StreamingService;
import ca.sheridancollege.vutran.repo.PodcastRepo;
import ca.sheridancollege.vutran.repo.StreamingServiceRepo;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	private PodcastRepo pr;
	private StreamingServiceRepo sr;
	
	@GetMapping("/")
	public String indexHTML(Model model) {
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
	
	@PostMapping("/addPodcast")
	public String addPodcastFormSubmission(Model model, @ModelAttribute Podcast p, @RequestParam Long streamingServiceId) {
		StreamingService s = sr.findById(streamingServiceId).get();
		p.setStreamingService(s.getName());
		pr.save(p);
		s.getPodcastList().add(p);
		sr.save(s);
		
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
	
	@PostMapping("/addstreamingService")
	public String addstreamingServiceFormSubmission(Model model, @ModelAttribute StreamingService s) {
		sr.save(s);
		
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
}
