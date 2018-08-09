FROM gradle:alpine
ADD . /assignment02
WORKDIR /assignment02
USER root
ENV INPUT_LIST_PATH=/var/data/input/jobs.json
ENV OUTPUT_IMAGE_PATH=/var/data/output/
RUN gradle build
CMD gradle run