TroubleShooting
===

### Write Dockerfile

- ๐ฅFROM openjdk:8u201-jre-alpine3.9
  - ๋ณต์ฌํ๋ ํ์ผ ๊ฒฝ๋ก ๋ฑ ์ดํ์ Container ์ ๊ทผํด์ ๊ด๋ฆฌํ๊ธฐ ํ๋ค ๊ฒ ๊ฐ์์ Ubuntu image๋ก ๋ณ๊ฒฝ ํ jdk ์ค์นํ๋ ๋ฐฉ์์ผ๋ก ๋ณ๊ฒฝ
  - vim ์ค์น ๋ช๋ น์ด ์ถ๊ฐ
- ๐ฅํ์ ํ์ผ๋ค์ /usr/src/app ๊ฒฝ๋ก๋ก COPY ํ  ๋ ์๋ฌ ๋ฐ์
  - ์ฒซ ๋ช๋ น์ด๋ฅผ ADD ๋ก ๋ณ๊ฒฝ
- ๐ฅContainer๋ฅผ -d run ํด๋ ์๋์ผ๋ก stop ๋๋ ํ์
  - `CMD ["tail", "-f"]` ๋ช๋ น์ด๋ฅผ ์ถ๊ฐํด์ ํด๊ฒฐ
  - ๋ ์ข์ ๋ฐฉ๋ฒ์ด ์๋์ง ๋ ํ์ธํด์ผํจ
- ๐ฅ`sh run.sh start` ๋ช๋ น์ด ์คํ ์ ์ค๋ฅ ๋ฐ์
  - not found ์๋ฌ 
    - windows์ unix์ ์ค๋ฐ๊ฟ ์ฐจ์ด ๋๋ฌธ์ ๋ฐ์
    - Ubuntu ํ๊ฒฝ์์ `vi -b` ๋ก ํ์ผ์ ์ด์ด๋ณด๋ฉด "^M" ๋ฌธ์์ด ํ์ธ
    - ๋๋ฒ๊น์ ์ํด์ Container ์คํ ๋ฐ /bin/bash๋ก ์ ๊ทผ ํ `cat run.sh | tr -d '\r' > new_run.sh` ๋ช๋ น์ด๋ก "^M" ์ ๊ฑฐ
    - ์ฐธ๊ณ ์๋ฃ : https://seokr.tistory.com/670
  - `run.sh` ์คํ ๋ถ๊ฐ
    - ํ์ผ ๋ด ์คํ๊ฐ ์์ด์ ์์ 
    - ๊ณ์ shell ๋ช๋ น์ด ์ค๋ฅ ๋ฐ์์ผ๋ก ์ด์ ํ๋ก๊ทธ๋จ์์ ์ฌ์ฉํ๋ shell ๋ณต์ฌ ํ ์คํ
  - `run.sh` ์คํ ์ "no main manifest attribute" ์๋ฌ
    - pom.xml์ `<mainClass>` ์ถ๊ฐ
    - ClassNotFound Exception ๋ฐ์
      - https://github.com/justdoanything/YONGYVER ํ๋ก์ ํธ์ troubleShooting์์ ์ฒ๋ฆฌํ๋ ๋ด์ฉ์ผ๋ก `with-dependencies` ์์ฑํ๊ณ  Dockerfile ์์ 
### <span style="color:red">์คํ ์ฑ๊ณต !</span>
  - dependency๋ฅผ ํฌํจํ๋ฉด .jar์ ํ์ผํฌ๊ธฐ๊ฐ ์ข ์ปค์ง๊ธฐ ๋๋ฌธ์ docker ํ๊ฒฝ์์ .jar๋ฅผ ์คํํ  ๋ maven ์ข์์ ์ฝ์ด์ ํ์ํ ์ธ๋ถ .jar ํ์ผ์ ๋ค์ด๋ฐ๋ ๋ฐฉ๋ฒ์ด ์๋์ง ์ฐพ์๋ด์ผํจ.

  - Container ์คํํ  ๋ `CMD ["tail", "-f"]` ๋ช๋ น์ด ์์ด ์ง์์ ์ผ๋ก ์คํ์์ผ๋๋ ๋ฐฉ๋ฒ์ด ์๋์ง ํ์ธํด์ผ๋ด์ผํจ.
    - ENTRYPOINT์ CMD ๋ช๋ น์ด๋ฆ ์ฐจ์ด์ ์ ์ฐพ๊ณ  Container๊ฐ ์ํ๋์ ๋ ์คํ๋์ด์ผ ํ  ๋ช๋ น์ด๋ `entrypoint.sh` ํ์ผ๋ก ์ด๋
        ```shell
        sed -i -e 's/\r//g' /usr/src/app/run.sh
        sh /usr/src/app/run.sh start

        tail -f
        ```

  - "^M"์ ์ ๊ฑฐํ  ์ ์๋ ๋ฐฉ๋ฒ์ด ์๋์ง ํ์ธํด๋ด์ผํจ.
    (๋ฒ์ฉ์ฑ์ ์ํด์ Dockerfile์ ๊ด๋ จ ๋ช๋ ์ด ์ถ๊ฐ)
    ```sh
    # ๊ธฐ์กด
    cat run.sh | tr -d '\r' > new_run.sh
    cp new_run.sh run.sh
    rm -rf new_run.sh
    ```
    - Container ์ํ ์ ์คํ๋์ด์ผ ํ  ๋ช๋ น์ด๋ค์ `entrypoint.sh` ๋ก ์ฎ๊ธฐ๊ณ  "^M" ์ ๊ฑฐ๋ `sed` ๋ช๋ น์ด๋ฅผ ์ํ
    - Dockerfile์ ์๋ ๋ช๋ น์ด ์ถ๊ฐ
    `RUN sed -i -e 's/\r//g' /usr/src/app/entrypoint.sh`
    - `entrypoint.sh` ํ์ผ์ `sed` ๋ช๋ น์ด๋ก ๋ณ๊ฒฝ
    ```sh
    # ๋ณ๊ฒฝ 
    sed -i -e 's/\r//g' /usr/src/app/run.sh
    ```
    