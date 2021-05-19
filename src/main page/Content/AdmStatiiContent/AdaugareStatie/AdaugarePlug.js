import React, { useState } from 'react'
import './AdaugarePlug.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'

function AdaugarePlug(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const plugObj = JSON.parse(sessionStorage.getItem('nrPlugs'))
    const chPointObj = JSON.parse(sessionStorage.getItem('nrChPoint'))
    const [formImput, setFormInput] = useState({});
    const history = useHistory();


    function handleAdd() {

        const stationId = sessionStorage.getItem('stationId')
        const pointId = sessionStorage.getItem('pointId')
        console.log(pointId)

        const dataBackend = {
            status: 0,
            level: formImput.level ? parseInt(formImput.level, 10) : 1,
            connectorType: formImput.connectorType ? formImput.connectorType : "Type B", //se pune valoarea default
            priceKw: formImput.priceKw,
            chargingSpeedKw: formImput.chargingSpeedKw
        }


        axios.post(`http://localhost:443/station/${stationId}/points/${pointId}`, dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })
    }

    function handleWorkflow() {
        handleAdd();
        plugObj.iValue = plugObj.iValue + 1;
        sessionStorage.setItem('nrPlugs', JSON.stringify(plugObj));
        if (plugObj.iValue <= plugObj.nValue) {
            setTimeout(() => {
                history.push(`/home/Adm-station/add/point/plug/${plugObj.iValue}`);
                window.location.reload()
            },50)
            
        }
        else if (chPointObj.iValue < chPointObj.nValue) {
            chPointObj.iValue = chPointObj.iValue + 1;
            sessionStorage.setItem('nrChPoint', JSON.stringify(chPointObj));
            setTimeout(() => {
                history.push(`/home/Adm-station/add/point/${chPointObj.iValue}`);
                window.location.reload()
            },50)
           
        }
        else {
            setTimeout(() => {
                history.push("/home/Adm-station");
            },50)
            
        }
    }

    function handleDelete() {
        const stationId = sessionStorage.getItem('stationId')
        axios.delete(`http://localhost:443/station/${stationId}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })

        
        setTimeout(()=>{history.push("/home/Adm-station")}, 100)
    }

    return (
        <div className="AdaugarePlug">
            <div className="AdaugarePlugBox">
                <div className="TitleRow">
                    <p>Punctul de incarcare {chPointObj.iValue}</p>
                    <p>Plug-ul {plugObj.iValue} din {plugObj.nValue}</p>
                </div>
                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleWorkflow();
                    }}>
                        <div className="FormRowTop">
                            {/* <label>Status</label>
                            <input type="text" placeholder="introdu status" /> */}
                            <label>Nivel</label>
                            <select list="nivel" className="input-field" placeholder="Nivel"
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
                            <button className="ButonRenunta"
                                onClick={(e) => {
                                    e.preventDefault();
                                    handleDelete();
                                }}>Renunta</button>
                            <button className="ButonAdaug" type="submit">Urmator</button>
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



export default connect(mapStateToProps)(AdaugarePlug);