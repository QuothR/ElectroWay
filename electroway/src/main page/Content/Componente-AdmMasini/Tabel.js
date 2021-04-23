import React from 'react'
import LinieTabel from './LinieTabel'
import DateLinieTabel  from './DateLinieTabel.json'
import Exit from './exit.svg'
import AdmMasini from '../AdmMasini';


const titleWords = ['Producator', 'Model', 'Consum (kW/h)', 'Distanta (Km)'];
const items = titleWords.map((word, idx) => {
    return <li key={idx}>{word}</li>;
});

function Tabel() {
    let text = "";
    if(DateLinieTabel.length > 4){
        text = "TabelBox scrollForTabelPlati";
    }
    else{
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
                            <LinieTabel producator={val.producator} model={val.model} consum={val.consum} distanta={val.distanta} />
                        </div>
                        <div className="ContentLines-drp" >
                            <img src={Exit} className="ExitButton" alt="" />
                        </div>
                    </div>

                );
            }
            )}

        </div>
    );
}

export default Tabel;
