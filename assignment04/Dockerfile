FROM gradle
ADD . /assignment04
WORKDIR /assignment04
USER root
RUN curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py && python get-pip.py && pip install awscli
RUN aws s3 cp s3://cnu-2k18/arpit.application.properties src/main/resources/application.properties && gradle build
CMD gradle run