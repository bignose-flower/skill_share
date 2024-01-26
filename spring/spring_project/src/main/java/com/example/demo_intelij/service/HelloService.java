package com.example.demo_intelij.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_intelij.entity.Sample;
import com.example.demo_intelij.repository.SampleRepository;

// @Service : Serviceクラスであることを示す
// Serviceクラスは基本的にControllerクラスから指令が飛んでくる
// 複雑なロジックを記述する
@Service
public class HelloService {

    @Autowired
    private SampleRepository sampleRepository;

    // Sampleテーブルからデータを取得して、カラム「column1」の値を取得し、Listとして返却
       public List<String> getColumns() {
        // repositoryのメソッドを利用して、DBからデータを取得する
        return sampleRepository.findAll()
            .stream()
            .map(entity -> entity.getColumn1())
            .toList();
    }

    // Sampleテーブルのうち指定されたindexに合致するレコードのカラム「column1」の値を返却
    // 存在しない場合はエラーメッセージを返却
    public String getColumnById(Long id){
        Sample entity = sampleRepository.findById(id).orElse(null);
        Optional<Sample> obj =  Optional.ofNullable(entity);

        if(obj.isPresent()) { 
            return  obj.get().getColumn1();
        }
        
        return String.format("index %d にデータが存在しません。",id);
    }
}
