package com.example.demo_intelij.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_intelij.entity.Sample;

// @Repository : Repositoryクラスであることを示す
// JpaRepositoryを継承することで、簡単なSQL命令を実行することが可能。
// RepositoryクラスはAPIとDBの仲介となるクラス
@Repository
public interface SampleRepository extends JpaRepository<Sample, Long>{
}
