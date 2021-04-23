import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './HomeContent.css'
import HomeImg from '../../Images/HomeCarImg.png'
import Statie from '../../Images/statie.svg'
import Masina from '../../Images/masina.svg'


function HomeContent() {
    const CarsNumber = 0;
    const StationsNumber = 0;
    return (

        <div className="HomeContent d-flex">
            <div className="CarBlock">
                <div className="HomePhoto"> <img className="S-C-Img" src={Masina} alt="MasinaImg" />  </div>
                <p>{CarsNumber}</p>
                <p>masini</p>
                <p>inregistrate</p>
            </div>

            <div className="ImgBlock">
                <img className="HomeImg img-fluid" src={HomeImg} alt="HomeImg" />
            </div>

            <div className="StationBlock">
                <div className="HomePhoto"> <img className="S-C-Img" src={Statie} alt="StatieImg" />  </div>
                <p>{StationsNumber}</p>
                <p>statii</p>
                <p>inregistrate</p>
            </div>

        </div>

    );
}

export default HomeContent;