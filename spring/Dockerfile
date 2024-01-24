# Javaの開発環境(テスト環境)と本番環境を作成する構成
FROM eclipse-temurin:17.0.9_9-jdk

# アプリケーションのコピー
ENV APP_ROOT /app

COPY ./spring_project/ $APP_ROOT
WORKDIR $APP_ROOT
RUN chmod +x $APP_ROOT/mvnw