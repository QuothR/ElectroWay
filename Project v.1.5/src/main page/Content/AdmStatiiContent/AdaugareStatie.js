import React, { useState } from 'react'
import './AdaugareStatie.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'

function AdaugareStatie(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({});
    const [responseText, setResponseTest] = useState(null);


    function handleAdd() {

        axios.post("http://localhost:8090/station", formImput, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res);
            })
    }

    return (
        <div className="AdaugareStatie">
            <div className="AdaugareStatieBox">
                <div className="TitleRow">
                    <p>Adaugare statie</p>
                </div>

                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleAdd();
                    }}>

                        <div className="FormRowTop">
                            <div className="FormColumnLeft">
                                <label>Oras</label>
                                <input type="text" placeholder="Oras"
                                // onChange={(e)=>{
                                //     const oras = e.target.value;
                                //     setFormInput({ ...formImput, ...{ oras } });
                                // }}
                                />
                                <label>Adresa</label>
                                <input type="text" placeholder="introdu adresa"
                                    onChange={(e) => {
                                        const address = e.target.value;
                                        setFormInput({ ...formImput, ...{ address } });
                                    }}
                                />
                                <label>Pret (RON/kWh)</label>
                                <input type="text" placeholder="introdu pret" />
                                <label>Latitudine</label>
                                <input type="text" placeholder="introdu latitudine"
                                    onChange={(e) => {
                                        const latitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ latitude } });
                                    }}
                                />
                                <label>Longitudine</label>
                                <input type="text" placeholder="introdu longitudine"
                                    onChange={(e) => {
                                        const longitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ longitude } });
                                    }}
                                />
                            </div>
                            <div className="FormColumnRight">
                                <label>Voltaj maxim</label>
                                <input type="text" placeholder="introdu voltaj" />
                                <label>Amperaj maxim</label>
                                <input type="text" placeholder="introdu amperaj" />
                                <label>Putere maxima</label>
                                <input type="text" placeholder="introdu putere" />
                                <label>Conector 1</label>
                                <select list="conector" class="input-field" placeholder="Tip">
                                    <option value="TypeB">TypeB</option>
                                    <option value="TypeC">TypeC</option>
                                </select>
                                <label>Conector 2</label>
                                <select class="input-field" placeholder="Tip">
                                    <option value="none">none</option>
                                    <option value="TypeB">TypeB</option>
                                    <option value="TypeC">TypeC</option>
                                </select>

                            </div>
                        </div>

                        <div className="SubmitButtonRow">
                            <button className="ButonAdaug" type="submit">Adauga</button>
                            <Link to="/home/Adm-station"><button className="ButonRenunta">Renunta</button></Link>
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



export default connect(mapStateToProps)(AdaugareStatie);