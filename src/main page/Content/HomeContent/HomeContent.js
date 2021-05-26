import React, { useEffect, useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './HomeContent.css'
import HomeImg from '../../../Images/HomeCarImg.png'
import Statie from '../../../Images/statie.svg'
import Masina from '../../../Images/masina.svg'
import axios from "axios";
import { connect } from "react-redux";

function HomeContent(props) {

    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [statii, getStatii] = useState([]);
    const [cars, getCars] = useState([]);

    useEffect(() => {
        axios
            .get("/station", {
                headers: {
                    'Authorization': `Bearer ${myToken}`,
                },
            })
            .then((response) => {
                getStatii(Array.from(response.data));
            });
    }, []);

    useEffect(() => {
        axios
            .get("/car/all", {
                headers: {
                    'Authorization': `Bearer ${myToken}`,
                },
            })
            .then((response) => {
                getCars(Array.from(response.data));
            });
    }, []);

    return (

        <div className="HomeContent d-flex">
            <div className="CarBlock">
                <div className="HomePhoto"> <img className="S-C-Img" src={Masina} alt="MasinaImg" />  </div>
                <p>{cars.length}</p>
                <p>masini</p>
                <p>inregistrate</p>
            </div>

            <div className="ImgBlock">
                <img className="HomeImg img-fluid" src={HomeImg} alt="HomeImg" />
            </div>

            <div className="StationBlock">
                <div className="HomePhoto"> <img className="S-C-Img" src={Statie} alt="StatieImg" />  </div>
                <p>{statii.length}</p>
                <p>statii</p>
                <p>inregistrate</p>
            </div>

        </div>

    );
}
const mapStateToProps = (state) => {
    return {
        user: state,
    };
};

export default connect(mapStateToProps)(HomeContent);