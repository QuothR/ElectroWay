import axios from "axios";

const LoginActionType = {
    LOGIN_SUCCESS: "LOGIN_SUCCESS",
    LOGIN_FAIL: "LOGIN_FAIL",
    LOGOUT_SUCCESS: "LOGOUT_SUCCESS",
};

const LoginAuthAction = (userState, history, setErrorHandler) => {
    return async (dispatch) => {
        try {
            const res = await axios.post("/login", userState);
            sessionStorage.setItem("userState", JSON.stringify(userState));
            const { data } = res;
            dispatch({ type: LoginActionType.LOGIN_SUCCESS, payload: data });
            history.push("/home");
        } catch (error) {
            console.error(error);
            dispatch({ 
                type: LoginActionType.LOGIN_FAIL, 
                payload: error.response.data.details,
             });
            
             setErrorHandler({
                message: error.response.data.details,
             });
        }
    }
};

const LogoutAuthAction = (history) => {
    return  (dispatch) => {
        dispatch({
            type: LoginActionType.LOGOUT_SUCCESS,
            payload: {},
        });
        history.push("/");
    }
}


export { LoginAuthAction, LoginActionType, LogoutAuthAction };