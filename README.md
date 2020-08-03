# Project  - SNS 튜게더

:heart: Project Name - Tugether :blush: ( YouTube + Together의 합성어 )

:yellow_heart: Team Name - 만두 :tongue:

:green_heart: Project Subject - 유튜버 팬들이 자신이 구독하는 유튜버의 영상클립이나 이미지를 공유하며 이용자들 간 소통하는 웹 큐레이션 SNS :envelope:



### Git 전략 :star:

> git flow를 활용하여 master브랜치에서 직접 작업하는 것을 막고, develop브랜치에서 모든 작업을 수행하도록 한다.

```shell
git flow init  # 자동으로 local브랜치가 develop으로 바뀐다.
git push origin develop # 리포지토리에 develop브랜치를 생성한다.
git branch --set-upstream-to=origin/develop develop #upstream 생성
```



> 특정 기능을 개발 하고자 할 때,

```shell
git flow feature start login # login 기능 구현
git branch #로 확인해보면 feature/login 브랜치로 바뀐것을 알 수 있다!
```



> login 기능 개발을 공동으로 진행한다면, publish 해준다.

```shell
git flow feature publish login
```

> 예시) 로그인 기능 프론트,백엔드 각자의 브랜치에서 개발한 코드를 백엔드 개발자가 자신의 브랜치로 합쳐서 테스트 하고 develop으로 올리기
 - feature/login_frontend ,  feature/login_backend

```shell
# 현재 위치 : feature/login_backend (로그인 기능 백엔드 개발자)
# pull 대신 track 명령으로 바뀌었습니다. 
# 현재 login_backend 브랜치에 login_frontend 개발한것을 가지고 온다.
# 이때 자동으로 login_frontend로 checkout 되게 됩니다.
git flow feature track feature/login_frontend  

# 그래서 feature/login_backend 로 이동한다.
git checkout feature/login_backend

# login_frontend 를 login_backend로 합친다.
git merge feature/login_frontend

# login_backend를 원격지로 올린다.
git push origin feature/login_backend

# develop 브랜치로 이동한다.
git checkout develop

# develop을 원격지로 올리기전에 fetch로 변경사항을 받아온다.
git fetch

# 원격지에 변경사항이 있다면 받아온다.
git pull origin develop

# 완성한 login기능코드 (login_backend)를 develop에 합친다.
git merge feature/login_backend

# 원격develop에 완성한 코드를 올린다.
git push origin develop

# 완료한 기능의 브랜치를 수동으로 삭제한다.
git branch -d feature/login_backend
git branch -d feature/login_frontendd

# 로그인 기능 끝.



