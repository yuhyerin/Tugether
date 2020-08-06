
import PasswordChange from './views/user/PasswordChange.vue'

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Menu from './views/menu/Menu.vue'
import TagView from './views/user/TagView.vue'
import PasswordFind from './views/user/PasswordFind.vue'
// import FeedMain from './views/feed/IndexFeed.vue'
import MainFeed from '@/views/feed/MainFeed.vue'
import Mypage from '@/views/mypage/Mypage.vue'
import MypageSetting from '@/views/mypage/MypageSetting.vue'
import MypagePasswordConfirm from '@/views/mypage/MypagePasswordConfirm.vue'
import MypagePasswordChange from '@/views/mypage/MypagePasswordChange.vue'
import Userpage from '@/views/mypage/Userpage.vue'
import Follow from '@/views/mypage/Follow.vue'

import Components from './views/Components.vue'
import store from './vuex/store'

// 라우터가드1) 인증받은 유저는 reject 하겠다! 
const rejectAuthUser = (to, from, next)=>{
    if(store.state.isLogin === true){ // 이미 로그인 된 유저이므로 로그인페이지로는 막아야한다.
        
        alert('이미 로그인을 하였습니다.')
        next("/feed/main") //돌릴 경로 입력 

    }else{ // 로그인 되지 않았으면 
        next() //원래 보내려고 했던곳으로 그냥 가라~
    }
}

// 라우터가드2) 오직 인증받은 유저만 들어갈 수 있게 하겠다!
const onlyAuthUser = (to,from,next)=>{
    if(store.state.isLogin === true){ // 이미 로그인 된 유저이므로 로그인페이지로는 막아야한다.
        
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
        component : TagView
    },
    // {
    //     path : '/feed/main',
    //     name : 'FeedMain',
    //     // beforeEnter: onlyAuthUser,
    //     component : FeedMain
    // },
    {
        path : '/mainfeed',
        name : 'MainFeed',
        component : MainFeed
    },
    {
        path : '/mypage/mypage',
        name : 'Mypage',
        component : Mypage
    },
    {
        path : '/mypage/mypagesetting',
        name : 'MypageSetting',
        component : MypageSetting
    },
    {
        path : '/mypage/mypagepasswordconfirm',
        name : 'MypagePasswordConfirm',
        component : MypagePasswordConfirm
    },
    {
        path : '/mypage/mypagepasswordchange',
        name : 'MypagePasswordChange',
        component : MypagePasswordChange
    },
    {
        path : '/mypage/userpage',
        name : 'Userpage',
        component : Userpage
    },
    {
        path : '/mypage/follow',
        name : 'Follow',
        component : Follow      
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/menu',
        name: 'Menu',
        component: Menu
    },
]
