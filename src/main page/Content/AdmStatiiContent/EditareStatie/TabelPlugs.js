import React, { useEffect, useState, useContext } from 'react'
import './TabelPlugs.css'
import { connect } from 'react-redux'
import axios from 'axios';
import { useHistory } from 'react-router'
import * as ReactBootStrap from "react-bootstrap";
//import Edit from "./edit.svg";
import { GrEdit } from 'react-icons/gr';
import { RiDeleteBinLine } from 'react-icons/ri'
import { Link } from 'react-router-dom'


function TabelPlugs(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const pointId = sessionStorage.getItem('pointId');
    const history = useHistory();
    const stationAddr = JSON.parse(sessionStorage.getItem('stationAddr'))
    const [pluguri, getPlugs] = useState([]);

    useEffect(() => {
        axios
            .get(`http://localhost:443/station/${stationAddr.id}/points/${pointId}/plugs`, {
                headers: {
                    Authorization: `Basic ${myToken}`,
                },
            })
            .then((response) => {
                getPlugs(Array.from(response.data));
            });
    }, []);

    function handleDelete(plugId) {

        axios.delete(`http://localhost:443/station/${stationAddr.id}/points/${pointId}/plugs/${plugId}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
        setTimeout(() => {
            window.location.reload();
        }, 20)
    }

    function handleAddPlug() {
        history.push("/home/Adm-station/edit/point/addPlug")
    }


    const renderPlugs = (plug, index) => {
        const status = plug.status === 1 ? "inactive" : "active"
        return (
            <tr key={index}>
                <td style={{ width: "30%" }}>Plug-ul numarul {index + 1}</td>
                <td style={{ width: "14%" }}>{status}</td>
                <td style={{ width: "24%" }}>{plug.connectorType}</td>
                <td style={{ width: "15%" }}>{plug.priceKw}</td>
                <td style={{ width: "11%" }}>{plug.chargingSpeedKw}</td>
                <td style={{ width: "5%" }}
                    onClick={(e) => {
                        sessionStorage.setItem('plugAddr', JSON.stringify(plug));
                        history.push("/home/Adm-station/edit/point/plug")
                    }}
                ><GrEdit /></td>
                <td style={{ width: "5%" }}
                    onClick={(e) => {
                        handleDelete(plug.id);
                    }}
                ><RiDeleteBinLine /></td>
            </tr>
        );
    }

    let text = "";
    if (pluguri.length > 4) {
        text = "scrollForTabelPlati";
    }
    else {
        text = "";
    }

    return (
        <div className="TabelPlugs">
            <div className="TabelPlugsBox">

                <div className="TitleRow">
                    <p>Editare pluguri</p>
                </div>

                <div className="TableRow">
                    <ReactBootStrap.Table striped bordered hover className={text}>
                        <thead>
                            <tr>
                                <th style={{ width: "30%" }}>Numar Plug</th>
                                <th style={{ width: "14%" }}>Status</th>
                                <th style={{ width: "23%" }}>Tip Conector</th>
                                <th style={{ width: "15%" }}>Pret</th>
                                <th style={{ width: "10%" }}>Viteza</th>
                                <th style={{ width: "5%" }}><GrEdit /></th>
                                <td style={{ width: "5%" }}><RiDeleteBinLine /></td>

                            </tr>
                        </thead>
                        <tbody>{pluguri.map(renderPlugs)}</tbody>
                    </ReactBootStrap.Table>
                </div>
                <div className="ButtonRow">
                    <Link to="/home/Adm-station/edit"><button className="ButonRenunta" onClick={() => {
                        sessionStorage.removeItem("pointId")
                    }}>Renunta</button></Link>
                    <button className="ButonAdaugaPlug" onClick={(e) => {
                        e.preventDefault();
                        handleAddPlug();
                    }}
                    >Adauga plug</button>
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



export default connect(mapStateToProps)(TabelPlugs);