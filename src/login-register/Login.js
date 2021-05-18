import React, { useState } from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'
import './login-register.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import { connect } from 'react-redux'
import { LoginActionType, LoginAuthAction } from '../redux/actions/LoginAction'

function Login(props) {
    const { user, login } = props;
    const [userState, setUser] = useState({});
    const history = useHistory();
    const [errorHandler, setErrorHandler] = useState(
        {
            message: "",
        }
    );

    return (
        <div className="login-register">
            <div className="container">
                <div className="row content">
                    <div className="col-md-6 mb-3">
                        <img src={carPicture} className="img-fluid img" alt="carP" />
                    </div>
                    <div className="col-md6 mx-5 my-4 sigin-col" id="signin">
                        <h3 className="sigin-text">  <img src={logo} className="img-fluid logo" alt="logo" /> Sign in  </h3>

                        <form className="form" onSubmit={(e) => {
                            e.preventDefault();
                            login(userState, history, setErrorHandler);

                        }}>
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input type="email" className="form-control" placeholder="name@example.com"
                                    onChange={(e) => {
                                        const email = e.target.value;
                                        setUser({ ...userState, ...{ email } });
                                        setErrorHandler("");
                                    }} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" className="form-control" placeholder="Enter password"
                                    onChange={(e) => {
                                        const password = e.target.value;
                                        setUser({ ...userState, ...{ password } });
                                        setErrorHandler("");
                                    }} />
                            </div>
                            <div className="form-group">
                                <a href="">Forgot password?</a>
                                <p>Don't have an account?
                                <Link to="./register"> Register here.</Link>
                                </p>
                            </div>

                            <div className=" form-group-bottom">
                                <button type="submit" className="btn btn-class">Sign in</button>
                                <div className="error-response">
                                    <p>{errorHandler.message}</p>
                                    </div>
                                
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        user: state,
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        login: (userState, history, setErrorHandler) => {
            dispatch(LoginAuthAction(userState, history, setErrorHandler));
        },
    };
};


export default connect(mapStateToProps, mapDispatchToProps)(Login);