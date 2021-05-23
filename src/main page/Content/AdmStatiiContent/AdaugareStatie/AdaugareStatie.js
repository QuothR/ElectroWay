import React, { useState } from 'react'
import './AdaugareStatie.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'


function AdaugareStatie(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({});
    const history = useHistory();

    function handleAdd() {

        const dataBackend = {
            address: formImput.address,
            latitude: formImput.latitude,
            longitude: formImput.longitude
        }

        axios.post("http://localhost:443/station", dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
                sessionStorage.setItem('stationId',res.data.id);
            })
    }


    function handleWorkflow() {
        if (formImput.address != null &&
            formImput.latitude != null &&
            formImput.longitude != null &&
            formImput.nrChPoint != null &&
            formImput.nrChPoint > 0) 
            {
                const chPointObj = {
                    nValue : formImput.nrChPoint, 
                    iValue : 1
                }
                handleAdd();
                sessionStorage.setItem('nrChPoint', JSON.stringify(chPointObj));
                setTimeout(() => {
                    history.push(`/home/Adm-station/add/point/${chPointObj.iValue}`);
                    window.location.reload()
                }, 200)
                
                
                
            }
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
                        handleWorkflow();
                    }}>

                        <div className="FormRowTop">

                            <label>Adresa</label>
                            <input type="text" id="Adresa" placeholder="introdu adresa" required
                                onChange={(e) => {
                                    const address = e.target.value;
                                    setFormInput({ ...formImput, ...{ address } });

                                }}
                            />

                            <label>Latitudine</label>
                            <input type="text" placeholder="introdu latitudine" required pattern=".{1,}"
                                onChange={(e) => {
                                    const latitude = e.target.value;
                                    setFormInput({ ...formImput, ...{ latitude } });
                                }}
                            />
                            <label>Longitudine</label>
                            <input type="text" placeholder="introdu longitudine" required pattern=".{1,}"
                                onChange={(e) => {
                                    const longitude = e.target.value;
                                    setFormInput({ ...formImput, ...{ longitude } });
                                }}
                            />

                            <label>Nr. puncte de incarcare</label>
                            <input placeholder="nr. pct. incarcare > 0" required
                                onChange={(e) => {
                                    const nrChPoint = e.target.value;
                                    setFormInput({ ...formImput, ...{ nrChPoint } });

                                }}
                            />

                        </div>

                        <div className="SubmitButtonRow">
                            <Link to="/home/Adm-station"><button className="ButonRenunta">Renunta</button></Link>
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



export default connect(mapStateToProps)(AdaugareStatie);