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

    useEffect(() => {
        axios
            .get("/templatecar/all", {
                headers: {
                    'Authorization': `Bearer ${myToken}`,
                },
            })
            .then((response) => {
                getTemplateCars(Array.from(response.data));
            });
    }, []);

    function handleAdd() {
function postPlug(res,dataplug){
    var carId=res.id;   
     axios.post(`/plug_type/create/${carId}`, dataplug, {
        headers: {
            'Authorization': `Bearer ${myToken}`
        }
    })
        .then((ressponse) => {
            console.log(ressponse.data);
        })
        setTimeout(() => {
        history.push("/home/Adm-cars")
    }, 100)
}

        console.log(masinaAleasa);
        const dataPlug = {
            plugType: DateTemplateuri[masinaAleasa].plugType
           
        }
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
        axios.post("/car/create", dataBackend, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
                postPlug(res.data,dataPlug);

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