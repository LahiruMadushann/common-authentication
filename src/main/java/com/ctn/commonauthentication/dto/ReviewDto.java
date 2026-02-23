package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long appraisalid;
    private Long shopid;
    private Double starValue;
    private Double starSupport;
    private Double starRecommendation;
    private String review;
}
