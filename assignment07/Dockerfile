FROM alpine:3.7

USER root

RUN apk update &&\
apk add python3 python3-dev mariadb-dev build-base &&\
pip3 install mysqlclient &&\
apk del python3-dev mariadb-dev build-base &&\
apk add mariadb-client-libs
RUN pip3 install awscli
RUN apk add nginx && adduser -D -g 'www' www && mkdir /www && chown -R www:www /var/lib/nginx && chown -R www:www /www && mkdir -p /run/nginx

ADD . /assignment07

WORKDIR /assignment07

COPY nginx.conf /etc/nginx/nginx.conf

RUN pip3 install -r requirements.txt

CMD ["sh", "start.sh"]
