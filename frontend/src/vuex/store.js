import Vue from 'vue'
import Vuex from 'vuex'
// import getters from './getters'
// import actions from './actions'
// import mutations from './mutations'
import * as axios from 'axios';
import router from '../main';
import { base } from "@/components/common/BaseURL.vue"; // baseURL
import { LoaderPlugin } from 'vue-google-login';

Vue.use(LoaderPlugin,{
    client_id: "963926899908-ncql9skkc6bkmifvg9bhc9jv2asecrcd.apps.googleusercontent.com"
})
Vue.use(Vuex)

export default new Vuex.Store({
    state:{ //state는 데이터 
        isLogin: false,
        isLoginError: false,
        message: "로그인해주세요.",
        token: "",
        email: "",
        nickname: "",
        userEmail: "",
    },
    mutations:{ //mutations는 state를 변경시키는 로직만을 작성하는게 원칙
        // 로그인 성공 시
        loginSuccess(state){
            state.isLogin = true;
            state.isLoginError = false;
        },

        //로그인 실패 시
        loginError(state){
            state.isLogin = false;
            state.isLoginError = true;
        },

        // 토큰값 얻어오기 
        getToken(state){
            return state.token
        },

        logout(state){
            state.isLogin = false;
        },

        getEmail(state) {
            return state.email
        },

        getUserEmail(state) {
            return state.userEmail
        }

    }, 
    actions:{ //actions는 비즈니스 로직 
        
        googlelogin({state, commit},googleUser) {
           
            Vue.GoogleAuth.then(auth2=>{
                // console.log(auth2.isSignedIn.get());
                let googletoken = JSON.stringify(auth2.currentUser.get().getAuthResponse(true).id_token);
                axios.post(base+'/account/googlesignin',
                    {"googletoken": googletoken}
                ).then(res=>{
                    localStorage.setItem("googletoken", googletoken);
                    localStorage.setItem("token", res.headers["jwt-auth-token"])
                    localStorage.setItem("email", res.data.data.email)
                    state.nickname = res.data.data.nickname;
                    // console.log("구글로 로그인하였습니다.");

                    //관심태그를 설정한 회원인지 체크한다. 
                    axios.get(
                        base + '/tugether/checkfavtag',
                        {
                            headers:{
                                "jwt-auth-token": localStorage.getItem("token")
                            }
                        }
                    )
                    .then(res=>{
                        if(res.data.status){ //관심태그 설정한 놈 
                            // console.log("관심태그 설정했는지 체크하고 돌아왔습니다! (설정했음) ")
                            router.push("/mainfeed");
                        }else{ //안한놈 
                            // console.log("관심태그 설정했는지 체크하고 돌아왔습니다! (설정안했음) ")
                            router.push("/select");
                        }
                    })
                    .catch(e=>{
                        // console.log("관심태그 선택여부 조회 실패 :( ")
                    });

                }).catch(e=>{
                    // alert("일반회원으로 등록된 사용자입니다 :) ");
                })
            })
           
        },
        googlelogout(){
            Vue.GoogleAuth.then(auth2=>{
                // console.log(auth2.isSignedIn.get());
                // console.log(auth2.currentUser.get());
                // console.log(JSON.stringify(auth2.currentUser.get().getAuthResponse()));
                // console.log(JSON.stringify(auth2.currentUser.get().getAuthResponse(true).id_token));
                auth2.signOut().then(function(){
                    // console.log("로그아웃 되었습니다!");
                });
                auth2.disconnect();
            })
        },
        
        // actions에서 mutations에정의된 함수를 호출하기 위해서는 commit을 사용한다.
        // signinObj : 로그인 시 입력한 { 이메일, 패스워드 }

        login({state, commit}, signinObj){ // 로그인 시도!
            
            axios.post(base + '/account/signin',
                signinObj)
                .then(res=>{
                    
                        // console.log("구글회원유무 ");
                        // console.log(res.data.isgoogle);
                        if(res.data.isgoogle){
                            alert("구글로그인 연동 회원입니다 :) ");
                            return;
                        }
                        localStorage.setItem("token", res.headers["jwt-auth-token"])
                        localStorage.setItem("email", res.data.data.email)
                        //임시 비밀번호로 로그인 했으면 => 비밀번호 변경페이지로 이동 
                        if (res.data.data.temp==1 && res.data.status) { 
                            state.message = res.data.data.email;

                            state.email = state.message

                            state.nickname = res.data.data.nickname;
                            // console.log(state.email);
                            // console.log(state.nickname);
                            // console.log("토큰: "+res.headers["jwt-auth-token"]);

                            state.token =  res.headers["jwt-auth-token"];
                            state.nickname = res.data.data.nickname;
                            alert("임시비밀번호로 설정되어있어 비밀번호 변경 페이지로 이동합니다.")
                            router.push("/passwordchange")
                            return
                        }
                        else if(res.data.status) { // 임시비밀번호 로그인 안했으면 
                            // console.log(res.data.status)
                            state.token = res.headers["jwt-auth-token"];
                            state.email = res.data.data.email;
                            state.nickname = res.data.data.nickname;
                    
                            commit("loginSuccess") //Actions에서는 mutations의 함수를 호출하여 state값을 바꾼다.
                            alert("로그인 성공! 환영합니다 :)");

                            //관심태그를 설정한 회원인지 체크한다. 
                            axios.get(
                                base + '/tugether/checkfavtag',
                                {
                                headers:{
                                    "jwt-auth-token": res.headers["jwt-auth-token"]
                                }
                                }
                            )
                            .then(res=>{

                                // console.log(res.data.status) //undefined ...? 

                                if(res.data.status){ //관심태그 설정한 놈 
                                    router.push("/mainfeed");

                                }else{ //안한놈 
                                    router.push("/select");
                                }
                                
                            })
                            .catch(e=>{
                                // console.log("관심태그를 설정한 회원인지 조회를 실패하였습니다.")
                                
                            });

                        }else{
                            state.message = "로그인해주세요.";
                            alert("입력정보를 확인하세요.");
                        }
                    
                })
                .catch(e=>{
                    console.log(e)
                    alert("이메일과 비밀번호를 확인해 주세요 :)");
                    state.email = "";
                    state.password=""
                    
                }
            );


        }
    }
})