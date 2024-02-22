package com.booksearch.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_recommend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRecommendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "up")
    private Long up;

    @Column(name = "down")
    private Long down;
}
