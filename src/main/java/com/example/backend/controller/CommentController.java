package com.example.backend.controller;

import com.example.backend.document.Comment;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.comment.*;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentItem> addComment(@RequestBody CommentCreate commentCreate) {
        return ResponseEntity.ok(commentService.addComment(commentCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable String id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody CommentUpdate newComment) {
        return ResponseEntity.ok(commentService.updateComment(id, newComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiObject> deleteComment(@PathVariable String id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }

    @PostMapping("/{commentId}/replies")
    public ResponseEntity<CommentDto> addReply(@PathVariable String commentId, @RequestBody ReplyCreate reply) {
        return ResponseEntity.ok(commentService.addReply(commentId, reply));
    }

    @DeleteMapping("/{commentId}/replies/{replyId}")
    public ResponseEntity<Comment> deleteReply(@PathVariable String commentId, @PathVariable String replyId) {
        return ResponseEntity.ok(commentService.deleteReply(commentId, replyId));
    }
}
