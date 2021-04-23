import React, {useState} from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import './login-register.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios'

function Login() {

    const [email, setEmail] = useState(null);
    const [password, setPassword] = useState(null);
    const [ postRes, setPostRes] = useState(null);

    function getEmail(val) {
        setEmail(val.target.value)
    }
    function getPassword(val) {
        setPassword(val.target.value)
    }

    function handleLogin() {
        const data = {
            email: email,
            password: password
        }

        axios.post('http://localhost:8090/login', data)
            .then(res => {
                setPostRes(res)
            })
            
       
    }

    return (
        <div className="login-register">
            <div className="container">
                <div className="row content">
                    <div className="col-md-6 mb-3">
                        <img src={carPicture} className="img-fluid img" alt="carP" />
                    </div>
                    <div className="col-md6 mx-5 my-4 sigin-col" id="signin">
                        <h3 className="sigin-text">  <img src={logo} className="img-fluid logo" alt="logo" /> Sign in  </h3>

                        <div className="form">
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input type="email" className="form-control" placeholder="name@example.com"
                                    onChange={getEmail} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" className="form-control" placeholder="Enter password"
                                    onChange={getPassword} />
                            </div>
                            <div className="form-group">
                                <a href="">Forgot password?</a>
                                <p>Don't have an account?
                                <Link to="./register"> Register here.</Link>
                                </p>
                            </div>

                        </div>
                        <button className="btn btn-class" onClick={handleLogin}>Sign in</button>
                    </div>
                </div>
            </div>
        </div>
    );
}


export default Login;