# Criação da imagem docker
FROM tomcat:11.0-jdk1-temurin

# Remoção dos arquivos WEBAPPS do tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# copia dos arquivos do localhost para a imagem docker
COPY target/app.war /usr/local/tomcat/webapps/ROOT.war

# abrir a porta 8080
EXPOSE 8080 

CMD ["catalina.sh", "run"]