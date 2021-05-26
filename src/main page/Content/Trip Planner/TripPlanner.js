import React, { useState, useEffect } from 'react'
import Mapp from './Mapp'
import './TripPlanner.css'
import { connect } from 'react-redux'
import axios from 'axios';



function TripPlanner(props) {
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [masini, getMasini] = useState([]);
    const [formImput, setFormInput] = useState({});
    const [errorMessage, setErrrorMessage] = useState("");
    const [routeBackend, setRouteBackend] = useState({
        totalTravelDistance: 0,
        totalTravelPrice: 0,
        totalTravelTime: 0,
        points: [

        ]
    });


    useEffect(() => {
        axios
            .get("/car/all", {
                headers: {
                    'Authorization': `Bearer ${myToken}`,
                },
            })
            .then((response) => {
                getMasini(Array.from(response.data));
            });

    }, []);


    function handleRoute() {


        const dataBackend = {
            locationsCoords: [
                {
                    lat: formImput.latitudinePornire,
                    lon: formImput.longitudinePronire
                },
                {
                    lat: formImput.latitudineDestinatie,
                    lon: formImput.longitudineDestinatie
                }
            ],
            avoid: "unpavedRoads",
            carData: {
                carId: formImput.carId ? formImput.carId : masini[0].id,
                currentChargeInkW: formImput.batteryLevel
            }
        }
        console.log(dataBackend)


        axios.post("/routing/points", dataBackend, {
            headers: {
                'Authorization': `Bearer ${myToken}`
            }
        })
            .then((res) => {
                setRouteBackend(res.data)
            }, (error) => {
                setErrrorMessage(error.response.data.details);
            })



    }

    function resetData() {
        setRouteBackend({
            totalTravelDistance: 0,
            totalTravelPrice: 0,
            totalTravelTime: 0,
            points: [

            ]
        })
        setErrrorMessage("")
    }

    return (
        <div className="TripPlanner">

            <div className="FormContainer">
                <div className="FormBox">
                    <div className="Title"> Genereaza o ruta</div>

                    <form className="Form" onSubmit={(e) => {
                        e.preventDefault();
                        handleRoute()
                    }}>
                        <div className="labelText">Punct de pornire</div>
                        <input type="text" name="latitude" placeholder="latitudine" required onChange={(e) => {
                            resetData();
                            e.preventDefault();
                            const latitudinePornire = e.target.value;
                            setFormInput({ ...formImput, ...{ latitudinePornire } });
                        }} />
                        <input type="text" name="longitude" placeholder="longitudine" required onChange={(e) => {
                            resetData();
                            e.preventDefault();
                            const longitudinePronire = e.target.value;
                            setFormInput({ ...formImput, ...{ longitudinePronire } });
                        }} />

                        <div className="labelText">Punct destinatie</div>
                        <input type="text" name="latitude" placeholder="latitudine" required onChange={(e) => {
                            resetData();
                            e.preventDefault();
                            const latitudineDestinatie = e.target.value;
                            setFormInput({ ...formImput, ...{ latitudineDestinatie } });
                        }} />
                        <input type="text" name="longitude" placeholder="longitudine" required onChange={(e) => {
                            resetData();
                            e.preventDefault();
                            const longitudineDestinatie = e.target.value;
                            setFormInput({ ...formImput, ...{ longitudineDestinatie } });
                        }} />

                        <div className="labelText">Alegeti o masina</div>
                        <select list="masini" className="input-field" placeholder="Tip"
                            onChange={(e) => {
                                resetData();
                                e.preventDefault();
                                const carId = e.target.value;
                                setFormInput({ ...formImput, ...{ carId } });
                            }}
                        >
                            {
                                masini.map((val, key) => {
                                    return (
                                        <option key={key} value={val.id}>{val.model}</option>
                                    );
                                })
                            }

                        </select>

                        <div className="labelText">Nivelul actual al bateriei</div>
                        <input type="text" name="latitude" placeholder="in kW" required onChange={(e) => {
                            resetData();
                            e.preventDefault();
                            const batteryLevel = e.target.value;
                            setFormInput({ ...formImput, ...{ batteryLevel } });
                        }} />


                        <div className="submitButton">
                            <button className="btnSubmit" type="submit">Genereaza</button>
                        </div>

                        <div className="errors">{errorMessage}</div>
                    </form>

                </div>

            </div>

            <div className="TripContainer">
                <Mapp ruta={routeBackend} />

            </div>

        </div>
    );
}
const mapStateToProps = (state) => {
    return {
        user: state,
    };
};



export default connect(mapStateToProps)(TripPlanner);