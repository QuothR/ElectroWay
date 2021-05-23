import { LoginActionType } from '../actions/LoginAction'
import axios from 'axios'
import jwt_decode from "jwt-decode";

const loginState = {
    isLogged: false,
    user: {
        token: "",
    },
};

const getLoginState = () => {
    const loginState = sessionStorage.getItem("loginState") ? sessionStorage.getItem("loginState") : {
        isLogged: false, user: {
            token: "",
        },
    };
    const userState = sessionStorage.getItem("userState") ? sessionStorage.getItem("userState") : {
        email: "",
        password: "",
    };
    try {
        const loginStateObj = JSON.parse(loginState);
        const userStateObj = JSON.parse(userState)
        const { token } = loginStateObj.user;

        if (token) {
            const tokenObj = jwt_decode(token);
            if (Math.floor(new Date().getTime() / 1000) > new Date(tokenObj.exp).getTime() - 125) {
                console.log("token nou");

                axios.post("http://localhost:443/login", userStateObj)
                    .then((res) => {
                        sessionStorage.setItem("userState", JSON.stringify(userStateObj));
                        loginStateObj.user.token = res.data.token;
                        sessionStorage.setItem("loginState", JSON.stringify(loginStateObj));
                        console.log(loginStateObj);

                    });


            }
        }

        return loginStateObj;
    }
    catch (e) {
        return loginState;
    }
};




let newLoginState = getLoginState();
setTimeout(() => {
    newLoginState = setInterval(getLoginState, 30000)
}, 5);


const loginReducer = (state = newLoginState, action) => {
    switch (action.type) {
        case LoginActionType.LOGIN_SUCCESS:
            const newLogin = {
                isLogged: true,
                user: action.payload,
            };
            sessionStorage.setItem("loginState", JSON.stringify(newLogin));
            return newLogin;
        case LoginActionType.LOGOUT_SUCCESS:
            sessionStorage.removeItem("loginState");
            sessionStorage.removeItem("userState");
            return loginState;

        case LoginActionType.LOGIN_FAIL:
            return state;
        default:
            return state;

    }
};


export default loginReducer;