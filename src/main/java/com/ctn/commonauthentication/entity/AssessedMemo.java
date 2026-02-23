package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessed_memo")
public class AssessedMemo implements Serializable {

    @EmbeddedId
    private AssessedMemoId id;

    @Column(name = "memo_text", nullable = false)
    private String memoText;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Embeddable
    public static class AssessedMemoId implements Serializable {

        @Column(name = "appraisalid", nullable = false)
        private Long appraisalId;

        @Column(name = "shopid", nullable = false)
        private Integer shopId;

        @Column(name = "id", nullable = false)
        private Integer id;
    }
}
