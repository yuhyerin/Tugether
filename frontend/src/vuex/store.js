import Vue from 'vue'
import Vuex from 'vuex'
// import getters from './getters'
// import actions from './actions'
// import mutations from './mutations'
import * as axios from 'axios';
import router from '../main';

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

        //로그아웃 
        logout(state){
            state.isLogin=false;
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
            
            axios.post("http://localhost:8080/account/signin",
                signinObj)
                .then(res=>{

                    //임시 비밀번호로 로그인 했으면 => 비밀번호 변경페이지로 이동 
                    if (res.data.data.temp==1 && res.data.status) { 
                        
                        commit("loginSuccess")
                        localStorage.setItem("token", res.headers["jwt-auth-token"]);
                        console.log("로컬스토리지에 토큰 저장하였습니다.")
                        state.email = res.data.data.email;
                        state.nickname = res.data.data.nickname;
                        state.token =  res.headers["jwt-auth-token"];
                        alert("임시비밀번호 상태라 비밀번호 변경 페이지로 이동합니다.")
                        router.push("/passwordchange")
                        return
                    }
                    else if(res.data.status) { // 임시비밀번호 로그인 안했으면 
                        
                        commit("loginSuccess") //Actions에서는 mutations의 함수를 호출하여 state값을 바꾼다.
                        localStorage.setItem("token", res.headers["jwt-auth-token"]);
                        console.log("로컬스토리지에 토큰 저장하였습니다.")
                        state.email = res.data.data.email;
                        state.nickname = res.data.data.nickname;
                        state.token =  res.headers["jwt-auth-token"];
                        alert("로그인 성공! 환영합니다 :)");

                        //관심태그를 설정한 회원인지 체크한다. 
                        axios.get(
                            "http://localhost:8080/tugether/checkfavtag",
                            {
                              headers:{
                                "jwt-auth-token": res.headers["jwt-auth-token"]
                              }
                            }
                          )
                          .then(res=>{

                                console.log(res.data.status);
                              if(res.data.status){ //관심태그 설정한 놈 
                                console.log("관심태그를 설정한 회원입니다.")
                                router.push("/feed/main");

                              }else{ //안한놈 
                                console.log("관심태그를 설정하지 않은 회원입니다.")
                                router.push("/select");
                              }
                            
                          })
                          .catch(e=>{
                            console.log("정보조회 실패")
                            
                          });

                    }else{
                        alert("이메일과 비밀번호를 확인해 주세요");
                    }
                })
                .catch(e=>{
                    console.log(e)
                    alert("이메일과 비밀번호를 확인해 주세요");
                    state.email = "";
                    state.password= "";
                    
                });


        }
    }
})