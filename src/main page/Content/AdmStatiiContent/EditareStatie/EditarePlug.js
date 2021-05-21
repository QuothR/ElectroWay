import React, { useState } from 'react'
import './EditarePlug.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'

function EditarePlug(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const stationAddr = JSON.parse(sessionStorage.getItem('stationAddr'))
    const plugAddr = JSON.parse(sessionStorage.getItem('plugAddr'))
    const pointId = sessionStorage.getItem('pointId');
    const [formImput, setFormInput] = useState({});
    const history = useHistory();
    console.log(plugAddr.connectorType)

    function handleModify() {

        const dataBackend = {
            status: 0,
            level: formImput.level ? formImput.level : plugAddr.level,
            connectorType: formImput.connectorType ? formImput.connectorType : plugAddr.connectorType, 
            priceKw: formImput.priceKw ? formImput.priceKw : plugAddr.priceKw,
            chargingSpeedKw: formImput.chargingSpeedKw ? formImput.chargingSpeedKw : plugAddr.chargingSpeedKw
        }

        console.log(dataBackend);
        axios.put(`http://localhost:443/station/${stationAddr.id}/points/${pointId}/plugs/${plugAddr.id}`, dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })
    }

    return (
        <div className="EditarePlug">
            <div className="EditarePlugBox">
                <div className="TitleRow">
                    <p>Editare plug</p>
                    
                </div>
                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleModify()
                    }}>
                        <div className="FormRowTop">
                            <label>Nivel</label>
                            <select list="nivel" className="input-field" placeholder="Nivel" defaultValue={plugAddr.level}
                                onChange={(e) => {
                                    const level = e.target.value;
                                    setFormInput({ ...formImput, ...{ level } });
                                    console.log(level)

                                }} >
                                <option value="1">Nivel 1</option>
                                <option value="2">Nivel 2</option>
                                <option value="3">Nivel 3</option>
                            </select>
                            <label>Conector</label>
                            <select list="conector" className="input-field" placeholder="Tip" defaultValue={plugAddr.connectorType}
                                onChange={(e) => {
                                    const connectorType = e.target.value;
                                    setFormInput({ ...formImput, ...{ connectorType } });

                                }}
                            >
                                <option value="Type 1">Type 1</option>
                                <option value="Type 2">Type 2</option>
                                <option value="Type CSS">Type CSS</option>
                                <option value="Type CHAdeMO">Type CHAdeMO</option>
                            </select>
                            <label>Pret (RON/kWh)</label>
                            <input type="text" placeholder="introdu pret" defaultValue={plugAddr.priceKw} required
                                onChange={(e) => {
                                    const priceKw = e.target.value;
                                    setFormInput({ ...formImput, ...{ priceKw } });

                                }}
                            />
                            <label>Viteza de incarcare kW/h</label>
                            <input type="text" placeholder="introdu viteza incarcare" defaultValue={plugAddr.chargingSpeedKw} required
                                onChange={(e) => {
                                    const chargingSpeedKw = e.target.value;
                                    setFormInput({ ...formImput, ...{ chargingSpeedKw } });

                                }}
                            />

                        </div>
                        <div className="formRowBottom">
                            <Link to="/home/Adm-station/edit/point"><button className="ButonRenunta">Renunta</button></Link>
                            <button className="ButonAdaug" type="submit">Modifica</button>
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



export default connect(mapStateToProps)(EditarePlug);