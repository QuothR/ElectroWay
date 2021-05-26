import React, { useState } from 'react'
import './AdaugarePlug.css'
import { connect } from 'react-redux'
import axios from 'axios';
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
            status: 1,
            connectorType: formImput.connectorType ? formImput.connectorType : "Type 1", //se pune valoarea default
            priceKw: formImput.priceKw,
            chargingSpeedKw: formImput.chargingSpeedKw
        }


        axios.post(`/station/${stationId}/points/${pointId}`, dataBackend, {
            headers: {
                'Authorization': `Bearer ${myToken}`
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
            },200)
            
        }
        else if (chPointObj.iValue < chPointObj.nValue) {
            chPointObj.iValue = chPointObj.iValue + 1;
            sessionStorage.setItem('nrChPoint', JSON.stringify(chPointObj));
            setTimeout(() => {
                history.push(`/home/Adm-station/add/point/${chPointObj.iValue}`);
                window.location.reload()
            },200)
           
        }
        else {
            setTimeout(() => {
                history.push("/home/Adm-station");
            },200)
            
        }
    }

    function handleDelete() {
        const stationId = sessionStorage.getItem('stationId')
        axios.delete(`/station/${stationId}`, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })

        
        setTimeout(()=>{
            sessionStorage.removeItem("nrPlugs")
            sessionStorage.removeItem("nrChPoint")
            history.push("/home/Adm-station")}, 100)
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
                            <label>Conector</label>
                            <select list="conector" className="input-field" placeholder="Tip"
                                onChange={(e) => {
                                    const connectorType = e.target.value;
                                    setFormInput({ ...formImput, ...{ connectorType } });

                                }}
                            >
                                <option value="Type 1">Type 1</option>
                                <option value="Type 2">Type 2</option>
                                <option value="CSS">CSS</option>
                                <option value="CHAdeMO">CHAdeMO</option>
                                
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