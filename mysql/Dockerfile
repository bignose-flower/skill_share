FROM mysql:8.0-debian

RUN apt-get update && apt-get install -y locales
RUN sed -i -e 's/# \(ja_JP.UTF-8\)/\1/' /etc/locale.gen
RUN locale-gen

ENV LC_ALL ja_JP.UTF-8

ENV TZ Asia/Tokyo
ENV LANG ja_JP.UTF-8