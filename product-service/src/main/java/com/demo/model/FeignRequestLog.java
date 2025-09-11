package com.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feign_request_logs", indexes = {@Index(name = "idx_request_id", columnList = "request_id")})
public class FeignRequestLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "method", length = 10)
    private String method;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Column(name = "query", columnDefinition = "TEXT")
    private String query;

    @Column(name = "status")
    private Integer status;

    @Column(name = "request_time", columnDefinition = "BIGINT")
    private Long requestTime;

    @Lob
    @Column(name = "request_header", columnDefinition = "TEXT")
    private String requestHeader;

    @Lob
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Lob
    @Column(name = "response_header", columnDefinition = "TEXT")
    private String responseHeader;

    @Lob
    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "request_id")
    private String requestId;
}
