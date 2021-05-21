import React, { useState } from 'react'
import './AdaugareChPoint.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'

function AdaugareChPoint(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const chPointObj = JSON.parse(sessionStorage.getItem('nrChPoint'))
    const [formImput, setFormInput] = useState({});
    const history = useHistory();
    
    

    function handleAdd() {


        const stationId = sessionStorage.getItem('stationId')
        console.log(stationId)
        const emtyData = {

        }

        axios.post(`http://localhost:443/station/${stationId}/points`, emtyData, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
                sessionStorage.setItem('pointId',res.data.id);
            })
    }

    function refreshPage(){ 
        window.location.reload(); 
    }

    function handleWorkflow() {
        if (formImput.nrPlugs > 0) {
            
            const plugObj = {
                nValue: formImput.nrPlugs,
                iValue: 1
            }

            handleAdd();
            sessionStorage.setItem('nrPlugs', JSON.stringify(plugObj));
            setTimeout(() => {
                history.push(`/home/Adm-station/add/point/plug/${plugObj.iValue}`);
                window.location.reload()
            },200)
 
        }
        else {
            return (<h1>null</h1>);
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
        //history.push("/home/Adm-station")
    }

    return (
        <div className="AdaugareChPoint">
            <div className="AdaugareChPointBox">
                <div className="TitleRow">
                    <p>Punctul de incarcare {chPointObj.iValue} din {chPointObj.nValue}</p>
                </div>
                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleWorkflow();
                    }}>
                        <div className="FormRowTop">
                            <label>Nr. plug-uri</label>
                            <input type="text" placeholder="introdu nr. plug-uri"
                                onChange={(e) => {
                                    const nrPlugs = e.target.value;
                                    setFormInput({ ...formImput, ...{ nrPlugs } });
                                    
                                }}
                            />

                        </div>
                        <div className="formRowBottom">
                            <button className="ButonRenunta"
                            onClick={(e) =>{
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

export default connect(mapStateToProps)(AdaugareChPoint);
