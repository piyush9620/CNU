FROM gradle:alpine
ADD . /milestone1
WORKDIR /milestone1
USER root
ENV INPUT_LIST_PATH=/var/data/input/jobs.json
ENV OUTPUT_IMAGE_PATH=/var/data/output/
RUN gradle build
CMD gradle run