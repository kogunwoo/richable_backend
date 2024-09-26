package com.idle.kb_i_dle_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.idle.kb_i_dle_backend")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {
    public WebConfig(){
        System.out.println("WebConfig created");
    }

    //controller에서  return "jsp페이지명";
    // /WEB-INF/views/jsp페이지명.jsp
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    //front -> backend : 서로 다른 프로세스이다.
    //front 프로세스와  backend 프로세스는 서로 다르다
    //front에서 자바스크립트로  -> backend 에 접근시도하면 CORS error
    //CORS 해제는 헤더에 추가정보(이거 와도 돼)
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //CORS정책오류 해결
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:5173");//frontdomain
        //.allowedMethods("GET", "POST", "get", "post"); //대소문자 다 써야 함

//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "get", "post") // 허용할 HTTP method
//                .allowCredentials(true) // 쿠키 인증 요청 허용
//                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱

    }


    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        //css, javascript, image
        registry.addResourceHandler("/**")
                .addResourceLocations("/resources/");

        //fileupload를 위함
        exposeDirectory("fileupload", registry);
    }


    //파일 업로드 관련 part
    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setResolveLazily(true);
        resolver.setMaxUploadSize(20*1024*1024);
        resolver.setMaxUploadSizePerFile(20*1024*1024);
        return resolver;
    }

    //파일을 업로드하면 http url로 접근이 되도록 해주기 위한모드
    //파일을 업로드 하려면 물리적 경로 c:/myhome/fileupload  ~~~~  http://localhost:8080/fileupload

//    public static final String FILEUPLOADPATH="fileupload";
//    public static final String DOMAIN="http://127.0.0.1:9090";


    //https://spring.io/guides/gs/uploading-files
    void exposeDirectory(String dirName,  ResourceHandlerRegistry registry)
    {
        Path uploadDir = Paths.get(dirName);
        //업로드 폴더의 물리적 구조(절대경로확인)
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        System.out.println("##############################3");
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("uploadPath : "  + uploadPath);
        System.out.println("##############################3");

        if(dirName.startsWith("../"))
            dirName = dirName.replace("../", "");

        System.out.println(dirName);
        System.out.println(uploadPath);

        registry
                .addResourceHandler("/"+dirName+"/**")
                .addResourceLocations("file:/"+uploadPath + "/");

        //spring frame
        //특정폴더를 파일서버로 작동시킨다.
        //application.properties  에서  fileUploadPath에
        //지정된 폴더값을 읽어서 그 폴더의 권한을 파일서버로 만든다

    }


}
