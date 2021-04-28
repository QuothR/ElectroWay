import React from 'react'

function Form(){
    return(

        <div className="Form"> 

           <p className="Scris_X">Punct de pornire</p>
           <label><input type="text" id="destinatia_x" name="destinatia_x" placeholder="Locația_X"/></label>
           <p className="Scris_Y">Destinație</p>
           <label><input type="text" id="destinatia_x" name="destinatia_x" placeholder="Locația_Y" /></label>
           <div className="ButonG"><button className="ButonGenereaza" type="submit">Genereaza</button></div>
         
           
        </div>
    );
}

export default Form;

