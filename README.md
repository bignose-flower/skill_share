## スキル共有アプリDocker開発環境構築
概要 : Dockerでのローカル開発環境の構築手順

事前に用意したnuxtプロジェクトとSpringBootプロジェクトは各サーバが連携できるような
簡単なアプリを作成している。

### 制約事項 
---
- 現状root権限のみでしか利用できない。
- IDEはVSCodeを想定。(他のエディタではWSLとの親和性がない。)

#### VSCodeで必要となるExtension
- Java
    - Extension Pack for Java
    - Spring Boot Extension Pack
- Vue
    - Vue Language Features
- mysql
    - MySQL
- Docker
    - Docker
    - hadolint(セキュリティ観点でのlinter)

### 目的 
---
- コンテナによる開発環境を作成することで環境開発負担を削減する
- 同じバージョンを利用することができる。
- 個人的な学習目的も含まれています。

### システム構成
フロント(vue + nuxt) ⇔ バックエンド(Java + SprintBoot) ⇔ DB(mysql)

### フォルダ構成
--- 
<pre>
.
├── README.md
├── delete.sh                      # imageとコンテナを一括で削除するスクリプト
├── docker-compose.yml             # docker-compose定義ファイル
├── log                            # mysqlのログ格納フォルダ
│   └── mysql
├── mysql
│   ├── Dockerfile                 # mysqlのイメージ定義ファイル
│   ├── conf.d                     # mysqlの設定フォルダ
│   └── docker-entrypoint-initdb.d # mysqlの初期データ投入用のフォルダ
├── spring
│   ├── Dockerfile                 # springbootのイメージ定義ファイル
│   └── spring_project             # springbootプロジェクトフォルダ
└── vue
    ├── Dockerfile                 # nuxtのイメージ定義ファイル
    └── vue_project                # nuxtのプロジェクトフォルダ
</pre>

### 1. git clone
--- 
> [!CAUTION]
> Windowsの場合は改行コードがLFからCRLFに自動変換されてしまうので、改行コードの変更を無効化する
> ``` linux
> $ git confiig --global core.autocrlf input
> ```
> 上記の設定によりコミット時はCRLF→LF変換。pull時は無変換となる。
``` linux
$ git clone https://github.com/bignose-flower/skill_share.git
```

### 1.コンテナ起動
---
- docker-compose.ymlファイルがある箇所で下記のコマンドを実行
```linux
$ docker-compose up
``` 
<details>
<summary>docker-compose.ymlについて</summary>
Docker Composeとは、複数のコンテナを効率的に操作するためのツールです。

Docker Composeを使う場合、「compose.yaml」という名前のファイルに、各コンテナに対する設定をあらかじめ定義しておきます。

その上で専用コマンドを実行することにより、定義した内容に従い複数コンテナを一括で同時に起動させることができるのです。
</details>

### 2.nuxtの起動
---
1. nuxtのコンテナにログインする
``` linux
$ docker exec -it frontend /bin/bash 
```
2. コンテナ内でパッケージのインストールおよびサーバ起動
``` linux
# frontendコンテナ内での操作
# パッケージインストール
$ yarn install
# サーバ起動
$ yarn dev
```
### 3.Webの起動と画面確認
---
- http://localhost:3001
- Fetch Data For HelloWorldはAPIから文字列を受け取るボタン
- Fetch Data For DBはAPI→DBにデータを受け取るボタン

### 4.DBへの接続確認
---
- mysqlのコンテナにログインする
``` linux 
$ docker exec -it mysql /bin/bash 
```
- テストデータ確認
``` linux
# mysqlコンテナ内での操作
$ mysql -h localhost -u root -p # この後にパスワードを入力
$ USE rd_procurement;
$ SELECT * FROM sample;

#初回起動時に下記データが登録されている。
mysql> select * from sample;
+----+---------+
| id | column1 |
+----+---------+
|  1 | Data 1  |
|  2 | Data 2  |
|  3 | Data 3  |
+----+---------+
3 rows in set (0.00 sec)
```

