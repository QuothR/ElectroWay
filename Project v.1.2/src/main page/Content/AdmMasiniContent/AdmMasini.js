import React from 'react'
import './AdmMasini.css'
import Tabel from './Tabel'

function AdmMasini() {
    return (
        <div className="AdmMasini">
            <div className="AdmBox">
                <div className="Inregistrate">
                    <p className="TitluInreg">Masini inregistrate</p>
                    <Tabel />
                </div>
                <div className="Adaugare">

                    <form>
                        <p className="TitluAdaug">Adaugare masina</p>
                        <label>Producator</label>
                        <input type="text" placeholder="introdu producator" />
                        <label>Model</label>
                        <input type="text" placeholder="introdu model" />
                        <label>Consum kW/h</label>
                        <input type="text" placeholder="introdu consum" />
                        <label>Distanta maxima</label>
                        <input type="text" placeholder="introdu distanta maxima" />
                        <div className="DivButonAdaug">
                            <button className="ButonAdaug" type="submit">Salveaza</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default AdmMasini;