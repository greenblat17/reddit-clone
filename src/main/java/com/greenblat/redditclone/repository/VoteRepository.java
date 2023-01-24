package com.greenblat.redditclone.repository;

import com.greenblat.redditclone.model.Post;
import com.greenblat.redditclone.model.User;
import com.greenblat.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