### 5.APIへの接続確認
---
- 接続確認
- http://localhost:8081/hello
![Alt text](/image/image-1.png)
- http://localhost:8081/testdb
![Alt text](/image/image-2.png)


### 変更1. DBコンテナの再ビルド(ロケールパッケージ追加対応)
---

1. docker-compose.ymlの編集
24行目の起動設定をコメントアウトにする
alwaysにすると、この後にコンテナを停止してもまた勝手に起動してしまう。
``` Linux
$ sed -i 's/restart: always/#restart: always/' ./docker-compose.yml
```

- DBコンテナのみを一旦削除して、再ビルドを行います。
2. mysqlコンテナの停止と削除
``` Linux
# skill_shareのルートディレクトリに移動
# 現在稼働中のコンテナをチェック
$ docker-compose ps

# dbコンテナの停止
$ docker-compose stop mysql

# mysqlコンテナが停止したかチェック
# STATUSがExitedになっていれば停止状態
$ docker-compose ps -a
NAME       IMAGE                  COMMAND                  SERVICE    CREATED             STATUS                      PORTS
backend    skill_share-backend    "/__cacert_entrypoin…"   backend    About an hour ago   Up 2 minutes                0.0.0.0:8081->8080/tcp
frontend   skill_share-frontend   "docker-entrypoint.s…"   frontend   About an hour ago   Up 2 minutes                0.0.0.0:3001->3000/tcp
mysql      skill_share-mysql      "docker-entrypoint.s…"   mysql      6 minutes ago       Exited (0) 50 seconds ago

# mysqlコンテナの削除
# 削除するか確認があるので、「y」を入力
$ docker-compose rm mysql

# コンテナの状態確認
$ docker-compose ps -a
NAME       IMAGE                  COMMAND                  SERVICE    CREATED             STATUS         PORTS
backend    skill_share-backend    "/__cacert_entrypoin…"   backend    About an hour ago   Up 4 minutes   0.0.0.0:8081->8080/tcp
frontend   skill_share-frontend   "docker-entrypoint.s…"   frontend   About an hour ago   Up 4 minutes   0.0.0.0:3001->3000/tcp
```

3. docker ボリュームの削除
```
# docker volumeの確認
$ docker volume ls
local     skill_share_mysql_db

# 削除
$ docker volume rm skill_share_mysql_db

# 確認
$ docker volume ls
```

4. mysqlのDockerfileの設定確認
    - 修正したDockerfileにてロケールパッケージのinstallするコードがあるか確認

5. mysqlイメージ及びコンテナの起動
```
# イメージの作成
# --no-cacheを指定することでcacheの利用を回避し、新規にimageを作成する
$ docker-compose build --no-cache mysql

# restart設定のコメントアウトを解除
$ sed -i 's/#restart: always/restart: always/' ./docker-compose.yml

# mysqlコンテナのみ起動を行う。
# -dでバックグラウンドとして起動する。
$ docker-compose up -d mysql

# 最終的なコンテナの稼働状況を確認
# mysqlコンテナの稼働を確認できる
$ docker-compose ps
backend    skill_share-backend    "/__cacert_entrypoin…"   backend    2 hours ago      Up 11 minutes   0.0.0.0:8081->8080/tcp
frontend   skill_share-frontend   "docker-entrypoint.s…"   frontend   2 hours ago      Up 11 minutes   0.0.0.0:3001->3000/tcp
mysql      skill_share-mysql      "docker-entrypoint.s…"   mysql      17 seconds ago   Up 15 seconds   33060/tcp, 0.0.0.0:3307->3306/tcp
```

6. 不要になったmysqlイメージを削除
```
# 不要imageの確認
# <none>となっているimageがあれば、それはmysqlの残骸となったimageなので削除する
$ docker images
...中略
<none>                          <none>    1c88e5a39bad   About an hour ago   637MB

# imageの削除
$ docker rmi <IMAGE ID>

# 確認
docker images
```