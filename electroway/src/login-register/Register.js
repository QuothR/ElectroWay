import React, { useState } from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import './login-register.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios'
import { Redirect } from 'react-router-dom' 

function Register() {

    const [username, setUsername] = useState(null);
    const [password, setPassword] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState(null);
    const [email, setEmail] = useState(null);
    const [postRes, setPostRes] =useState(null);

    function getUsername(val) {
        setUsername(val.target.value)
    }
    function getPassword(val) {
        setPassword(val.target.value)
    }
    function getPhoneNumber(val) {
        setPhoneNumber(val.target.value)
    }
    function getEmail(val) {
        setEmail(val.target.value)
    }

    function handleRegister(){

        const data = {
            username: username,
            password: password,
            firstName: "Andrei",
            lastName: "Ciobanu",
            phoneNumber: phoneNumber,
            emailAddress: email,
            address1: "adresa",
            city: "Falticeni",
            country: "Romania",
            zipcode: "185224"
        }

        axios.post('http://localhost:8090/register', data)
            .then(res => {
                setPostRes(res.status)
                console.log(res)
            })

    }


    return (
        <div className="login-register">
            <div className="container">
                <div className="row content">
                    <div className="col-md-6 mb-3">
                        <img src={carPicture} className="img-fluid img" alt="carP" />
                    </div>

                    <div className="col-md6 mx-5 signup-col" id="signup">
                        <h3 className="sigin-text">  <img src={logo} className="img-fluid logo" alt="logo" /> Sign up</h3>
                        <div className="form">
                            <div className="form-group">
                                <label htmlFor="fname">Username</label>
                                <input type="text" aria-label="First name" className="form-control" placeholder="Username"
                                    onChange={getUsername} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Parola</label>
                                <input type="password" aria-label="Last name" className="form-control" placeholder="Parola"
                                    onChange={getPassword} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Confirmare parola</label>
                                <input type="password" name="password" className="form-control" placeholder="Confirmare parola"
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="nrTel">Numar de telefon</label>
                                <input type="text" className="form-control" placeholder="07..."
                                    onChange={getPhoneNumber} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Adresa de email</label>
                                <input type="email" name="password" className="form-control" placeholder="name@exemplu.com"
                                    onChange={getEmail} />
                            </div>
                            <div className="form-group">
                                <p>Already have an account?
                                    <Link to="./login"> Login here.</Link>
                                </p>
                            </div>
                            <button className="btn btn-class" onClick={handleRegister}>Confirm</button>
                            {postRes === 200 ? <Redirect to={{ pathname: "/login"}} /> : console.log("eh") }
                           
                        </div>
                    </div>



                </div>
            </div>
        </div>
    );
}


export default Register;