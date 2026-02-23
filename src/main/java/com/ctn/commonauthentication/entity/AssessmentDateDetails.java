package com.ctn.commonauthentication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_date")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDateDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_date_id")
    private Long assessmentDateId;

    @Column(name = "appraisalid", insertable = false, updatable = false)
    private Long appraisalId;

    @ManyToOne
    @JoinColumn(name = "appraisalid", insertable = false, updatable = false)
    @JsonBackReference
    private AppraisalRequestInformation appraisalRequestInformation;

    @Column(name = "assessment_date")
    private LocalDateTime assessmentDate;
}
