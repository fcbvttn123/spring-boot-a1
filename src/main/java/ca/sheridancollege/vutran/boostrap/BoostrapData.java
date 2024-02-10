package ca.sheridancollege.vutran.boostrap;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ca.sheridancollege.vutran.beans.Podcast;
import ca.sheridancollege.vutran.beans.StreamingService;
import ca.sheridancollege.vutran.repo.PodcastRepo;
import ca.sheridancollege.vutran.repo.StreamingServiceRepo;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BoostrapData implements CommandLineRunner {
	
	private PodcastRepo pr;
	private StreamingServiceRepo sr;

	@Override
	public void run(String... args) throws Exception {
		Podcast p1 = Podcast.builder()
				.title("The Joe Rogan Experience")
				.releaseyear(2020)
				.starRating(4)
				.link("Link")
				.streamingService("Apple Podcasts")
				.build();	
		Podcast p2 = Podcast.builder()
				.title("Serial")
				.releaseyear(2019)
				.starRating(2)
				.link("Link")
				.streamingService("Google Podcasts")
				.build();
		Podcast p3 = Podcast.builder()
				.title("The Daily")
				.releaseyear(2023)
				.starRating(5)
				.link("Link")
				.streamingService("Spotify")
				.build();
	
		StreamingService s1 = StreamingService.builder()
				.name("Spotify")
				.link("Link")
				.podcastList(new ArrayList<Podcast>())
				.build();
		s1.getPodcastList().add(p3);
		StreamingService s2 = StreamingService.builder()
				.name("Apple Podcasts")
				.link("Link")
				.podcastList(new ArrayList<Podcast>())
				.build();
		s2.getPodcastList().add(p1);
		StreamingService s3 = StreamingService.builder()
				.name("Google Podcasts")
				.link("Link")
				.podcastList(new ArrayList<Podcast>())
				.build();
		s3.getPodcastList().add(p2);
		
		pr.save(p1);
		pr.save(p2);
		pr.save(p3);
		
		sr.save(s1);
		sr.save(s2);
		sr.save(s3);
	}

}
