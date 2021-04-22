import React from 'react'
import Tabel from './Tabel'
import DateTabel from './DateTabelLasate.json'
import { IoIosArrowBack } from "react-icons/io";
import { IoIosArrowForward } from "react-icons/io";

function ElementeJosStg() {
    return (
        <div className="ElementeJos">
            <div className="Stanga">
                <IoIosArrowBack className="ArrowButton" />
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
                <IoIosArrowForward className="ArrowButton" />
            </div>
          
        </div>
    );
}

export default ElementeJosStg