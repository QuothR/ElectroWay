import React from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import './login-register.css'
import 'bootstrap/dist/css/bootstrap.min.css'

function Login(){
    return (
        <div className="login-register">
            <div className="container">
            <div className="row content">
                <div className="col-md-6 mb-3">
                    <img src={carPicture} className="img-fluid img" alt="carP"/> 
                </div>
                <div className="col-md6 mx-5 my-4 sigin-col" id="signin">
                    <h3 className="sigin-text">  <img src={logo} className="img-fluid logo" alt="logo"/> Sign in  </h3>

                    <form className="form">
                        <div className="form-group">
                            <label htmlFor="email">Email address</label>
                            <input type="email" className="form-control" placeholder="name@example.com"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input type="password" name="password" className="form-control" placeholder="Enter password"/>
                        </div>
                        <div className="form-group">
                            <a href="">Forgot password?</a>
                            <p>Don't have an account? 
                                <Link to="./register"> Register here.</Link>
                            </p>
                        </div>
                        
                    </form>
                    <button className="btn btn-class" onClick={() => {window.location.pathname = "/home"}}>Sign in</button>
                </div>
            </div>
        </div>
    </div>
    );
}


export default Login;