import React from 'react'
import './Statistici.css'
import Tabel from './Componente-Statistici/BartChart'

function Statistici() {
    return (
        <div className="Statistici">
            <div className="StatisticiBox">
                <div className="StatisticiButtons">

                    <div className="StatisticiButtons-unu">
                        <input list="perioada" class="input-field" placeholder="lunar" />
                        <datalist id="perioada">
                            <option value="saptamanal">Saptamanal</option>
                            <option value="lunar">Lunar</option>
                            <option value="anual">Anual</option>
                        </datalist>
                    </div>

                    <div className="StatisticiButtons-doi">
                        <input list="sume" class="input-field" placeholder="sume" />
                        <datalist id="sume">
                            <option value="incasate">Saptamanal</option>
                            <option value="respinse">Anual</option>
                        </datalist>
                    </div>

                </div>
                <div className="StatisticiGrafic">
                    
                      
                           <Tabel />
                      
                </div>
            </div>
        </div>
    );
}

export default Statistici