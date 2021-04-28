import { applyMiddleware, combineReducers, compose, createStore } from 'redux'
import authReducer from './reducers/AuthReducer'
import loginReducer from './reducers/LoginReducer'
import thunk from 'redux-thunk'


const allReducers = combineReducers({
    authReducer,
    loginReducer,
});

const middlewareEnhancer = applyMiddleware(thunk);
const composedEnchancer = compose(middlewareEnhancer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__())



const myStore = createStore(
    allReducers,
    composedEnchancer);

export default myStore;