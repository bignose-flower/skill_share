package com.example.demo_intelij.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


 
 // @Configuration : 設定ファイルであることを示す
 // APIサーバで設定を行うときに下記のクラスに設定を行っていく。
 @Configuration
public class DemoAppConfig {
    
    // 今回はnuxtからSpringBootで立ち上げたAPIに対してリクエストが飛んでくるので、
    // CORS(CrossOriginResourceSharing)に引っかかる。
    // APIがnuxtからのリクエストを許可できるような設定を行う。
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3001"); // 許可するURL
        source.registerCorsConfiguration("/**", config); // あらゆるURLへのリクエストに対してconfigが適用される
        return new CorsFilter(source);
    }
    
}
