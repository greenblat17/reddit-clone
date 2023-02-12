package com.greenblat.redditclone.service;

import com.greenblat.redditclone.exception.RedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    @Test
    @DisplayName("Test should pass when comment do not contains swear words")
    void shouldNotContainsSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null,
                null, null, null, null);
        assertThat(commentService.containsSwearWords("This is a clean comment")).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when comment contains swear words")
    void shouldFailWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null,
                null, null, null, null);
        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(RedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}