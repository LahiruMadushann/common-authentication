package com.ctn.commonauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "body_type_master")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BodyType {

    @Id
    @Column(name = "bodytypeid")
    private Integer bodyTypeId;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "body_type")
    private String bodyType;
}
