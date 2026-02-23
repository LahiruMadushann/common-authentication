package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "branch_map")
@Data
public class HeadBranchMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "head_branch_id")
    private int headBranchId;
    @Column(name = "branch_id")
    private int branch;
}
