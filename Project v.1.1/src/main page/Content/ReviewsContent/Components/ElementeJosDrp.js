import React from 'react'
import Tabel from './Tabel'
import stanga from './stanga.png'
import dreapta from './dreapta.png'
import DateTabel from './DateTabelPrimite.json'

function ElementeJosDrp() {
    return (
        <div className="ElementeJos">
            <div className="Stanga">
                <img src={stanga} className="ArrowButton" alt="" />
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
                <img src={dreapta} className="ArrowButton" alt="" />
            </div>

        </div>
    );
}

export default ElementeJosDrp