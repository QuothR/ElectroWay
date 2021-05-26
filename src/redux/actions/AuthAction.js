import axios from "axios";

const AuthActionType = {
    REGISTER_SUCCESS: "REGISTER_SUCCESS",
    REGISTER_FAIL: "REGISTER_FAIL",
};

const RegisterAuthAction = (userState, history, setErrorHandler) => {
    return async (dispatch) => {
        console.log(userState);
        try {
            
            const res = await axios.post("/register", userState);
            const { data } = res;
            dispatch({ type: AuthActionType.REGISTER_SUCCESS, payload: data });
            history.push("/login");
        } catch (error) {
            console.error(error);
            dispatch({ 
                type: AuthActionType.REGISTER_FAIL, 
                payload: error.response.data.details,
             });
            
             setErrorHandler({
                message: error.response.data.details,
             });
        }

    };
};


export { RegisterAuthAction, AuthActionType };
