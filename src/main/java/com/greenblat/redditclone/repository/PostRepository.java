package com.greenblat.redditclone.repository;

import com.greenblat.redditclone.model.Post;
import com.greenblat.redditclone.model.Subreddit;
import com.greenblat.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
