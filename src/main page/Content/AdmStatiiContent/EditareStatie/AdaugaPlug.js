import React, { useState } from 'react'
import './AdaugaPlug.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'

function AdaugaPlug(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const stationAddr = JSON.parse(sessionStorage.getItem('stationAddr'))
    const pointId = sessionStorage.getItem('pointId')
    const [formImput, setFormInput] = useState({});
    const history = useHistory();

    function refreshPage() {
        window.location.reload();
    }

    function handleAdd() {

        console.log(pointId)

        const dataBackend = {
            status: 0,
            level: formImput.level ? parseInt(formImput.level, 10) : 1,
            connectorType: formImput.connectorType ? formImput.connectorType : "Type B", //se pune valoarea default
            priceKw: formImput.priceKw,
            chargingSpeedKw: formImput.chargingSpeedKw
        }

        console.log(dataBackend.level)

        axios.post(`http://localhost:443/station/${stationAddr.id}/points/${pointId}`, dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })
        
        setTimeout(()=>{history.push("/home/Adm-station/edit/point")},100)
    }


    return (
        <div className="AdaugaPlug">
            <div className="AdaugaPlugBox">
                <div className="TitleRow">
                    <p>Adaugare Plug</p>
                </div>
                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleAdd();
                    }}>
                        <div className="FormRowTop">
                            {/* <label>Status</label>
                            <input type="text" placeholder="introdu status" /> */}
                            <label>Nivel</label>
                            <select list="nivel" className="input-field" placeholder="Nivel"
                                onChange={(e) => {
                                    const level = e.target.value;
                                    setFormInput({ ...formImput, ...{ level } });
                                }} >
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                            <label>Conector</label>
                            <select list="conector" className="input-field" placeholder="Tip"
                                onChange={(e) => {
                                    const connectorType = e.target.value;
                                    setFormInput({ ...formImput, ...{ connectorType } });

                                }}
                            >
                                <option value="Type B">Type B</option>
                                <option value="Type C">Type C</option>
                            </select>
                            <label>Pret (RON/kWh)</label>
                            <input type="text" placeholder="introdu pret"
                                onChange={(e) => {
                                    const priceKw = e.target.value;
                                    setFormInput({ ...formImput, ...{ priceKw } });

                                }}
                            />
                            <label>Viteza de incarcare kW/h</label>
                            <input type="text" placeholder="introdu viteza incarcare"
                                onChange={(e) => {
                                    const chargingSpeedKw = e.target.value;
                                    setFormInput({ ...formImput, ...{ chargingSpeedKw } });

                                }}
                            />

                        </div>
                        <div className="formRowBottom">
                            <Link to="/home/Adm-station/edit/point"><button className="ButonRenunta">Renunta</button></Link>
                            <button className="ButonAdaug" type="submit">Adauga</button>
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



export default connect(mapStateToProps)(AdaugaPlug);