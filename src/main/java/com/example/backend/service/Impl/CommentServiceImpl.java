package com.example.backend.service.Impl;

import com.example.backend.document.Comment;
import com.example.backend.document.Product;
import com.example.backend.document.Seller;
import com.example.backend.document.User;
import com.example.backend.dto.ApiObject;
import com.example.backend.dto.comment.*;
import com.example.backend.exception.error.ErrorSystemException;
import com.example.backend.exception.error.NotFoundException;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.SellerRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    @Override
    public CommentItem addComment(CommentCreate commentCreate) {
        Product product = productRepository.findById(commentCreate.getProductId())
                .orElseThrow(()->new NotFoundException("Không tìm thấy sản phẩm với id : "+commentCreate.getProductId()));
        Comment comment = Comment.builder()
                .rating(commentCreate.getRating())
                .product(product)
                .content(commentCreate.getContent())
                .datePosted(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .status(true)
                .build();
        if (commentCreate.isStoreOwner()){
            comment.setCreatedById(product.getCreatedBy().getId());
        }else{
            comment.setCreatedById(commentCreate.getCreatedById());
        }
        commentRepository.save(comment);
        updateProductAverageRating(comment);
        return convertCommentItem(comment);
    }
    private void updateProductAverageRating(Comment comment) {
        List<Comment> comments = commentRepository.findAllByProduct(comment.getProduct());

        double averageRating = comments.stream()
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0.0);

        Product product = productRepository.findById(comment.getProduct().getId())
                .orElseThrow(()->new NotFoundException("Không tìm thấy sản phẩm với id : "+comment.getProduct().getId()));
        product.setRating(averageRating);
        productRepository.save(product);
    }

    @Override
    public CommentDto getCommentById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Không tìm thấy bình luận nào với id : "+id));
        return convertCommentDto(comment);
    }

    @Override
    public Comment updateComment(String id, CommentUpdate newComment) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new NotFoundException("Không tìm thấy bình luận nào với id : "+id));
        if (comment.getCreatedById().equals(newComment.getCreatedById())){
            comment.setContent(newComment.getContent());
            comment.setRating(newComment.getRating());
            comment.setUpdatedOn(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime());
            commentRepository.save(comment);
            return comment;
        }else{
            throw new NotFoundException("Bạn không có quyền chỉnh sửa bình luận.");
        }
    }

    @Override
    public ApiObject deleteComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy bình luận nào với id : "+id));
        comment.setStatus(false);
        commentRepository.save(comment);
        return ApiObject.builder().message("Xóa bình luận thành công!").build();
    }

    @Override
    public CommentDto addReply(String commentId, ReplyCreate reply) {
        Comment optionalComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy bình luận này với id : "+commentId));
        Product product = productRepository.findById(optionalComment.getProduct().getId())
                .orElseThrow(()->new NotFoundException("Không tìm thấy sản phẩm với id : "+optionalComment.getProduct().getId()));
        Comment.Reply replyNew = Comment.Reply.builder()
                .id(getReplyId(commentId))
                .userId(reply.isStoreOwner() ? product.getCreatedBy().getId() : reply.getUserId())
                .content(reply.getContent())
                .datePosted(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .build();
        if (optionalComment.getReplies() == null){
            optionalComment.setReplies(Collections.singletonList(replyNew));
        }else{
            optionalComment.getReplies().add(replyNew);
        }
        commentRepository.save(optionalComment);
        return convertCommentDto(optionalComment);
    }

    @Override
    public Comment deleteReply(String commentId, String replyId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy bình luận với id : "+ commentId));
        comment.getReplies().removeIf(item -> item.getId().equals(replyId));
        commentRepository.save(comment);
        return comment;
    }

    public CommentItem convertCommentItem(Comment comment){
        return CommentItem.builder()
                .id(comment.getId())
                .productId(comment.getProduct().getId())
                .createdById(comment.getCreatedById())
                .datePosted(comment.getDatePosted())
                .content(comment.getContent())
                .rating(comment.getRating())
                .status(comment.isStatus())
                .build();
    }
    public CommentDto convertCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .productId(comment.getProduct().getId())
                .commentBy(getInfo(comment.getCreatedById()))
                .content(comment.getContent())
                .status(comment.isStatus())
                .rating(comment.getRating())
                .datePosted(comment.getDatePosted())
                .replies(
                        comment.getReplies() == null
                        ? null : comment.getReplies().stream().map(this::convertReplyDto).collect(Collectors.toList())
                )
                .build();
    }
    public CommentDto.Reply convertReplyDto(Comment.Reply reply){
        return CommentDto.Reply.builder()
                .id(reply.getId())
                .commentBy(getInfo(reply.getUserId()))
                .content(reply.getContent())
                .datePosted(reply.getDatePosted())
                .build();
    }
    public CreatedCommentBy getInfo(String id){
        if (sellerRepository.existsById(id)){
            Seller seller = sellerRepository.findById(id)
                    .orElseThrow(()->new ErrorSystemException("Lỗi hệ thống trong việc tìm dữ liệu với id : "+id));
            return CreatedCommentBy.builder()
                    .name(seller.getName())
                    .avatar(seller.getLogo().getId())
                    .isStoreOwner(true)
                    .build();
        }else if(userRepository.existsById(id)){
            User user = userRepository.findById(id)
                    .orElseThrow(()->new ErrorSystemException("Lỗi hệ thống trong việc tìm dữ liệu với id : "+id));
            return CreatedCommentBy.builder()
                    .name(user.getFullName())
                    .avatar(null)
                    .isStoreOwner(false)
                    .build();
        }else {
            throw new NotFoundException("Không tìm thấy thông tin người bình luận!");
        }
    }
    public String getReplyId(String commentId) {
        List<Comment> comments = commentRepository.findAll();
        Set<String> replies = comments.stream()
                .flatMap(comment -> {
                    if (comment.getReplies() == null) {
                        return Stream.empty(); // Trả về một stream rỗng nếu replies là null
                    } else {
                        return comment.getReplies().stream(); // Trả về stream của replies nếu không null
                    }
                })
                .map(Comment.Reply::getId)
                .collect(Collectors.toSet());

        if (replies.isEmpty()) {
            return generateUniqueId(commentId);
        } else {
            return generateUniqueId(replies, commentId);
        }
    }

    public String generateUniqueId(Set<String> existingIds, String commentId) {
        String newId;
        do {
            newId = commentId + UUID.randomUUID();
        } while (existingIds.contains(newId)); // Kiểm tra xem ID đã tồn tại chưa
        return newId;
    }

    public String generateUniqueId(String commentId) {
        return generateUniqueId(Set.of(), commentId);
    }

}
