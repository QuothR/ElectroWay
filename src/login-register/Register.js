import React, { useState } from 'react'
import carPicture from './Electric-car.svg'
import logo from './logo3.png'
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'
import './Register.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { connect } from 'react-redux'
import { RegisterAuthAction } from '../redux/actions/AuthAction'
import { ENG } from "../locales/en/engTran"
import { RO } from "../locales/ro/roTran"

function Register(props) {
    const { user, register } = props;
    const [userState, setUser] = useState({});
    const history = useHistory();
    const [errorHandler, setErrorHandler] = useState(
        {
            message: "",
        }
    );
    const language = sessionStorage.getItem('language') ? sessionStorage.getItem('language') : 1
    let languageText = language === 1 ? ENG : RO;




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

                    <form className="block-sus"
                        onSubmit={(e) => {
                            e.preventDefault();
                            register(userState, history, setErrorHandler);
                            console.log(userState);
                        }}>
                        <div className="coloane">

                            {/* Coloana 1 */}
                            <div className="coloana1">
                                <div className="form-group">
                                    <label htmlFor="uname">Username</label>
                                    <input type="text" aria-label="username" className="form-control" placeholder="Username"
                                        onChange={(e) => {
                                            const username = e.target.value;
                                            setUser({ ...userState, ...{ username } });
                                            setErrorHandler("");
                                            // const roles = { roles :["ROLE_DRIVER", "ROLE_OWNER"]};


                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="fname">Nume</label>
                                    <input type="text" aria-label="First name" className="form-control" placeholder="Numele personal"
                                        onChange={(e) => {
                                            const firstName = e.target.value;
                                            setUser({ ...userState, ...{ firstName } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="nrTel">Numar de telefon</label>
                                    <input type="text" className="form-control" placeholder="07..."
                                        onChange={(e) => {
                                            const phoneNumber = e.target.value;
                                            setUser({ ...userState, ...{ phoneNumber } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="adresa">Adresa</label>
                                    <input type="text" aria-label="adresa" className="form-control" placeholder="Adresa personala"
                                        onChange={(e) => {
                                            const address1 = e.target.value;
                                            setUser({ ...userState, ...{ address1 } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="tara">Tara</label>
                                    <input type="text" aria-label="tara" className="form-control" placeholder="Tara"
                                        onChange={(e) => {
                                            const country = e.target.value;
                                            setUser({ ...userState, ...{ country } });
                                            setErrorHandler("");
                                        }} />
                                </div>


                            </div>


                            {/* Coloana 2 */}

                            <div className="coloana2">
                                <div className="form-group">
                                    <label htmlFor="password">Parola</label>
                                    <input type="password" aria-label="Last name" className="form-control" placeholder="Parola"
                                        onChange={(e) => {
                                            const password = e.target.value;
                                            setUser({ ...userState, ...{ password } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="lname">Prenume</label>
                                    <input type="text" aria-label="Last name" className="form-control" placeholder="Prenumele personal"
                                        onChange={(e) => {
                                            const lastName = e.target.value;
                                            setUser({ ...userState, ...{ lastName } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="email">Adresa de email</label>
                                    <input type="email" name="email" className="form-control" placeholder="name@exemplu.com"
                                        onChange={(e) => {
                                            const emailAddress = e.target.value;
                                            setUser({ ...userState, ...{ emailAddress } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="uname">Oras</label>
                                    <input type="text" aria-label="oras" className="form-control" placeholder="Orasul"
                                        onChange={(e) => {
                                            const city = e.target.value;
                                            setUser({ ...userState, ...{ city } });
                                            setErrorHandler("");
                                        }} />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="codPostal">Cod postal</label>
                                    <input type="text" aria-label="codPostal" className="form-control" placeholder="Cod postal"
                                        onChange={(e) => {
                                            const zipcode = e.target.value;
                                            setUser({ ...userState, ...{ zipcode } });
                                            setErrorHandler("");
                                        }} />
                                </div>



                            </div>


                        </div>
                        <div className="block-jos">
                            <div className="form-group">
                                <p>Already have an account?
                                    <Link to="./login"> Login here.</Link>
                                </p>
                            </div>

                            <div className="form-group-bottom">
                                <button type="submit" className="btn btn-class"
                                    // onClick={(e) => {
                                    //     setUser({ ...userState, ...{ roles: [] } });
                                    // }}
                                >Confirm</button>

                                <div className="error-response">
                                    <p>{errorHandler.message}</p>
                                </div>

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
        register: (userState, history, setErrorHandler) => {
            dispatch(RegisterAuthAction(userState, history, setErrorHandler));
        },
    };
};


export default connect(mapStateToProps, mapDispatchToProps)(Register);