import React, { useEffect, useState } from "react";import axios from "axios";
import './AdmMasini.css'
import "bootstrap/dist/css/bootstrap.min.css";
import Masina from '../../../Images/masina.svg'
import { useHistory } from 'react-router'
import './AdmMasini.css'
import Tabel from './Tabel'
import { connect } from 'react-redux'
function AdmMasini() {

//  function altaFunctie(){
//     handleGet();
//     redirectionare();
//  }
//     function handleGet() {
//         axios.get("http://localhost:443", {
//             headers: {
//                 Authorization: `Basic ${myToken}`
//             }
//         })
//             .then((res) => {
//                 console.log('sunt prost');
//                  console.log(res.data);
//                 sessionStorage.setItem('userId',res.data.id);
//             })
//     }




  const history = useHistory();
      const redirectionare = () => {
        history.push("/home/Adm-cars/OptiuniIntermediar");
        
        //window.location.reload();
    }
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
          redirectionare();
          //  altaFunctie();
            
          }}>Adaugare masina</button>
        </div>
                </div>
            </div>
        </div>
    );
}
// const mapStateToProps = (state) => {
//     return {
//         user: state,
//     };
// };
export default AdmMasini;