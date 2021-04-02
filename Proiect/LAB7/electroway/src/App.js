import React from 'react'
import Login from './login-register/Login'
import Register from './login-register/Register'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import './main page/Similar components/MainPage.css'
import MainPage from './main page/MainPage'


function App() {
  return (

    <Router>
      <div>
        <Switch>
          <Route path="/home" component={MainPage} />
          <Route path="/register" component={Register} />
          <Route path="/login" component={Login} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
