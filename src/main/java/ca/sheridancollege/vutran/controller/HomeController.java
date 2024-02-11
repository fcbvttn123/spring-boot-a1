package ca.sheridancollege.vutran.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/deleteStreamingService/{id}")
	public String deleteStreamingService(Model model, @PathVariable("id") Long id) {
		sr.deleteById(id);
		List<Podcast> removedPodcastList = pr.findByStreamingServiceId(id);
		for (Podcast podcast : removedPodcastList) {
            pr.delete(podcast);
        }
		
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
	
	@GetMapping("/deletePodcast/{id}")
	public String deletePodcast(Model model, @PathVariable("id") Long id) {
		Podcast removedPodcast = pr.findById(id).get();
		Long stIdOfRemovedPodcast = removedPodcast.getStreamingServiceId();
		StreamingService stOfRemovedPodcast = sr.findById(stIdOfRemovedPodcast).get();
		List<Podcast> stPodcastListOfRemovedPodcast = stOfRemovedPodcast.getPodcastList();
		
		List<Podcast> updatedStPodcastListOfRemovedPodcast = new ArrayList<Podcast>();
		for(Podcast p : stPodcastListOfRemovedPodcast) {
			if(p.getId() != id) {
				updatedStPodcastListOfRemovedPodcast.add(p);
			}
		}
		stOfRemovedPodcast.setPodcastList(updatedStPodcastListOfRemovedPodcast);
		pr.deleteById(id);
		
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
	
	@GetMapping("/updateStreamingService/{id}")
	public String updateSt(Model model, @PathVariable("id") Long id) {
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("st", sr.findById(id));
		return "updateSt";
	}
	
	@PostMapping("/updateStreamingService") 
	public String updateStFormSubmission(Model model, @ModelAttribute StreamingService st) {
		Optional<StreamingService> mySt = sr.findById(st.getId());
		StreamingService updatedSt = mySt.get();
		updatedSt.setName(st.getName());
		updatedSt.setLink(st.getLink());
		sr.save(updatedSt);
		
		model.addAttribute("podcast", new Podcast());
		model.addAttribute("streamingService", new StreamingService());
		model.addAttribute("podcastList", pr.findAll());
		model.addAttribute("streamingServiceList", sr.findAll());
		return "index";
	}
	
	@GetMapping("/updatePodcast/{id}")
	public String updatePodcast(Model model, @PathVariable("id") Long id) {
		model.addAttribute("podcast", pr.findById(id));
		model.addAttribute("streamingServiceList", sr.findAll());
		return "updatePodcast";
	}
	
	@PostMapping("/updatePodcast") 
	public String updatePodcastFormSubmission(Model model, @ModelAttribute Podcast podcast) {
		Optional<Podcast> myPodcast = pr.findById(podcast.getId());
		Podcast updatedPodcast = myPodcast.get();
		updatedPodcast.setLink(podcast.getLink());
		updatedPodcast.setReleaseyear(podcast.getReleaseyear());
		updatedPodcast.setStarRating(podcast.getStarRating());
		updatedPodcast.setTitle(podcast.getTitle());
		pr.save(updatedPodcast);
		
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
		p.setStreamingServiceId(s.getId());
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
