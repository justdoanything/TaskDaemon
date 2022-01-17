TroubleShooting
===

### Write Dockerfile

- 💥FROM openjdk:8u201-jre-alpine3.9
  - 복사하는 파일 경로 등 이후에 Container 접근해서 관리하기 힘들 것 같아서 Ubuntu image로 변경 후 jdk 설치하는 방식으로 변경
  - vim 설치 명령어 추가
- 💥필수 파일들은 /usr/src/app 경로로 COPY 할 때 에러 발생
  - 첫 명령어를 ADD 로 변경
- 💥Container를 -d run 해도 자동으로 stop 되는 현상
  - `CMD ["tail", "-f"]` 명령어를 추가해서 해결
  - 더 좋은 방법이 있는지 더 확인해야함
- 💥`sh run.sh start` 명령어 실행 시 오류 발생
  - not found 에러 
    - windows와 unix의 줄바꿈 차이 때문에 발생
    - Ubuntu 환경에서 `vi -b` 로 파일을 열어보면 "^M" 문자열 확인
    - 디버깅을 위해서 Container 실행 및 /bin/bash로 접근 후 `cat run.sh | tr -d '\r' > new_run.sh` 명령어로 "^M" 제거
    - 참고자료 : https://seokr.tistory.com/670
  - `run.sh` 실행 불가
    - 파일 내 오타가 있어서 수정
    - 계속 shell 명령어 오류 발생으로 운영 프로그램에서 사용하던 shell 복사 후 실행
  - `run.sh` 실행 시 "no main manifest attribute" 에러
    - pom.xml에 `<mainClass>` 추가
    - ClassNotFound Exception 발생
      - https://github.com/justdoanything/YONGYVER 프로젝트의 troubleShooting에서 처리했던 내용으로 `with-dependencies` 생성하고 Dockerfile 수정
### <span style="color:red">실행 성공 !</span>
  - dependency를 포함하면 .jar의 파일크기가 좀 커지기 때문에 docker 환경에서 .jar를 실행할 때 maven 종속을 읽어서 필요한 외부 .jar 파일을 다운받는 방법이 있는지 찾아봐야함.

  - Container 실행할 때 `CMD ["tail", "-f"]` 명령어 없이 지속적으로 실행시켜놓는 방법이 있는지 확인해야봐야함.
    - ENTRYPOINT와 CMD 명령어릐 차이점을 찾고 Container가 수행됐을 때 실행되어야 할 명령어는 `entrypoint.sh` 파일로 이동
        ```shell
        sed -i -e 's/\r//g' /usr/src/app/run.sh
        sh /usr/src/app/run.sh start

        tail -f
        ```

  - "^M"을 제거할 수 있는 방법이 있는지 확인해봐야함.
    (범용성을 위해서 Dockerfile에 관련 명렁어 추가)
    ```sh
    # 기존
    cat run.sh | tr -d '\r' > new_run.sh
    cp new_run.sh run.sh
    rm -rf new_run.sh
    ```
    - Container 수행 시 실행되어야 할 명령어들을 `entrypoint.sh` 로 옮기고 "^M" 제거는 `sed` 명령어를 수행
    - Dockerfile에 아래 명령어 추가
    `RUN sed -i -e 's/\r//g' /usr/src/app/entrypoint.sh`
    - `entrypoint.sh` 파일에 `sed` 명령어로 변경
    ```sh
    # 변경 
    sed -i -e 's/\r//g' /usr/src/app/run.sh
    ```
    