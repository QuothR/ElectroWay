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

    let text = "";
    if (pluguri.length > 4) {
        text = "scrollForTabelPlati";
    }
    else {
        text = "";
    }

    function handleDelete(plugId) {
        
        axios.delete(`http://localhost:443/station/${stationAddr.id}/points/${pointId}/plugs/${plugId}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
    }


    const renderPlugs = (plug, index) => {
        return (
            <tr key={index}>
                <td >Plug-ul numarul {index + 1}</td>
                <td >{plug.level}</td>
                <td >{plug.connectorType}</td>
                <td >{plug.priceKw}</td>
                <td >{plug.chargingSpeedKw}</td>
                <td
                onClick={(e) => {
                    sessionStorage.setItem('plugAddr',JSON.stringify(plug));
                    history.push("/home/Adm-station/edit/point/plug")
                }}
                ><GrEdit /></td>
                <td
                    onClick={(e) => {
                        handleDelete(plug.id);
                    }}
                ><RiDeleteBinLine /></td>
            </tr>
        );
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
                                <th >Numar Plug</th>
                                <th >Nivel</th>
                                <th >Tip Conector</th>
                                <th >Pret</th>
                                <th >Viteza</th>
                                <th><GrEdit /></th>
                                <td><RiDeleteBinLine /></td>

                            </tr>
                        </thead>
                        <tbody>{pluguri.map(renderPlugs)}</tbody>
                    </ReactBootStrap.Table>
                </div>
                <div className="ButtonRow">
                    <Link to="/home/Adm-station/edit"><button className="ButonRenunta">Renunta</button></Link>
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