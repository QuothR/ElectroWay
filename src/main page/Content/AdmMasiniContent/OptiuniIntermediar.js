import React, { useState } from 'react'
import './Optiuni.css'
import { connect } from 'react-redux'
import { useHistory } from 'react-router'
import { Link } from 'react-router-dom'
import NoteBook from "./Notebook-rafiki.svg"
import SelectAmico from "./Select-amico.svg"

function OptiuniIntermediar(props) {
    const { user } = props;
    const [formImput, setFormInput] = useState({});
    //const [formImput, setFormInput] = useState();

    const history = useHistory();
    const redirectionareManual = () => {
        history.push("/home/Adm-cars/OptiuniIntermediar/AddCar");

        //window.location.reload();
    }
    const redirectionareAutomat = () => {
        history.push("/home/Adm-cars/OptiuniIntermediar/AddCarFromList");

        //window.location.reload();
    }
    return (
        <div className="OptiuniIntermediar">
            <div className="OptiuniBox">
                <div className="TitleRow">
                    <p>Adaugare masina</p>
                </div>

                <div className="FormRow">
                    <div className="Formularul">
                        <div className="FormRowTop">
                            <div className="FormColumnLeft">
                                <img className="S-C-Img" src={NoteBook} alt="MasinaImg" />
                                <button className="ButonManual" onClick={(e) => {
                                    e.preventDefault();
                                    redirectionareManual();
                                    //  altaFunctie();

                                }}>Adauga manual</button>
                            </div>
                            <div className="FormColumnRight">
                                <img className="S-C-Img" src={SelectAmico} alt="MasinaImg" />
                                <button className="ButonAdaug" onClick={(e) => {
                                    e.preventDefault();
                                    redirectionareAutomat();
                                    //  altaFunctie();

                                }}>Alege o masina</button>
                            </div>



                        </div>
                    </div>

                </div>

                <div className="SubmitButtonRow">

                    <Link to="/home/Adm-cars"><button className="ButonRenunta">Renunta</button></Link>
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



export default connect(mapStateToProps)(OptiuniIntermediar);