import LinieTabel from './LinieTabel'
import axios from "axios";
import { connect } from "react-redux";
import Exit from './exit.svg'
import AdmMasini from './AdmMasini';
import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
const titleWords = ['Model', 'An', 'Cap. incarcare (kW)', 'Viteza'];
const items = titleWords.map((word, idx) => {
    return <li key={idx}>{word}</li>;
});

function Tabel(props) {


    function deleteCar(param) {
        axios.delete(`http://localhost:443/car/delete/${param}`, {
            headers: {
                'Authorization': `Basic ${myToken}`
            }
        })
            .then((res) => {
                console.log(res.data);
            })

        setTimeout(() => {
            window.location.reload();
        }, 50)

    }
    const { user } = props;
    const myToken = user.loginReducer.user.token;
    const [DateLinieTabel, getMasini] = useState([]);
    let text = "";

    useEffect(() => {
        axios
            .get("http://localhost:443/car/all", {
                headers: {
                    'Authorization': `Basic ${myToken}`,
                },
            })
            .then((response) => {
                getMasini(Array.from(response.data));
            });
    }, []);


    //console.log(DateLinieTabel);
    if (DateLinieTabel.length > 3) {
        text = "TabelBox scrollForTabelPlati";
    }
    else {
        text = "TabelBox";
    }



    return (
        <div className={text}>
            <div className="TitleLine">
                <div className="TitleLine-stg">
                    <ul>{items}</ul>
                </div>
                <div className="TitleLine-drp">
                </div>
            </div>
            {DateLinieTabel.map((val, key) => {
                return (

                    <div className="ContentLines" key={key}>
                        <div className="ContentLines-stg">
                            <LinieTabel model={val.model} year={val.year} batteryCapacity={val.batteryCapacity} vehicleMaxSpeed={val.vehicleMaxSpeed} />
                        </div>
                        <div className="ContentLines-drp" >
                            <img src={Exit} className="ExitButton" alt="" onClick={() => deleteCar(val.id)} />
                        </div>
                    </div>

                );
            }
            )}

        </div>
    );
}
const mapStateToProps = (state) => {
    return {
        user: state,
    };
};
export default connect(mapStateToProps)(Tabel);