import React, { useState } from 'react'
import './AdaugareMasina.css'
import { connect } from 'react-redux'
import { useHistory } from 'react-router'
import axios from 'axios';
import { Link } from 'react-router-dom'

function AdaugareMasina(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [formImput, setFormInput] = useState({});
    const [carForPlugs, getCarForPostPlugs] = useState([]);
    //const [formImput, setFormInput] = useState();

    const history = useHistory();
    const dataPlug = {
        plugType1: formImput.plugType1 ? formImput.plugType1 : 'Type',
        plugType2: formImput.plugType2 ? formImput.plugType2 : 'Type'
    }
    function postPlugs(res) {


        const carId = res.id;


        console.log(carId);

        if (dataPlug.plugType1 != 'Type') {
            const dataplug = {
                plugType: dataPlug.plugType1
            }
            console.log(dataPlug.plugType1);
            //post la plug 1
            axios.post(`/plug_type/create/${carId}`, dataplug, {
                headers: {
                    'Authorization': `Bearer ${myToken}`
                }
            })
                .then((response) => {
                    console.log(response.data);
                })

            // setTimeout(() => {
            //     history.push("/home/Adm-cars")
            // }, 100)
        }
        if (dataPlug.plugType2 != 'Type') {
            const dataplug = {
                plugType: dataPlug.plugType2
            }
            axios.post(`/plug_type/create/${carId}`, dataplug, {
                headers: {
                    'Authorization': `Bearer ${myToken}`
                }
            })
                .then((ressponse) => {
                    console.log(ressponse.data);
                })


        }
        setTimeout(() => {
            history.push("/home/Adm-cars")
        }, 100)

    }
    function handleAdd() {
        const dataBackend = {
            model: formImput.model,
            year: formImput.year,
            batteryCapacity: formImput.batteryCapacity,
            chargingCapacity: formImput.chargingCapacity,
            // plugType: formImput.plugType ? formImput.plugType:'Type 1',
            vehicleMaxSpeed: formImput.vehicleMaxSpeed,
            auxiliaryKwh: formImput.auxiliaryKwh
        }
        console.log(dataBackend);
        axios.post("/car/create", dataBackend, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })
            .then((res) => {
                getCarForPostPlugs(res.data);
                postPlugs(res.data);
                console.log(res.data);
            })

        // setTimeout(() => {
        //     history.push("/home/Adm-cars")
        // }, 100)
    }


    console.log(dataPlug);
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

                        <div className="FormRowTop">
                            <div className="FormColumnLeft">
                                <label>Model</label>
                                <input type="text" placeholder="Model"
                                    onChange={(e) => {
                                        const model = e.target.value;
                                        //console.log(model);
                                        setFormInput({ ...formImput, ...{ model } });
                                    }}
                                />
                                <label>An</label>
                                <input type="text" placeholder="introdu anul"
                                    onChange={(e) => {
                                        const year = e.target.value;
                                        //console.log(year);
                                        setFormInput({ ...formImput, ...{ year } });
                                    }}
                                />
                                <label>Capacitate baterie</label>
                                <input type="text" placeholder="capacitate baterie"


                                    onChange={(e) => {
                                        const batteryCapacity = e.target.value;
                                        setFormInput({ ...formImput, ...{ batteryCapacity } });
                                    }}
                                />
                                <label> Tip plug 1</label>
                                <select list="conector" className="input-field" placeholder="Tip" defaultValue="Type"
                                    onChange={(ex) => {
                                        const plugType1 = ex.target.value;
                                        setFormInput({ ...formImput, ...{ plugType1 } });
                                    }}

                                >
                                    <option value="Type 1">Type 1</option>
                                    <option value="Type 2">Type 2</option>
                                    <option value="CSS">CSS</option>
                                    <option value="CHAdeMO">CHAdeMO</option>
                                </select>

                            </div>
                            <div className="FormColumnRight">
                                <label>Capacitate incarcare</label>
                                <input type="text" placeholder="capacitate incarcare"
                                    onChange={(e) => {
                                        const chargingCapacity = e.target.value;
                                        setFormInput({ ...formImput, ...{ chargingCapacity } });
                                    }}
                                />
                                <label>Viteza maxima</label>
                                <input type="text" placeholder="viteza maxima"
                                    onChange={(e) => {
                                        const vehicleMaxSpeed = e.target.value;
                                        setFormInput({ ...formImput, ...{ vehicleMaxSpeed } });
                                    }}
                                />
                                <label>Auxiliary kwh</label>
                                <input type="text" placeholder="auxiliar kwh"
                                    onChange={(e) => {
                                        const auxiliaryKwh = e.target.value;
                                        setFormInput({ ...formImput, ...{ auxiliaryKwh } });
                                    }}
                                />
                                <label> Tip plug 2</label>
                                <select list="conector" className="input-field" placeholder="Tip" defaultValue="Type"
                                    onChange={(e) => {
                                        const plugType2 = e.target.value;
                                        setFormInput({ ...formImput, ...{ plugType2 } });
                                    }}

                                >
                                    <option value="Type">Type</option>
                                    <option value="Type 1">Type 1</option>
                                    <option value="Type 2">Type 2</option>
                                    <option value="Type 3">Type 3</option>
                                </select>


                            </div>
                        </div>

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



export default connect(mapStateToProps)(AdaugareMasina);