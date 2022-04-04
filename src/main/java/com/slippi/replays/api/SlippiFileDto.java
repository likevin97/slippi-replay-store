package com.slippi.replays.api;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SlippiFileDto {
    private UUID id;
    private String connectCode;
    private String fileName; 
    private String contentType;
    private OffsetDateTime createdAt;

    public SlippiFileDto() {
    }

    public SlippiFileDto(UUID id, String connectCode, String fileName, String contentType, OffsetDateTime createdAt) {
        this.id = id;
        this.connectCode = connectCode;
        this.fileName = fileName;
        this.contentType = contentType;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getConnectCode() {
        return this.connectCode;
    }

    public void setConnectCode(String connectCode) {
        this.connectCode = connectCode;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
