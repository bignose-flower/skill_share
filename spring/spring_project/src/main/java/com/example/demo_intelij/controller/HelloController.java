package com.example.demo_intelij.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_intelij.service.HelloService;

// @RestController : ControllerクラスがRESTful APIとして認識されるようになる。
// Webからのリクエストを受け付けるクラス
@RestController
public class HelloController {

    // @Autowired : SpringBoot起動時に@Serviceや@Entity、@Repositoryなどのアノテーションがついたクラスがメモリ上に登録される。
    // それらのクラスは既にインスタンスが作られているので、@Autowiredがついたフィールドに自動的に変数代入される。
    // そのためこのフィールドではインスタンスの作成が不要となる。
    @Autowired
    private HelloService helloService;

    // 文字列を返却するメソッド
    // @RequestMapping : 指定したURLにリクエストが飛んでくると、処理が実行される。
    // ex) http://localhost:8081/hello
    @RequestMapping("/hello")
    public String hello(){
        return "hello world on docker!";
    }

    // DBに登録されているデータを返却するメソッド
    // ex) http://localhost:8081/testdb
    @RequestMapping("/testdb")
    public List<String> getData(){
        return helloService.getColumns();
    }

    // 指定されたindexのデータをDBから取得して返却するメソッド
    @RequestMapping("/testdb/{id}")
    public String getDataById(@PathVariable Long id){
        return helloService.getColumnById(id);
    }
}
