package com.slippi.replays.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "replays")
public class ReplayEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "connect_code")
    private String connectCode;

    @Column(name = "slp_name")
    private String slpName;

    @Lob
    @Column(name = "slp_data")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] slpData;
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public ReplayEntity() {
    }

    public ReplayEntity(UUID id, String connectCode, String slpName, byte[] slpData, OffsetDateTime createdAt) {
        this.id = id;
        this.connectCode = connectCode;
        this.slpName = slpName;
        this.slpData = slpData;
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

    public String getSlpName() {
        return this.slpName;
    }

    public void setSlpName(String slpName) {
        this.slpName = slpName;
    }

    public byte[] getSlpData() {
        return this.slpData;
    }

    public void setSlpData(byte[] slpData) {
        this.slpData = slpData;
    }


    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
