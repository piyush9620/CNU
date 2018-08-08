FROM gradle:alpine
ADD . /milestone1
WORKDIR /milestone1
USER root
RUN gradle build
CMD gradle run