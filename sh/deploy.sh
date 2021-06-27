#!/bin/bash

# 프로젝트 경로 : REPOSITORY/PROJECT_NAME
REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=freelec-springboot2-webservice

# 프로젝트 루트로 이동해서
cd $REPOSITORY/$PROJECT_NAME/

# 풀 함 땡기고
echo "> Git Pull"
git pull

# gradlew 통해 빌드해주고
echo "> 프로젝트 빌드 시작"
./gradlew build

# 상위로 이동해서
echo "> step1 디렉토리로 이동"
cd $REPOSITORY

# 아까 빌드한 파일을 여기에 복사한담에
echo "> 빌드파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

# 빌드파일이 현재 돌고있다면 pid를 확인
echo"> 현재 구동중인 앱 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동중인 앱 pid : $CURRENT_PID"

# 쉘스크립트 if문 : 띄어쓰기를 항상 조심하자.
# 문법은 https://jink1982.tistory.com/48 참조.
# 아래는 $CURRENT_PID의 문자열 길이가 0이면 암것도 안하고
# 1 이상이면 현재 프로세스 종료 후 5초 sleep을 걸어준다.
if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 앱 없으므로, 종료 X"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

# 가장 최신의 jar 파일을 잡아
echo "> 새 앱 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR 이름 : $JAR_NAME"

# 백그라운드에서 실행시킨다.
# nohup java -jar $REPOSITORY/$JAR_NAME 2>&1

# + 스프링 설정파일 위치를 application.properties와 application-oauth.properties로 하겠다.
# nohup java -jar -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties $REPOSITORY/$JAR_NAME 2>&1 &

# + 거기에 properties 두 개 더 추가하겠다.
nohup java -jar -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties -Dspring.profiles.active=real $REPOSITORY/$JAR_NAME 2>&1 &