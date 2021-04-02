import React from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import './login-register.css'
import 'bootstrap/dist/css/bootstrap.min.css';

function Register(){
    return (
        <div className="login-register">
            <div className="container">
                <div className="row content">
                    <div className="col-md-6 mb-3">
                        <img src={carPicture} className="img-fluid img" alt="carP"/>
                    </div>

                    <div className="col-md6 mx-5 signup-col" id="signup">
                            <h3 className="sigin-text">  <img src={logo} className="img-fluid logo" alt="logo"/> Sign up</h3>
                        <form className="form" >
                            <div className="form-group">
                                <label htmlFor="fname">First name</label>
                                <input type="text" aria-label="First name" className="form-control" placeholder="Your first name"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="lname">Last name</label>
                                <input type="text" aria-label="Last name"  className="form-control" placeholder="Your last name"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Email address</label>
                                <input type="email" className="form-control" placeholder="name@example.com"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" className="form-control" placeholder="Enter password"/>
                            </div>
                            <div className="form-group">
                                <p>Already have an account?
                                    <Link to="./login"> Login here.</Link>
                                </p>
                            </div>
                            <button className="btn btn-class">Confirm</button>
                        </form>
                    </div>



                </div>
            </div>
        </div>
    );
}


export default Register;