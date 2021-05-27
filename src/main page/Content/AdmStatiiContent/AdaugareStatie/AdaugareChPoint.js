import React, { useState } from 'react'
import './AdaugareChPoint.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { useHistory } from 'react-router'

function AdaugareChPoint(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const chPointObj = JSON.parse(sessionStorage.getItem('nrChPoint'))
    const [formImput, setFormInput] = useState({});
    const history = useHistory();
    const [errorMessage, setErrorMessage] = useState(null)
    

    function handleAdd() {

        if (formImput.nrPlugs > 0) {
            const stationId = sessionStorage.getItem('stationId')
            console.log(stationId)
            const emtyData = {

            }

            axios.post(`/station/${stationId}/points`, emtyData, {
                headers: {
                    'Authorization': `Bearer ${myToken}`
                }
            })
                .then((res) => {
                    console.log(res.data);
                    sessionStorage.setItem('pointId',res.data.id);
                    handleWorkflow()
                }, (error) => {
                    // console.log(error.response.data.details)
                    // const mesajEroare = error.response.data.details ? error.response.data.details : "bad request"
                    const mesajEroare = "Something went wrong."
                    setErrorMessage(mesajEroare)
                })
        }
        else {
            const mesajEroare = "Please enter valid data."
            setErrorMessage(mesajEroare)
        }
    }
    function handleWorkflow() {
        const plugObj = {
            nValue: formImput.nrPlugs,
            iValue: 1
        }
        sessionStorage.setItem('nrPlugs', JSON.stringify(plugObj));
        setTimeout(() => {
            history.push(`/home/Adm-station/add/point/plug/${plugObj.iValue}`);
            window.location.reload()
        },200)
    }

    function handleDelete() {

        const stationId = sessionStorage.getItem('stationId')
        axios.delete(`/station/${stationId}`, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })

        
        setTimeout(()=>{
            sessionStorage.removeItem("nrChPoint")
            history.push("/home/Adm-station")}, 100)
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
                        handleAdd();
                    }}>
                        <div className="FormRowTop">
                            <label>Nr. plug-uri</label>
                            <input type="text" placeholder="introdu nr. plug-uri"
                                onChange={(e) => {
                                    const nrPlugs = e.target.value;
                                    setFormInput({ ...formImput, ...{ nrPlugs } });
                                    setErrorMessage("")
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
                            <div className="error-response">
                                <p>{errorMessage}</p>
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

export default connect(mapStateToProps)(AdaugareChPoint);
