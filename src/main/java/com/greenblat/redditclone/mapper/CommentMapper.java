package com.greenblat.redditclone.mapper;

import com.greenblat.redditclone.dto.CommentDto;
import com.greenblat.redditclone.model.Comment;
import com.greenblat.redditclone.model.Post;
import com.greenblat.redditclone.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment map(CommentDto commentDto, Post post, User user) {
        Comment comment = new Comment();

        if (commentDto != null) {
            comment.setText(commentDto.getText());
        }
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedDate(java.time.Instant.now());

        return comment;
    }

    public CommentDto mapToDto(Comment comment) {
        if (comment == null)
            return null;

        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setText(comment.getText());

        commentDto.setUsername(comment.getUser().getUsername());
        commentDto.setPostId(comment.getPost().getPostId());

        return commentDto;
    }
}
