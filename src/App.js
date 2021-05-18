import React, { useState, useEffect } from 'react'
import Login from './login-register/Login'
import Register from './login-register/Register'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import './main page/Similar components/MainPage.css'
import MainPage from './main page/MainPage'
import MainContent from './MainPageLanding/compForMain/MainContent'
import ProtectedRoute from './Routes/ProtectedRoute'
import ProtectedLoginRoute from './Routes/ProtectedLoginRoute'
import axios from 'axios'
import { connect } from 'react-redux'

function App(props) {

  const { user } = props;
  const isAuth = user.loginReducer.isLogged;

  return (
    <Router>
      <div>
        <Switch>
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
