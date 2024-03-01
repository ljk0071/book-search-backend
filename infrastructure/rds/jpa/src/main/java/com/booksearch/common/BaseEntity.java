package com.booksearch.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base Entity class by [MappedSuperClass]
 *
 * @property createdAt [LocalDateTime] dateTime when entity is first created.
 * @property updatedAt [LocalDateTime] dateTime when entity is last modified.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "REGISTER_DATE_TIME", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE_TIME")
    private LocalDateTime updatedAt;

}
