package com.greenblat.redditclone.mapper;

import com.greenblat.redditclone.dto.SubredditDto;
import com.greenblat.redditclone.model.Post;
import com.greenblat.redditclone.model.Subreddit;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SubredditMapper {

    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if (subreddit == null)
            return null;

        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    public Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
        if (subredditDto == null) {
            return null;
        }

        return Subreddit.builder()
                .id(subredditDto.getId())
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }
}
