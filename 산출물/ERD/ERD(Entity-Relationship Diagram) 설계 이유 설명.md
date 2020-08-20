<h3>회원가입</h3>

**USER(사용자) - PROFILE(프로필)**

> **하나의 사용자는 하나의 프로필을 갖는다.**

1:1 대응관계

부모테이블(user)의 PK를 자식테이블(profile)의 PK로 사용하므로 Identifying Relationship

<br></br>

**USER(사용자) - FAV_TAG(사용자 관심태그)**

> **하나의 사용자는 하나 이상의 사용자 관심태그를 갖는다.**

1:N 대응관계

부모테이블(user)의 PK를 자식테이블(fav_tag)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**TAG(태그) - FAV_TAG(사용자 관심태그)**

> **하나 이상의 태그가 하나 이상의 사용자 관심태그에 속한다.**
>
> 하나의 사용자 관심태그는 하나의 태그에 대응된다.

M:N 대응관계 (1:1 대응관계?)

부모테이블(tag)의 PK를 자식테이블(fav_tag)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>게시글 작성</h3>

**USER(사용자) - ARTICLE(게시글)**

> **하나의 사용자는 0 또는 하나 이상의 게시글을 작성한다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(article)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**ARTICLE(게시글) - ARTICLE_TAG(게시글 태그)**

> **하나의 게시글은 하나 이상의 게시글 태그를 갖는다.**

1:N 대응관계

부모테이블(article)의 PK를 자식테이블(article_tag)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**TAG(태그) - ARTICLE_TAG(게시글 태그)**

> **하나 이상의 태그가 하나 이상의 게시글 태그에 속한다.**
>
> 하나의 게시글 태그는 하나의 태그에 대응된다.

M:N 대응관계 (1:1 대응관계?)

부모테이블(tag)의 PK를 자식테이블(article_tag)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>스크랩</h3>

**USER(사용자) - SCRAP(스크랩)**

> **하나의 사용자는 0 또는 하나 이상의 게시글을 스크랩한다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(scrap)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**ARTICLE(게시글) - SCRAP(스크랩)**

> **하나의 게시글에 대해서 한 번 이하로 스크랩한다.**

1:1 대응관계 (0포함)

부모테이블(article)의 PK를 자식테이블(scrap)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>좋아요</h3>

**USER(사용자) - LIKE(좋아요)**

> **하나의 사용자는 0 또는 하나 이상의 게시글에 좋아요를 누른다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(like)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**ARTICLE(게시글) - LIKE(좋아요)**

> **하나의 게시글에 대해서 한 번 이하로 좋아요를 누른다.**

1:1 대응관계 (0포함)

부모테이블(article)의 PK를 자식테이블(like)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>댓글 작성</h3>

**USER(사용자) - COMMENT(댓글)**

> **하나의 사용자는 0 또는 하나 이상의 댓글을 작성한다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(comment)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**ARTICLE(게시글) - COMMENT(댓글)**

> **하나의 게시글에 0 또는 하나 이상의 댓글이 달린다.**

1:N 대응관계 (0포함)

부모테이블(article)의 PK를 자식테이블(comment)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>알림</h3>

**USER(사용자) - NOTICE(알림)**

> **하나의 사용자는 0 또는 하나 이상의 알림을 받는다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(notice)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

<h3>팔로우 기능</h3>

**USER(사용자) - FOLLOWING(팔로잉)**

> **하나의 사용자는 0 또는 하나 이상의 다른 사용자를 팔로우한다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(following)의 PK로 사용하지 않으므로 Non-Identifying Relationship

<br></br>

**USER(사용자) - FOLLOWER(팔로워)**

> **하나의 사용자는 0 또는 하나 이상의 팔로워를 갖는다.**

1:N 대응관계 (0포함)

부모테이블(user)의 PK를 자식테이블(follower)의 PK로 사용하지 않으므로 Non-Identifying Relationship