import { AuthActionType } from '../actions/AuthAction'
const authState = {
    user: {
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        emailAddress: "",
        address1: "",
        city: "",
        country: "",
        zipcode: ""
    }
}








const authReducer = (state = authState, action) => {
    switch (action.type) {
        case AuthActionType.REGISTER_SUCCESS:
            return {
                user: action.payload,
            };
        case AuthActionType.REGISTER_FAIL:
            return state;
        default:
            return state;
    }
};



export default authReducer;

