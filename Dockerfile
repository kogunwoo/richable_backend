# Tomcat 이미지를 기반으로 설정
FROM tomcat:9.0.94-jdk17-temurin

# 컨테이너 내부에서 작업 디렉토리 설정
WORKDIR /usr/local/tomcat/webapps

# 빌드된 WAR 파일을 컨테이너에 복사
COPY build/libs/kb_i_dle_backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

# server.xml의 8080 포트를 80으로 변경
RUN sed -i 's/port="8080"/port="80"/' /usr/local/tomcat/conf/server.xml

# 애플리케이션 포트 노출
EXPOSE 80

# Tomcat 실행 명령
CMD ["catalina.sh", "run"]
