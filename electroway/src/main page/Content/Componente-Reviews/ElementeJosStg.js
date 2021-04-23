import React from 'react'
import Tabel from './Tabel'
import stanga from './stanga.png'
import dreapta from './dreapta.png'
import DateTabel from './DateTabelLasate.json'

function ElementeJosStg() {
    return (
        <div className="ElementeJos">
            <div className="Stanga">
                <img src={stanga} className="ArrowButton" alt="" />
            </div>

            <div className="Mijloc">
               {DateTabel.map((val, key) => {
                   return (
                       <div className="ContentMijloc" key={key} >
                           <Tabel statie={val.statie} text={val.text}/>
                       </div>
                   );
                   }    
               )}
            </div>

            <div className="Dreapta">
                <img src={dreapta} className="ArrowButton" alt="" />
            </div>
          
        </div>
    );
}

export default ElementeJosStg