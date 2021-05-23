import React, { useEffect, useState, useContext } from 'react'
import './EditareStatie.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'
import * as ReactBootStrap from "react-bootstrap";
import Edit from "../Images/edit.svg";
import Exit from "../Images/exit.svg"
import { GrEdit } from 'react-icons/gr';
import { RiDeleteBinLine } from 'react-icons/ri'

function EditareStatie(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({});
    const [responseText, setResponseTest] = useState(null);

    const history = useHistory();
    const stationAddr = JSON.parse(sessionStorage.getItem('stationAddr'))

    const [puncte, getPuncte] = useState([]);
    const [pluguri, getPlugs] = useState([]);
    useEffect(() => {
        axios
            .get(`http://localhost:443/station/${stationAddr.id}/points`, {
                headers: {
                    Authorization: `Basic ${myToken}`,
                },
            })
            .then((response) => {
                getPuncte(Array.from(response.data));
            });
    }, []);

    function handleDelete(pointId) {

        console.log(pointId)
        axios.delete(`http://localhost:443/station/${stationAddr.id}/points/${pointId}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })

        setTimeout(() => {
            window.location.reload();
        }, 5)
    }

    function handleDelStation() {

        axios.delete(`http://localhost:443/station/${stationAddr.id}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })

       setTimeout(() => {
        history.push("/home/Adm-station")
       },100)
    }

    function handleModifyStation() {

        const dataBackend = {
            address: formImput.address ? formImput.address : stationAddr.address,
            latitude: formImput.latitude ? formImput.latitude : stationAddr.latitude,
            longitude: formImput.longitude ? formImput.longitude : stationAddr.longitude
        }


        axios.put(`http://localhost:443/station/${stationAddr.id}`, dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })

        stationAddr.address=dataBackend.address
        stationAddr.latitude=dataBackend.latitude
        stationAddr.longitude=dataBackend.longitude
        sessionStorage.setItem('stationAddr', JSON.stringify(stationAddr))

    }

    function handleAdaugarePunct() {
        
        const emtyData = {

        }

        axios.post(`http://localhost:443/station/${stationAddr.id}/points`, emtyData, {
            headers: {
                'Authorization': `Basic ${myToken}`
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
                                    }}
                                />

                                <label>Latitudine</label>
                                <input type="text" placeholder="introdu latitudine" defaultValue={stationAddr.latitude} required
                                    onChange={(e) => {
                                        const latitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ latitude } });
                                    }}
                                />
                                <label>Longitudine</label>
                                <input type="text" placeholder="introdu longitudine" defaultValue={stationAddr.longitude} required
                                    onChange={(e) => {
                                        const longitude = e.target.value;
                                        setFormInput({ ...formImput, ...{ longitude } });
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