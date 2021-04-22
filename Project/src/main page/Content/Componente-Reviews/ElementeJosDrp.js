import React from 'react'
import Tabel from './Tabel'
import DateTabel from './DateTabelPrimite.json'
import { IoIosArrowBack } from "react-icons/io";
import { IoIosArrowForward } from "react-icons/io";

function ElementeJosDrp() {
    return (
        <div className="ElementeJos">
            <div className="Stanga">
                <IoIosArrowBack className="ArrowButton" />
            </div>

            {DateTabel.map((val, key) => {
                return (
                    <div className="Mijloc" key={key} >
                        <Tabel statie={val.statie} text={val.text} />
                    </div>
                );
            }
            )}

            <div className="Dreapta">
                <IoIosArrowForward className="ArrowButton" />
            </div>

        </div>
    );
}

export default ElementeJosDrp