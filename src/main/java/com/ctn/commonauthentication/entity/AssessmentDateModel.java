package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "assessment_date")
public class AssessmentDateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_date_id")
    private Integer assessmentDateId;

    @Column(name = "appraisalid")
    private Long appraisalId;

    @Column(name = "assessment_date")
    private Timestamp assessmentDate;

    @Column(name = "priority")
    private Integer priority;
}
