## スキル共有アプリDocker開発環境構築
概要 : Dockerでのローカル開発環境の構築手順について記載

### フォルダ構成
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

### コンテナ起動
- docker-compose.ymlファイルがある箇所で下記のコマンドを実行
- ※現状root権限のみでしか実行できないので、wsl2側の権限はrootで実行してください。
''' docker-compose up '''

### nuxtの起動
- nuxtのコンテナにログインする
''' docker exec -it frontend /bin/bash '''
- コンテナ内でパッケージのインストールおよびサーバ起動
- yarn install
- yarn dev
### Web画面確認
- http://localhost:3001

### dbへの接続確認
- mysqlのコンテナにログインする
''' docker exec -it mysql /bin/bash '''
- テストデータ確認
- mysql -h localhost -u root -p
- USE rd_procurement
- SELECT * FROM sample;

### バックエンドへの接続確認
- backendのコンテナにログインする
''' docker exec -it backend /bin/bash '''
- 接続確認
- http://localhost:8081/hello
- http://localhost:8081/testdb #DBへ接続している