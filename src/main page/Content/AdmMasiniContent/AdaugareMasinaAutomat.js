import React, { useEffect, useState } from "react";
import './Automat.css'
import { connect } from 'react-redux'
import { useHistory } from 'react-router'
import axios from 'axios';
import { Link } from 'react-router-dom'

function AdaugareMasinaAutomat(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [masinaAleasa, setMasinaAleasa] = useState(1);
    const [DateTemplateuri, getTemplateCars] = useState([]);
    const [ChoosenCar, getCarById] = useState({});
    const history = useHistory();


    let text = "";

    useEffect(() => {
        axios
            .get("http://localhost:443/templatecar/all", {
                headers: {
                    'Authorization': `Basic ${myToken}`,
                },
            })
            .then((response) => {
                getTemplateCars(Array.from(response.data));
            });
    }, []);

    function handleAdd() {

        console.log(masinaAleasa);
        const dataBackend = {
            model: DateTemplateuri[masinaAleasa].model,
            year: DateTemplateuri[masinaAleasa].year,
            batteryCapacity: DateTemplateuri[masinaAleasa].batteryCapacity,
            chargingCapacity: DateTemplateuri[masinaAleasa].chargingCapacity,
            // plugType: formImput.plugType ? formImput.plugType:'Type 1',
            vehicleMaxSpeed: DateTemplateuri[masinaAleasa].vehicleMaxSpeed,
            auxiliaryKwh: DateTemplateuri[masinaAleasa].auxiliaryKwh
        }
        console.log('Date backend ' + dataBackend);
        axios.post("http://localhost:443/car/create", dataBackend, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })

        setTimeout(() => {
            history.push("/home/Adm-cars")
        }, 100)
    }
    return (
        <div className="AdaugareMasina">
            <div className="AdaugareMasinaBox">
                <div className="TitleRow">
                    <p>Adaugare masina</p>
                </div>

                <div className="FormRow">
                    <form className="Formularul" onSubmit={(e) => {
                        e.preventDefault();
                        handleAdd();
                    }}>


                        <label> Alege masina</label>
                        <select list="templatecar" className="input-field" placeholder="Choose Car"
                            onChange={(e) => {
                                const index = e.target.value;
                                setMasinaAleasa(index)
                            }}

                        >
                            {DateTemplateuri.map((val, key) => {
                                return (

                                    <option key={key} value={key}>{val.model}</option>


                                );
                            })
                            }

                        </select>





                        <div className="SubmitButtonRow">
                            <Link to="/home/Adm-cars/OptiuniIntermediar"><button className="ButonInapoi">Inapoi</button></Link>
                            <button className="ButonAdaug" type="submit">Adauga</button>
                            <Link to="/home/Adm-cars"><button className="ButonRenunta">Renunta</button></Link>
                        </div>

                    </form>
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



export default connect(mapStateToProps)(AdaugareMasinaAutomat);