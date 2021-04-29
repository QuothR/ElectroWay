const { AuthActionType } = require("../actions/AuthAction");
const { LoginActionType } = require("../actions/LoginAction");


const authError = {
    message: "",
};

const errorReducer = (state = authError, action) => {
    switch (action.type) {
        case AuthActionType.REGISTER_FAIL:
            return { 
                message: action.payload,
             };
        case LoginActionType.LOGIN_FAIL:
            return{
                message: action.payload,
            };
        default:
            return state;

    }
};

export default errorReducer;