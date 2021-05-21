import React, { useEffect, useState } from "react";import axios from "axios";
import './AdmMasini.css'
import "bootstrap/dist/css/bootstrap.min.css";
import Exit from './exit.svg'
import Masina from '../../../Images/masina.svg'
import { useHistory } from 'react-router'
import * as ReactBootStrap from "react-bootstrap";
import './AdmMasini.css'
import Tabel from './Tabel'
import { connect } from 'react-redux'
function AdmMasini() {





  const history = useHistory();

    return (
        <div className="AdmMasini">
            <div className="AdmBox">
                <div className="Inregistrate">
                    <p className="TitluInreg">Masini inregistrate</p>
                    <Tabel />
                </div>
                <div className="Adaugare">
                      <div className="CarBlock">
          <div className="HomePhoto">
            {" "}
            <img className="S-C-Img" src={Masina} alt="MasinaImg" />{" "}
          </div>
          <button className="btn-class"  onClick={(e) => {
          e.preventDefault();
          history.push("/home/Adm-cars/AddCar");
          }}>Adaugare masina</button>
        </div>
                </div>
            </div>
        </div>
    );
}

export default AdmMasini;