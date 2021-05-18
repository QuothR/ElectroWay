import React from 'react'
import MyTranslator from '../../../MyTranslator.js'
import ReactFlagsSelect from 'react-flags-select';

class Form extends React.Component {
    constructor(props) {
        super(props);
        this.state = {selected: MyTranslator.staticProperty};
    }

    setSelected = (code) => {
        this.setState({
          selected: code
    });
    
    MyTranslator.staticProperty = code;
    }
     
    render () {
    const translate = new MyTranslator("formTripPlanner");
    return(

        <div className="Form"> 
        <ReactFlagsSelect
              selected={this.state.selected}
              onSelect={code => this.setSelected(code)}
              countries={["GB", "RO"]}
              showSelectedLabel={false}
              showOptionLabel={false}
              fullWidth={false}
            />
           <p className="Scris_X">{translate.useTranslation("pctPornire")}</p>
           <label><input type="text" id="destinatia_x" name="destinatia_x" placeholder="Locația_X"/></label>
           <p className="Scris_Y">{translate.useTranslation("destinatie")}</p>
           <label><input type="text" id="destinatia_x" name="destinatia_x" placeholder="Locația_Y" /></label>
           <div className="ButonG"><button className="ButonGenereaza" type="submit">{translate.useTranslation("genereaza")}</button></div>
         
           
        </div>
    );
}
}
export default Form;

