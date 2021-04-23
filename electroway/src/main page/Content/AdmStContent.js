import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AdmStContent.css";
import Statie from "./Images/statie.svg"
import Edit from "./Images/edit.svg"
import * as ReactBootStrap from "react-bootstrap";
import { Link } from 'react-router-dom'

function AdmStContent() {
  const stations = [
    { oras: "Iasi", strada: "Str. Ivan Petrovici Pavlov 16", pret: "1.5 RON" },
    { oras: "Iasi", strada: "Sos. Pacurari 138", pret: "1.95 RON" },
    { oras: "Iasi", strada: "Str. 14 Decembrie 1989 2", pret: "0.8 RON" },
    { oras: "Vaslui", strada: "Str. Stefan cel Mare 5", pret: "0.5 RON" },
  ];

  const renderStation = (station, index) => {
    return (
      <tr key={index}>
        <td style={{ width: '20%' }}>{station.oras}</td>
        <td style={{ width: '50%' }}>{station.strada}</td>
        <td style={{ width: '20%' }}>{station.pret}</td>
        <td><img className="edit-img" src={Edit}></img></td>
      </tr>
    );
  };

  return (
    <div className="AdmStatii">
      <div className="AdmBox">
        <div className="Inregistrate">
          <p className="Titlu">Statii inregistrate</p>
          <ReactBootStrap.Table striped bordered hover>
            <thead>
              <tr>
                <th style={{ width: '20%' }}>Oras</th>
                <th style={{ width: '50%' }}>Adresa</th>
                <th style={{ width: '20%' }}>Pret</th>
                <th >Edit</th>
              </tr>
            </thead>
            <tbody>{stations.map(renderStation)}</tbody>
          </ReactBootStrap.Table>
        </div>

        <div className="StationBlock">
          <div className="HomePhoto">
            {" "}
            <img className="S-C-Img" src={Statie} alt="StatieImg" />{" "}
          </div>
          <Link to="/home/Adm-station/Add"><button className="btn-class">Adaugare statie</button></Link>
        </div>
      </div>
    </div>
  );
}

export default AdmStContent;
