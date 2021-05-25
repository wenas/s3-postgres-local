# S3＆Postgreローカルテスト環境


### S3ダミー

```
docker run -p 9000:9000 --mount type=bind,src=$(pwd)/data,dst=/data minio/minio server /data 
```

パスワードは minioadmin:minioadmin

```bash
export AWS_ACCESS_KEY_ID=minioadmin
export AWS_SECRET_ACCESS_KEY=minioadmin
aws --endpoint-url http://127.0.0.1:9000 s3 ls
```

### Dockerビルド(Dockerファイル)

```bash
docker build -t <イメージ名> .
```


### Dockerビルド(jib)

jibでローカルにあるイメージからビルドする場合、イメージ名の指定にdocker://が必要。

```xml

<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>3.0.0</version>
    <configuration>
        <from>
            <image>docker://my-base-image:latest</image>
        </from>
        <to>app-runner-spring</to>
        <allowInsecureRegistries>true</allowInsecureRegistries>
    </configuration>
</plugin>
```
