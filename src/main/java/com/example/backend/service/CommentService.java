package com.example.backend.service;

import com.example.backend.document.Comment;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.comment.*;

public interface CommentService {
    CommentItem addComment(CommentCreate commentCreate);

    CommentDto getCommentById(String id);

    Comment updateComment(String id, CommentUpdate newComment);

    ApiObject deleteComment(String id);

    CommentDto addReply(String commentId, ReplyCreate reply);

    Comment deleteReply(String commentId, String replyUserId);
}
