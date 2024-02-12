package ca.sheridancollege.vutran.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vutran.beans.Podcast;

public interface PodcastRepo extends JpaRepository<Podcast, Long> {
	public List<Podcast> findByStreamingServiceId(Long id);
	public List<Podcast> findByOrderByReleaseyearDesc();
}
