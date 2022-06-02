
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf /usr/share/nginx/html/*
COPY /target/gatling/ /usr/share/nginx/html/
 
EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]
