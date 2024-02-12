package ca.sheridancollege.vutran.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vutran.beans.StreamingService;

public interface StreamingServiceRepo extends JpaRepository<StreamingService, Long> {
	public StreamingService findByPodcastListId(Long id);
}
