package com.greenblat.redditclone.service;

import com.greenblat.redditclone.dto.VoteDto;
import com.greenblat.redditclone.exception.PostNotFoundException;
import com.greenblat.redditclone.exception.RedditException;
import com.greenblat.redditclone.model.Post;
import com.greenblat.redditclone.model.User;
import com.greenblat.redditclone.model.Vote;
import com.greenblat.redditclone.model.VoteType;
import com.greenblat.redditclone.repository.PostRepository;
import com.greenblat.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + voteDto.getPostId()));
        User user = authService.getCurrentUser();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }

        if (voteDto.getVoteType().equals(VoteType.UPVOTE)) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        postRepository.save(post);

        voteRepository.save(mapToVote(voteDto, post, user));
    }

    private Vote mapToVote(VoteDto voteDto, Post post, User user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
