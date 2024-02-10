package ca.sheridancollege.vutran.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vutran.beans.Podcast;

public interface PodcastRepo extends JpaRepository<Podcast, Long> {

}
