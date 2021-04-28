import React, { useState } from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import {useHistory} from 'react-router'
import './Register.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios'
import { Redirect } from 'react-router-dom'
import {connect} from 'react-redux'
import {RegisterAuthAction} from '../redux/actions/AuthAction'

function Register(props) {




    // function handleRegister() {

    //     const data = {
    //         username: user.username,
    //         password: user.password,
    //         firstName: user.firstName,
    //         lastName: user.lastName,
    //         phoneNumber: user.phoneNumber,
    //         emailAddress: user.emailAddress,
    //         address1: user.address1,
    //         city: user.city,
    //         country: user.country,
    //         zipcode: user.zipcode
    //     }

    //     axios.post('http://localhost:8090/register', data)
    //         .then(res => {
    //             setPostRes(res.status)
    //             console.log(res)
    //         })


    // }
    const { user, register } = props;
    const [userState, setUser] = useState({});
    const history = useHistory();
    

    return (
        <div className="register">
            <div className="block">
                <div className="block-stanga">
                    <img src={carPicture} className="img-fluid img" alt="carP" />
                </div>

                <div className="block-dreapta">
                    <div className="LinieTitlu">
                        <h2> <img src={logo} className="img-fluid logo" alt="logo" /> Sign up </h2>
                    </div>

                    <form className="coloane"
                    onSubmit={(e) =>{
                        e.preventDefault();
                        register(userState, history);
                    }}>

                        {/* Coloana 1 */}
                        <div className="coloana1">
                            <div className="form-group">
                                <label htmlFor="uname">Username</label>
                                <input type="text" aria-label="username" className="form-control" placeholder="Username"
                                    onChange={(e) => {
                                        const username = e.target.value;
                                        setUser({ ...userState, ...{ username } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="fname">Nume</label>
                                <input type="text" aria-label="First name" className="form-control" placeholder="Numele personal"
                                    onChange={(e) => {
                                        const firstName = e.target.value;
                                        setUser({ ...userState, ...{ firstName } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="nrTel">Numar de telefon</label>
                                <input type="text" className="form-control" placeholder="07..."
                                    onChange={(e) => {
                                        const phoneNumber = e.target.value;
                                        setUser({ ...userState, ...{ phoneNumber } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="adresa">Adresa</label>
                                <input type="text" aria-label="adresa" className="form-control" placeholder="Adresa personala"
                                    onChange={(e) => {
                                        const address1 = e.target.value;
                                        setUser({ ...userState, ...{ address1 } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="tara">Tara</label>
                                <input type="text" aria-label="tara" className="form-control" placeholder="Tara"
                                    onChange={(e) => {
                                        const country = e.target.value;
                                        setUser({ ...userState, ...{ country } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <p>Already have an account?
                                    <Link to="./login"> Login here.</Link>
                                </p>
                            </div>

                            <button type="submit" className="btn btn-class">Confirm</button>
                            


                        </div>


                        {/* Coloana 2 */}

                        <div className="coloana2">
                            <div className="form-group">
                                <label htmlFor="password">Parola</label>
                                <input type="password" aria-label="Last name" className="form-control" placeholder="Parola"
                                    onChange={(e) => {
                                        const password = e.target.value;
                                        setUser({ ...userState, ...{ password } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="lname">Prenume</label>
                                <input type="text" aria-label="Last name" className="form-control" placeholder="Prenumele personal"
                                    onChange={(e) => {
                                        const lastName = e.target.value;
                                        setUser({ ...userState, ...{ lastName } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="email">Adresa de email</label>
                                <input type="email" name="email" className="form-control" placeholder="name@exemplu.com"
                                    onChange={(e) => {
                                        const emailAddress = e.target.value;
                                        setUser({ ...userState, ...{ emailAddress } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="uname">Oras</label>
                                <input type="text" aria-label="oras" className="form-control" placeholder="Orasul"
                                    onChange={(e) => {
                                        const city = e.target.value;
                                        setUser({ ...userState, ...{ city } });
                                    }} />
                            </div>

                            <div className="form-group">
                                <label htmlFor="codPostal">Cod postal</label>
                                <input type="text" aria-label="codPostal" className="form-control" placeholder="Cod postal"
                                    onChange={(e) => {
                                        const zipcode = e.target.value;
                                        setUser({ ...userState, ...{ zipcode } });
                                    }} />
                            </div>



                        </div>
                    </form>
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
      register: (userState, history) => {
        dispatch(RegisterAuthAction(userState, history));
      },
    };
  };


export default connect(mapStateToProps, mapDispatchToProps)(Register);