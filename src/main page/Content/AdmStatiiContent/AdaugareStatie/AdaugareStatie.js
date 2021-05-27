import React, { useEffect, useState } from 'react'
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
    const [errorMessage, setErrorMessage] = useState(null)

    function handleAdd() {

        const dataBackend = {
            address: formImput.address,
            latitude: formImput.latitude,
            longitude: formImput.longitude,
            description: formImput.description ? formImput.description : "empty"
        }

        axios.post("/station", dataBackend, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })
            .then((res) => {
                // console.log(res.data);
                sessionStorage.setItem('stationId', res.data.id);

                // merge la urmatoarea pagina
                handleWorkflow()


            }, (error) => {
                // console.log(error.response.data.details)
                // const mesajEroare = error.response.data.details ? error.response.data.details : "bad request"
                const mesajEroare = "Please enter valid data."
                setErrorMessage(mesajEroare)
            })

    }


    function handleWorkflow() {
        console.log(formImput)
        const chPointObj = {
            nValue: formImput.nrChPoint,
            iValue: 1
        }
        sessionStorage.setItem('nrChPoint', JSON.stringify(chPointObj));
        history.push(`/home/Adm-station/add/point/${chPointObj.iValue}`);
        window.location.reload()

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
                        handleAdd()
                    }}>

                        <div className="FormRowTop">

                            <label>Adresa</label>
                            <input type="text" id="Adresa" placeholder="introdu adresa" required
                                onChange={(e) => {
                                    const address = e.target.value;
                                    setFormInput({ ...formImput, ...{ address } });
                                    setErrorMessage("")
                                }}
                            />

                            <label>Latitudine</label>
                            <input type="text" placeholder="introdu latitudine" required pattern=".{1,}"
                                onChange={(e) => {
                                    const latitude = e.target.value;
                                    setFormInput({ ...formImput, ...{ latitude } });
                                    setErrorMessage("")
                                }}
                            />
                            <label>Longitudine</label>
                            <input type="text" placeholder="introdu longitudine" required pattern=".{1,}"
                                onChange={(e) => {
                                    const longitude = e.target.value;
                                    setFormInput({ ...formImput, ...{ longitude } });
                                    setErrorMessage("")
                                }}
                            />
                            <label>Beneficiile stației</label>
                            <input type="text" placeholder="optional"
                                onChange={(e) => {
                                    const description = e.target.value;
                                    setFormInput({ ...formImput, ...{ description } });
                                    setErrorMessage("")
                                }}
                            />

                            <label>Nr. puncte de incarcare</label>
                            <input placeholder="nr. pct. incarcare > 0" required
                                onChange={(e) => {
                                    const nrChPoint = e.target.value;
                                    setFormInput({ ...formImput, ...{ nrChPoint } });
                                    setErrorMessage("")
                                }}
                            />

                        </div>

                        <div className="SubmitButtonRow">
                            <Link to="/home/Adm-station"><button className="ButonRenunta">Renunta</button></Link>
                            <button className="ButonAdaug" type="submit">Urmator</button>

                        </div>
                        <div className="error-response">
                            <p>{errorMessage}</p>
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