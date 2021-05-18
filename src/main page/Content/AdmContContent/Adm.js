import React from "react";
import "./adm.css";
import axios from "axios";

import { connect } from 'react-redux'
import DateCont from "./DateCont.json";

function Adm(props) {


    const { user } = props;
    const myToken = user.loginReducer.user.token;


    function test() {
        axios.get("http://localhost:443/station", {
            headers: {
                'Authorization': `Bearer ${myToken}`,
            }
        }).then(res => {
            console.log(res.data);
        }
        );

    }


    return (
        <div className="admin">
            <h3>Administrate your account</h3>
            <div className="change-forms">
                <div className="column">
                    <form method="post" className="form-administrate">
                        <div className="form-in">
                            <label htmlFor="username" required>
                                Username
              </label>
                            <input
                                type="text"
                                name="username"
                                id="username"
                                defaultValue={DateCont.username}
                            />
                        </div>
                        <div className="form-in">
                            <label htmlFor="email">Email</label>
                            <input
                                type="text"
                                name="email"
                                id="email"
                                defaultValue={DateCont.emailAddress}
                            />
                        </div>

                        <div className="form-in">
                            <label htmlFor="nume">Nume</label>
                            <input type="text" name="nume" id="nume"
                                defaultValue={DateCont.lastName} />
                        </div>

                        <div className="form-in">
                            <label htmlFor="prenume">Prenume</label>
                            <input
                                type="text"
                                name="prenume"
                                id="prenume"
                                defaultValue={DateCont.firstName}
                            />
                        </div>

                        <div className="form-in">
                            <label htmlFor="numarDeTelefon">Numar de telefon</label>
                            <input
                                type="text"
                                name="telefon"
                                id="telefon"
                                defaultValue={DateCont.phoneNumber}
                            />
                        </div>

                        <div className="form-in">
                            <label htmlFor="adresa1">Adresa 1</label>
                            <input type="text" name="ad1" id="ad1"
                                defaultValue={DateCont.address1} />
                        </div>

                        <div className="form-in">
                            <label htmlFor="adresa2">Adresa 2</label>
                            <input type="text" name="ad2" id="ad2"
                                defaultValue={DateCont.address2} />
                        </div>
                    </form>
                </div>

                <div className="column">
                    <form method="post" className="form-administrate">
                        <div className="form-in">
                            <label htmlFor="tara">Tara</label>
                            <input type="text" name="tara" id="tara"
                                defaultValue={DateCont.country} />
                        </div>

                        <div className="form-in">
                            <label htmlFor="oras">Oras</label>
                            <input type="text" name="oras" id="oras"
                                defaultValue={DateCont.city} />
                        </div>

                        <div className="form-in">
                            <label htmlFor="regiune">Regiune</label>
                            <input
                                type="text"
                                name="regiune"
                                id="regiune"
                                defaultValue={DateCont.region}
                            />
                        </div>
                        <div className="form-in">
                            <label htmlFor="codPostal">Cod postal</label>
                            <input
                                type="text"
                                name="codPostal"
                                id="codPostal"
                                defaultValue={DateCont.zipcode}
                            />
                        </div>
                        <div className="form-in">
                            <label htmlFor="parolaVeche" required>
                                Parola veche
              </label>
                            <input
                                type="password"
                                name="parolaVeche"
                                id="parolaVeche"
                                placeholder="parola veche"
                            />
                        </div>

                        <div className="form-in">
                            <label htmlFor="parolaNoua">Parola noua</label>
                            <input
                                type="password"
                                name="parolaNoua"
                                id="parolaNoua"
                                placeholder="parola noua"
                            />
                        </div>

                        <div className="change">
                            <input type="submit" defaultValue="Save" />
                            <button onClick={ (e) => {
                                e.preventDefault();
                                test();
                            }}>dsadsa</button>
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


export default connect(mapStateToProps)(Adm);