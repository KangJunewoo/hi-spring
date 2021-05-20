# hi-spring
스프링 뿌셔

## 3~6장 프로젝트
* CRUD 가능한 게시판
* 소셜로그인 가능
* 로그인 여부 / 글 작성 여부에 따른 서로 다른 권한 부여

## 추후 복습할 내용
* 어노테이션이 무지하게 많이 나온다. 롬복부터 springboot, jpa 관련까지 꼭 정리하고 넘어가야겠다.

## shell script
```shell
REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=hi-spring

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build tlwkr"

./gradlew build

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```