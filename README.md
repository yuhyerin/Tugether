## Project  - 튜게더 (Tugether = YouTube + Together )

:heart: Project Name - Tugether :blush: ( YouTube + Together의 합성어 )

:yellow_heart: Project Subject - 유튜브 영상 큐레이션 및 태그기반 유튜브 영상 공유 SNS :envelope:

:green_heart: Team Name - 만두 :tongue:
팀원들끼리 똘똘 뭉치자는 의미에서 만두로 정했습니다 😚

:blue_heart: Team Member & Role 👪 <br/>
팀장 😚	유혜린

팀원 😏	권민지, 정현희, 고소영, 최규식	😏

역할

기획 - 고소영

프론트 개발 - 권민지, 고소영, 최규식

백엔드 개발 - 유혜린, 정현희

QA - 정현희

Tech Leader - 유혜린

:heartpulse: 서비스 소개 <br/>
기존에 존재하지 않았던 유튜브 구독자들을 위한 커뮤니티 SNS 서비스 입니다! <br/>
관심태그를 설정하여 공통의 관심분야를 가진 사람들끼리 유튜브 영상을 공유하고 추천받을 수 있습니다! <br/>
SNS의 팔로우기능을 활용하여 비슷한 취향의 방송을 보는 사람들과 소통할 수 있습니다! <br/>



:purple_heart: 개발규칙💻

### Git 전략 :star:

Git-flow에는 5가지 종류의 브랜치가 존재합니다. 
항상 유지되는 메인 브랜치들(master, develop)과 일정 기간 동안만 유지되는 보조 브랜치들(feature, release, hotfix)이 있습니다.

master : 제품으로 출시될 수 있는 브랜치
develop : 다음 출시 버전을 개발하는 브랜치
feature : 기능을 개발하는 브랜치
release : 이번 출시 버전을 준비하는 브랜치
hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치



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



> 같은기능을 구현하는 프론트, 백엔드 개발자간 코드를 머지한 후  develop에 push 한다.


