import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AdmStContent.css";
import Statie from "./Images/statie.svg";
import Edit from "./Images/edit.svg";
import * as ReactBootStrap from "react-bootstrap";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import axios from "axios";
import { connect } from "react-redux";

function AdmStContent(props) {
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [statii, getStatii] = useState([]);

  sessionStorage.removeItem('stationAddr');
  sessionStorage.removeItem('nrChPoint')
  sessionStorage.removeItem('nrPlugs')

  useEffect(() => {
    axios
      .get("http://localhost:443/station", {
        headers: {
          Authorization: `Basic ${myToken}`,
        },
      })
      .then((response) => {
        getStatii(Array.from(response.data));
      });
  }, []);


  let text = "";
  if (statii.length > 4) {
    text = "scrollForTabelPlati";
  }
  else {
    text = "";
  }

  const renderStation = (station, index) => {
    return (
      <tr key={index}>
        <td style={{ width: "100%" }}>{station.address}</td>
        <td>
          <img className="edit-img" src={Edit}
            onClick={(e) => {
              sessionStorage.setItem('stationAddr', JSON.stringify(station));
              history.push("/home/Adm-station/edit");
            }}
          ></img>
        </td>
      </tr>
    );
  };

  const history = useHistory();
  const redirectionare = () => {
    history.push("/home/Adm-station/Add");
  };

  return (
    <div className="AdmStatii">
      <div className="AdmBox">
        <div className="Inregistrate">
          <p className="Titlu">Statii inregistrate</p>
          <ReactBootStrap.Table striped bordered hover className={text}>
            <thead>
              <tr>
                <th style={{ width: "100%" }}>Adresa</th>
                <th>
                  <img className="edit-img" src={Edit}></img>
                </th>
              </tr>
            </thead>
            <tbody>{statii.map(renderStation)}</tbody>
          </ReactBootStrap.Table>
        </div>

        <div className="StationBlock">
          <div className="HomePhoto">
            {" "}
            <img className="S-C-Img" src={Statie} alt="StatieImg" />{" "}
          </div>
          <button
            className="btn-class"
            onClick={(e) => {
              e.preventDefault();
              redirectionare();
            }}
          >
            Adaugare statie
          </button>
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

export default connect(mapStateToProps)(AdmStContent);
