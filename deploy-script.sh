#!/bin/bash
REPOSITORY=~/app/was
GITHUB_REPO=block-file-extensions
PROJECT_NAME=flow
TODAY=$(date "+%Y%m%d")

cd $REPOSITORY/$GITHUB_REPO

echo "> GIT PULL"

git pull origin main

echo "> 테스트 없이 Gradle 빌드 시작"

chmod +x ./gradlew
./gradlew build -x check --parallel

echo "> WAS 경로로 이동"

cd $REPOSITORY

echo "> 구동 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "> 구동 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 기존 jar 백업"

OLD_JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

mv -i $OLD_JAR_NAME backup/$TODAY$OLD_JAR_NAME

echo "> Build 파일 복사"

cp $REPOSITORY/$GITHUB_REPO/build/libs/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR NAME : $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &