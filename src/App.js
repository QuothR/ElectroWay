import React from 'react'
import Login from './login-register/Login'
import Register from './login-register/Register'
import { BrowserRouter as Router, Switch} from 'react-router-dom'
import './main page/Similar components/MainPage.css'
import ResetPassword from './login-register/ResetPassword'
import MainPage from './main page/MainPage'
import MainContent from './MainPageLanding/compForMain/MainContent'
import ProtectedRoute from './Routes/ProtectedRoute'
import ProtectedLoginRoute from './Routes/ProtectedLoginRoute'

import { connect } from 'react-redux'

function App(props) {

  const { user } = props;
  const isAuth = user.loginReducer.isLogged;

  return (
    <Router>
      <div>
        <Switch>
          <ProtectedLoginRoute path="/resetpassword" exact component={ResetPassword} isAuth={isAuth} />
          <ProtectedLoginRoute path="/" exact component={MainContent} isAuth={isAuth} />
          <ProtectedRoute path="/home" component={MainPage} isAuth={isAuth} />
          <ProtectedLoginRoute path="/register" component={Register} isAuth={isAuth} />
          <ProtectedLoginRoute path="/login" component={Login} isAuth={isAuth} />
        </Switch>
      </div>
    </Router>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state,
  };
};



export default connect(mapStateToProps)(App);
