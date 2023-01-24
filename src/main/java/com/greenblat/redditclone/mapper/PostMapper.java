package com.greenblat.redditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.greenblat.redditclone.dto.PostRequest;
import com.greenblat.redditclone.dto.PostResponse;
import com.greenblat.redditclone.model.*;
import com.greenblat.redditclone.repository.CommentRepository;
import com.greenblat.redditclone.repository.VoteRepository;
import com.greenblat.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PostMapper {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {
        if (postRequest == null && subreddit == null && user == null)
            return null;

        Post.PostBuilder postBuilder = Post.builder();
        if (postRequest != null) {
            postBuilder.description(postRequest.getDescription());
            postBuilder.postId(postRequest.getPostId());
            postBuilder.postName(postRequest.getPostName());
            postBuilder.url(postRequest.getUrl());
        }
        if (subreddit != null) {
            postBuilder.subreddit(subreddit);
        }
        if (user != null) {
            postBuilder.user(user);
        }
        postBuilder.createdDate(Instant.now());
        postBuilder.voteCount(0);

        return postBuilder.build();
    }

    public PostResponse mapToDto(Post post) {
        if (post == null)
            return null;

        PostResponse postResponse = new PostResponse();

        postResponse.setId(post.getPostId());
        postResponse.setUserName(postUserUsername(post));
        postResponse.setSubredditName(postSubredditName(post));
        postResponse.setPostName(post.getPostName());
        postResponse.setUrl(post.getUrl());
        postResponse.setDescription(post.getDescription());
        postResponse.setDuration(getDuration(post));
        postResponse.setCommentCount(commentCount(post));
        postResponse.setVoteCount(post.getVoteCount());
        postResponse.setDownVote(isPostDownVoted(post));
        postResponse.setUpVote(isPostUpVoted(post));

        return postResponse;
    }

    private String postUserUsername(Post post) {
        if (post == null) {
            return null;
        }

        User user = post.getUser();
        if (user == null) {
            return null;
        }
        return user.getUsername();
    }

    private String postSubredditName(Post post) {
        if (post == null) {
            return null;
        }

        Subreddit subreddit = post.getSubreddit();
        if (subreddit == null) {
            return null;
        }

        return subreddit.getName();
    }

    private Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    private boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    private boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
