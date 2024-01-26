package com.example.demo_intelij.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity : Entityクラスであることを示す
// Entityクラスはテーブルのデータそのものを示すクラス
// Sampleクラスの場合は、sampleテーブル : [idカラム,column1カラム]という構成になっていることがわかる
@Entity
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String column1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }
}
