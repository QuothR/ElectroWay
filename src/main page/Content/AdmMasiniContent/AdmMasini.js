import React from "react";
import './AdmMasini.css'
import "bootstrap/dist/css/bootstrap.min.css";
import Masina from '../../../Images/masina.svg'
import { useHistory } from 'react-router'
import './AdmMasini.css'
import Tabel from './Tabel'
import { RO } from "../../../locales/ro/roTran"
import { ENG } from "../../../locales/en/engTran"

function AdmMasini() {
  const language = sessionStorage.getItem('language') ? sessionStorage.getItem('language') : 1
  let languageText = language === 1 ? ENG : RO;
  const history = useHistory();

  return (
    <div className="AdmMasini">
      <div className="AdmBox">
        <div className="Inregistrate">
          <p className="TitluInreg">{languageText.admMasini.carsRegistered}</p>
          <Tabel />
        </div>
        <div className="Adaugare">
          <div className="CarBlock">
            <div className="HomePhoto">
              {" "}
              <img className="S-C-Img" src={Masina} alt="MasinaImg" />{" "}
            </div>
            <button className="btn-class" onClick={(e) => {
              e.preventDefault();
              history.push("/home/Adm-cars/AddCar");
            }}>{languageText.admMasini.addCar}</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdmMasini;