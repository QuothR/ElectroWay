import React from 'react'
import './Statistici.css'
import grafic from './Componente-Statistici/grafic.png'

function Statistici() {
    return (
        <div className="Statistici">
            <div className="StatisticiBox">
                <div className="StatisticiButtons">

                    <div className="StatisticiButtons-unu">
                        <input list="perioada" class="input-field" placeholder="anual" />
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
                            <option value="in asteptare">Lunar</option>
                            <option value="respinse">Anual</option>
                        </datalist>
                    </div>

                </div>
                <div className="StatisticiGrafic">
                    <img src={grafic} className="GraficImg" alt="" />
                </div>
            </div>
        </div>
    );
}

export default Statistici;