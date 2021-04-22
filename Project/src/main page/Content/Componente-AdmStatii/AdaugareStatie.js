import React from 'react'
import './AdaugareStatie.css'

function AdaugareStatie() {
    return (
        <div className="AdaugareStatie">
            <div className="AdaugareStatieBox">
                <div className="TitleRow">
                    <p>Adaugare statie</p>
                </div>

                <div className="FormRow">
                    <form className="Formularul">

                        <div className="FormRowTop">
                            <div className="FormColumnLeft">
                                <label>Oras</label>
                                <input type="text" placeholder="introdu producator" />
                                <label>Adresa</label>
                                <input type="text" placeholder="introdu adresa" />
                                <label>Pret (RON/kWh)</label>
                                <input type="text" placeholder="introdu pret" />
                                <label>Latitudine</label>
                                <input type="text" placeholder="introdu latitudine" />
                                <label>Longitudine</label>
                                <input type="text" placeholder="introdu longitudine" />
                            </div>
                            <div className="FormColumnRight">
                                <label>Voltaj maxim</label>
                                <input type="text" placeholder="introdu voltaj" />
                                <label>Amperaj maxim</label>
                                <input type="text" placeholder="introdu amperaj" />
                                <label>Putere maxima</label>
                                <input type="text" placeholder="introdu putere" />
                                <label>Conector 1</label>
                                <input list="conector" class="input-field" placeholder="Tip" />
                                <label>Conector 2</label>
                                <input list="conector" class="input-field" placeholder="Tip" />
                                <datalist id="conector">
                                    <option value="TypeB"></option>
                                    <option value="TypeC"></option>
                                </datalist>
                            </div>
                        </div>

                        <div className="SubmitButtonRow">
                            <button className="ButonAdaug" type="submit">Adauga</button>
                        </div>

                    </form>
                </div>


            </div>
        </div>
    );
}

export default AdaugareStatie;