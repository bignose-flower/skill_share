FROM eclipse-temurin:17.0.9_9-jdk

ENV APP_ROOT /app
ENV TZ Asia/Tokyo

COPY ./spring_project/ $APP_ROOT
WORKDIR $APP_ROOT
# コンテナ起動時にSpringBootプロジェクトを起動するので、実行権限を付与
RUN chmod +x $APP_ROOT/mvnw