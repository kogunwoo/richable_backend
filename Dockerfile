# Tomcat 이미지를 기반으로 설정
FROM tomcat:9.0.94-jdk17-temurin

# 컨테이너 내부에서 작업 디렉토리 설정
WORKDIR /usr/local/tomcat/webapps

# 빌드된 WAR 파일을 컨테이너에 복사
COPY build/libs/your-app-name.war /usr/local/tomcat/webapps/app.war

# 애플리케이션 포트 노출
EXPOSE 8080

# Tomcat 실행 명령
CMD ["catalina.sh", "run"]
