
import PasswordChange from './views/user/PasswordChange.vue'

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import TagView from './views/user/TagView.vue'
import PasswordFind from './views/user/PasswordFind.vue'
import ArticleWrite from './views/user/Write.vue'
import MainFeed from './views/feed/MainFeed.vue'
import Mypage from '@/views/mypage/Mypage.vue'
import MypageSetting from '@/views/mypage/MypageSetting.vue'
import MypagePasswordConfirm from '@/views/mypage/MypagePasswordConfirm.vue'
import MypagePasswordChange from '@/views/mypage/MypagePasswordChange.vue'
import Userpage from '@/views/mypage/Userpage.vue'
import Follow from '@/views/mypage/Follow.vue'
import Comment from '@/views/user/Comment.vue'
import Update from '@/views/user/Update.vue'
import Notice from '@/views/notice/Notice.vue'
import Components from './views/Components.vue'
import store from './vuex/store'
import Stats from '@/views/user/Stats.vue'
import Search from '@/views/search/Search.vue'

// 라우터가드1) 인증받은 유저는 reject 하겠다! 
const rejectAuthUser = (to, from, next)=>{
    if( localStorage.getItem("token") !=null ){ // 이미 로그인 된 유저이므로 로그인페이지로는 막아야한다.
        
        alert('이미 로그인을 하였습니다.')
        next("/mainfeed") //돌릴 경로 입력 

    }else{ // 로그인 되지 않았으면 
        next() //원래 보내려고 했던곳으로 그냥 가라~
    }
}

// 라우터가드2) 오직 인증받은 유저만 들어갈 수 있게 하겠다!
const onlyAuthUser = (to,from,next)=>{
    if(localStorage.getItem("token")){ // 이미 로그인 된 유저이므로 가던길 가게 해준다.
        
        next() //원래 보내려고 했던곳으로 그냥 가라~

    }else{ // 로그인 되지 않았으면 
        alert("로그인을 해주세요 ")
        next("/") //돌릴 경로 입력 
    }
}

export default [
    {
        path : '/',
        name : 'Login',
        beforeEnter: rejectAuthUser, //beforeEnter : 라우터 이동시키기 전에 이 함수를 실행시켜 보고 보낸다.
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        beforeEnter: rejectAuthUser,
        component : Join
    },
    {
        path : '/passwordfind',
        name : 'PasswordFind',
        beforeEnter: rejectAuthUser,
        component : PasswordFind
    },
    {
        path : '/passwordchange',
        name : 'PasswordChange',
        component : PasswordChange
    },
    {
        path : '/select',
        name : 'TagView',
        beforeEnter: onlyAuthUser,
        component : TagView
    },
    {
        path : '/mainfeed',
        name : 'MainFeed',
        beforeEnter: onlyAuthUser,
        component : MainFeed
    },
    {
        path : '/mypage',
        name : 'Mypage',
        beforeEnter: onlyAuthUser,
        component : Mypage
    },
    {
        path : '/mypage/mypagesetting',
        name : 'MypageSetting',
        beforeEnter: onlyAuthUser,
        component : MypageSetting
    },
    {
        path : '/mypage/mypagepasswordconfirm',
        name : 'MypagePasswordConfirm',
        beforeEnter: onlyAuthUser,
        component : MypagePasswordConfirm
    },
    {
        path : '/mypage/mypagepasswordchange',
        name : 'MypagePasswordChange',
        beforeEnter: onlyAuthUser,
        component : MypagePasswordChange
    },
    {
        path : '/userpage',
        name : 'Userpage',
        beforeEnter: onlyAuthUser,
        component : Userpage
    },
    {
        path : '/mypage/follow',
        name : 'Follow',
        beforeEnter: onlyAuthUser,
        component : Follow      
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        // path : '/write/:article_id',
        path : '/write',
        name: 'ArticleWrite',
        beforeEnter: onlyAuthUser,
        component: ArticleWrite
    },
    {
        path : '/update/:article_id',
        name: 'Update',
        beforeEnter: onlyAuthUser,
        component: Update
    },
    {
        path : '/notice',
        name: 'Notice',
        beforeEnter: onlyAuthUser,
        component: Notice
    },
    {
        path : '/comment/:article_id',
        name : 'Comment',
        component : Comment
    },
    {
        path : '/stats',
        name : 'Stats',
        component : Stats
    },
    {
        path : '/search',
        name : 'Search',
        component : Search
    }
]
