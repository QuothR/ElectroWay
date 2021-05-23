import React, { useState } from 'react'
import './AdaugareMasina.css'
import { connect } from 'react-redux'
import { useHistory } from 'react-router'
import axios from 'axios';
import { Link } from 'react-router-dom'
import {RO} from "../../../locales/ro/roTran"
import {ENG} from "../../../locales/en/engTran"

function AdaugareMasina(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({});
    const history = useHistory();

    const [language, setLanguage] = useState(sessionStorage.getItem('language') ? sessionStorage.getItem('language') : 1)
    let text = language == 1 ? ENG : RO;

    function handleAdd() {
        const dataBackend = {
            model: formImput.model,
            year: formImput.year,
            batteryCapacity: formImput.batteryCapacity,
            chargingCapacity: formImput. chargingCapacity,
            plugType: formImput.plugType ? formImput.plugType:'Type 1',
            vehicleMaxSpeed: formImput. vehicleMaxSpeed,
            auxiliaryKwh: formImput.auxiliaryKwh
        }
        console.log(dataBackend);
        axios.post("http://localhost:443/car/create", dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })
           
            setTimeout(() => {
                history.push("/home/Adm-cars")
            }, 200)
    }

    return (
        <div className="AdaugareMasina">
            <div className="AdaugareMasinaBox">
                <div className="TitleRow">
                    <p>Adaugare masina</p>
                </div>

                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleAdd();
                    }}>

                        <div className="FormRowTop">
                            <div className="FormColumnLeft">
                                <label>Model</label>
                                <input type="text" placeholder="Model"
                                onChange={(e)=>{
                                    const model = e.target.value;
                                    //console.log(model);
                                    setFormInput({ ...formImput, ...{ model } });
                                }}
                                />
                                <label>An</label>
                                <input type="text" placeholder="introdu anul"
                                    onChange={(e) => {
                                        const year = e.target.value;
                                        //console.log(year);
                                        setFormInput({ ...formImput, ...{ year } });
                                    }}
                                />
                                <label>Capacitate baterie</label>
                                <input type="text" placeholder="capacitate baterie" 
                              
                                 
                                     onChange={(e) => {
                                         const batteryCapacity = e.target.value;
                                         setFormInput({ ...formImput, ...{ batteryCapacity } });
                                    }}
                                /> 
                               
                            </div>
                            <div className="FormColumnRight">
                            <label>Capacitate incarcare</label>
                                <input type="text" placeholder="capacitate incarcare"
                                     onChange={(e) => {
                                         const chargingCapacity= e.target.value;
                                        setFormInput({ ...formImput, ...{chargingCapacity} });
                                     }}
                                />
                                 <label>Viteza maxima</label>
                                <input type="text" placeholder="viteza maxima"
                                     onChange={(e) => {
                                         const vehicleMaxSpeed= e.target.value;
                                        setFormInput({ ...formImput, ...{vehicleMaxSpeed} });
                                     }}
                                />
                                <label>Auxiliary kwh</label>
                                <input type="text" placeholder="auxiliar kwh"
                                onChange={(e) => {
                                         const auxiliaryKwh= e.target.value;
                                        setFormInput({ ...formImput, ...{auxiliaryKwh} });
                                     }}
                                     />
                                <label> Tip plug</label>
                                <select list="conector" className="input-field" placeholder="Tip"
                                   onChange={(e) => {
                                    const plugType= e.target.value;
                                   setFormInput({ ...formImput, ...{plugType} });
                                }}
                                
                                >
                                    <option value="Type1">Type 1</option>
                                    <option value="Type2">Type 2</option>

                                </select>
                              

                            </div>
                        </div>

                        <div className="SubmitButtonRow">
                            <button className="ButonAdaug" type="submit">Adauga</button>
                            <Link to="/home/Adm-cars"><button className="ButonRenunta">Renunta</button></Link>
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



export default connect(mapStateToProps)(AdaugareMasina);