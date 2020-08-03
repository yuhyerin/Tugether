# git flow 사용법

git flow를 사용하겠다는 선언을 한다.

``` bash
git flow init
```

이렇게 하면 자동으로 local 브랜치가 develop으로 바뀐다.



우리 원격리포지토리에 develop 브랜치가 없으면 생성하고 연결한다.

```bash
git push origin develop # 리포지토리에 develop브랜치 생성

git branch --set-upstream-to=origin/develop develop #upstream 생성
```



로컬에서 작업할 때, feature/기능명 으로 브랜치를 만들어 작업한다.

```bash
git flow feature start login #나 이제 login기능 구현할 꺼다

git status
# On branch feature/login feature/login 브랜치로 바뀐것을 알 수 있음.
```



만약 login 작업을 공동으로 진행한다면, publish 해줘야 한다.

```bash
git flow feature publish login

```

이렇게 하면, 원격리포지토리에 feature/login 이라는 브랜치가 생기고 공동작업자는 이 브랜치를 pull 해서 작업하면 된다.

```bash
# 같이 login기능 구현하는 사람이 login 땡겨와서 작업하기!!!
git flow feature pull origin login
```



작업이 끝나면 feature finish 명령으로 develop에 반영한다. ( 반영하고, 로컬의 브랜치를 자동으로 삭제해준다. )

```bash
git flow feature finish login

git branch
# feature/login 사라짐. 자동으로 develop브랜치로 checkout 된다.
```



로컬 develop브랜치에서 commit, push 해서 업로드한다.

```bash
git add .
git commit -m "create login.txt "
git push
```



develop의 마지막작업자는 푸시 후, lab.ssafy.com에서 merge request 요청한다.



# The End . . .



