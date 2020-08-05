import Vue from 'vue'
import Vuex from 'vuex'
// import getters from './getters'
// import actions from './actions'
// import mutations from './mutations'
import * as axios from 'axios';
import router from '../main';
import { base } from "@/components/common/BaseURL.vue"; // baseURL

Vue.use(Vuex)

export default new Vuex.Store({
    state:{ //state는 데이터 
        isLogin: false,
        isLoginError: false,
        message: "로그인해주세요.",
        token: "",
        email: "",
        nickname:"",
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
        }

    }, 

    actions:{ //actions는 비즈니스 로직 
        // actions에서 mutations에정의된 함수를 호출하기 위해서는 commit을 사용한다.
        // signinObj : 로그인 시 입력한 { 이메일, 패스워드 }
        login({state, commit}, signinObj){ // 로그인 시도!
            
            axios.post(base + '/account/signin',
                signinObj)
                .then(res=>{

                    //임시 비밀번호로 로그인 했으면 => 비밀번호 변경페이지로 이동 
                    if (res.data.data.temp==1 && res.data.status) { 
                        state.message = res.data.data.email;

                        state.email = state.message

                        state.nickname = res.data.data.nickname;
                        console.log(state.email);
                        console.log(state.nickname);
                        console.log("토큰: "+res.headers["jwt-auth-token"]);

                        state.token =  res.headers["jwt-auth-token"];
                        state.nickname = res.data.data.nickname;
                        alert("임시비밀번호 상태라 비밀번호 변경 페이지로 이동합니다.")
                        router.push("/passwordchange")
                        return
                    }
                    else if(res.data.status) { // 임시비밀번호 로그인 안했으면 
                        console.log(res.data.status)
                        console.log("얘 임시비밀번호로 로그인한애야?? "+res.data.data.temp)
                        console.log("임시비밀번호 로그인을 안했으면! ")
                        state.token =  res.headers["jwt-auth-token"];
                        state.message = res.data.data.email;
                        state.nickname = res.data.data.nickname;
                        console.log(state.message);
                        console.log(state.nickname);
                        console.log("토큰: "+state.token);
                   
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

                            console.log(res.data.status) //undefined ...? 

                              if(res.data.status){ //관심태그 설정한 놈 
                                console.log("관심태그 설정했는지 체크하고 돌아왔습니다! (설정했음) ")
                                router.push("/feed/main");

                              }else{ //안한놈 
                                console.log("관심태그 설정했는지 체크하고 돌아왔습니다! (설정안했음) ")
                                router.push("/select");
                              }
                            
                          })
                          .catch(e=>{
                            console.log("정보조회 실패")
                            
                          });

                    }else{
                        state.message = "로그인해주세요.";
                        alert("입력정보를 확인하세요.");
                    }
                })
                .catch(e=>{
                    console.log(e)
                    alert("store.js: 이메일과 비밀번호를 확인해 주세요");
                    state.email = "";
                    state.password=""
                    
                });


        }
    }
})