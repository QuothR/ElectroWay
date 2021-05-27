import React, { useEffect, useState, useContext } from 'react'
import './EditareStatie.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'
import * as ReactBootStrap from "react-bootstrap";
import { GrEdit } from 'react-icons/gr';
import { RiDeleteBinLine } from 'react-icons/ri'

function EditareStatie(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({})
    const history = useHistory();
    const stationAddr = JSON.parse(sessionStorage.getItem('stationAddr'))
    const [errorMessage, setErrorMessage] = useState(null)

    const [puncte, getPuncte] = useState([]);
    useEffect(() => {
        axios
            .get(`/station/${stationAddr.id}/points`, {
                headers: {
                    'Authorization': `Bearer ${myToken}`,
                },
            })
            .then((response) => {
                getPuncte(Array.from(response.data));
            });
    }, []);

    function handleDelete(pointId) {

        console.log(pointId)
        axios.delete(`/station/${stationAddr.id}/points/${pointId}`, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })

        setTimeout(() => {
            window.location.reload();
        }, 5)
    }

    function handleDelStation() {

        axios.delete(`/station/${stationAddr.id}`, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })

        setTimeout(() => {
            history.push("/home/Adm-station")
        }, 100)
    }

    function handleModifyStation() {
        console.log(formImput.latitude, formImput.longitude)
        if(formImput.latitude >= -90 && formImput.latitude <= 90 && formImput.longitude >= -180 && formImput.longitude <= 180) {
            const dataBackend = {
                address: formImput.address ? formImput.address : stationAddr.address,
                latitude: formImput.latitude ? formImput.latitude : stationAddr.latitude,
                longitude: formImput.longitude ? formImput.longitude : stationAddr.longitude,
                description: formImput.description ? formImput.description : stationAddr.description
            }
    
    
            axios.put(`/station/${stationAddr.id}`, dataBackend, {
                headers: {
                    'Authorization': `Bearer ${myToken}`
                }
            })
                .then((res) => {
                    console.log(res.data);
                }, (error) => {
                    // console.log(error.response.data.details)
                    // const mesajEroare = error.response.data.details ? error.response.data.details : "bad request"
                    const mesajEroare = "Something went wrong."
                    setErrorMessage(mesajEroare)
                })
    
            stationAddr.address = dataBackend.address
            stationAddr.latitude = dataBackend.latitude
            stationAddr.longitude = dataBackend.longitude
            sessionStorage.setItem('stationAddr', JSON.stringify(stationAddr))
        }
        else {
            const mesajEroare = "Please enter valid data."
            setErrorMessage(mesajEroare)
        }

    }

    function handleAdaugarePunct() {

        const emtyData = {

        }

        axios.post(`/station/${stationAddr.id}/points`, emtyData, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
                sessionStorage.setItem('pointId', res.data.id);
                history.push("/home/Adm-station/edit/point");
            })
    }

    let text = "";
    if (puncte.length > 3) {
        text = "scrollForTabelPlati";
    }
    else {
        text = "";
    }

    const renderPoints = (point, index) => {

        return (

            <tr key={index}>
                <td style={{ width: "100%" }}>{index + 1}</td>

                <td
                    onClick={(e) => {
                        sessionStorage.setItem('pointId', point.id);
                        history.push("/home/Adm-station/edit/point");
                    }}><GrEdit />
                </td>

                <td
                    onClick={(e) => {
                        handleDelete(point.id);
                    }}
                ><RiDeleteBinLine /></td>
            </tr>

        );

    };



    return (
        <div className="EditareStatie">
            <div className="EditareStatieBox">
                <div className="BoxLeft">
                    <div className="TitleRow">
                        <p>Editare statie {stationAddr.id}</p>
                    </div>

                    <div className="FormRow">
                        <form className="Formularul">

                            <div className="FormRowTop">

                                <label>Adresa</label>
                                <input type="text" id="Adresa" placeholder="introdu adresa" defaultValue={stationAddr.address} required
                                    onChange={(e) => {
                                        const address = e.target.value;
                                        setFormInput({ ...formImput, ...{ address } });
                                        setErrorMessage("")
                                    }}
                                />

                                <label>Latitudine</label>
                                <input type="text" placeholder="introdu latitudine" defaultValue={stationAddr.latitude} required
                                    onChange={(e) => {
                                        const latitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ latitude } });
                                        setErrorMessage("")
                                    }}
                                />
                                <label>Longitudine</label>
                                <input type="text" placeholder="introdu longitudine" defaultValue={stationAddr.longitude} required
                                    onChange={(e) => {
                                        const longitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ longitude } });
                                        setErrorMessage("")
                                    }}
                                />
                                <label>Beneficiile stației</label>
                                <input type="text" placeholder="optional" defaultValue={stationAddr.description} required pattern=".{1,}"
                                    onChange={(e) => {
                                        const description = e.target.value;
                                        setFormInput({ ...formImput, ...{ description } });
                                        setErrorMessage("")
                                    }}
                                />

                            </div>

                            <div className="SubmitButtonRow">
                                <button className="ButonModifica"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        handleModifyStation();
                                    }}
                                >Modifica</button>
                                <button className="ButonSterg"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        handleDelStation();
                                    }}>Stergere</button>
                                    <div className="error-response">
                                        <p>{errorMessage}</p>
                                    </div>
                            </div>

                        </form>
                    </div>
                </div>

                <div className="BoxRight">
                    <div className="TitleRow">
                        <p>Editare puncte de incarcare</p>
                    </div>

                    <div className="TableRow">
                        <ReactBootStrap.Table striped bordered hover className={text}>
                            <thead>
                                <tr>
                                    <th style={{ width: "100%" }}>Numar Punct</th>
                                    <td><GrEdit /></td>
                                    <td><RiDeleteBinLine /></td>

                                </tr>
                            </thead>
                            <tbody>{puncte.map(renderPoints)}</tbody>
                        </ReactBootStrap.Table>
                    </div>

                    <div className="ButonRenuntaRow">
                        <Link to="/home/Adm-station"><button className="ButonRenunta" >Renunta</button></Link>
                        <button className="ButonAdaugaPunct" onClick={(e) => {
                            e.preventDefault();
                            handleAdaugarePunct();
                        }}
                        >Adauga punct</button>
                    </div>
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



export default connect(mapStateToProps)(EditareStatie);