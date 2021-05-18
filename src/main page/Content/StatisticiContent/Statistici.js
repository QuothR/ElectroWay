import React from 'react'
import './Statistici.css'
import Tabel from './Components/BartChart'
import showBar from './Components/connect'
import { useState } from 'react';
import MyTranslator from '../../../MyTranslator.js'
import ReactFlagsSelect from 'react-flags-select';


function onInputChangedMod(e) {
    localStorage.setItem("perioada", e.target.value);
   window.location.reload(false);

    return;
    console.log(e.target.value);
    //return e.target.value;
   if(e.target.value==="saptamanal")
   {
       console.log('apelez');
    return showBar("saptamanal");

   }
   else
   if(e.target.value==="anual")
   return showBar('anual');
   else
   if(e.target.value==='lunar')
   return showBar('lunar');
   else
   return showBar('lunar');
   //showSpecificBar(e.target.value);
  };
  function onSumChanged(e) {
   // return (e.target.value);
  }

  function showSpecificBar(changedValue){
      
    //   let changeValue='anual';
    //   let changeValue=onInputChangedMod;
     // let varModChange=onInputChangedMod;
     // let VarSumChange=onSumChanged();
    //   if(varModChange==='saptamanal'){
    //     console.log("saptamanl");  
    //     changeValue=1;
    //   }
    //       else
    //       if(varModChange==='anual')
    //       changeValue=2;
    //       else
    //       changeValue=3;
     switch(changedValue){
         case 'saptamanal':
             return showBar('saptamanal');
             break;
        case 'anual':
              return showBar('anual');
              break;
         default:
             console.log('lunaar');
             return showBar('lunar');     

     }
  }


  class Statistici extends React.Component {
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
    const translate = new MyTranslator("statistici");
//    var state = {
//         reload: false
//       };
//    var refreshPage = () => {
//         this.setState(
//           {reload: true},
//           () => this.setState({reload: false})
//         )}
    if(localStorage.getItem("perioada") === null) {
        localStorage.setItem("perioada", "anual");
    }
    return (
        <div id="statistici-Id" className="Statistici">
            <div className="StatisticiBox">
            <ReactFlagsSelect
              selected={this.state.selected}
              onSelect={code => this.setSelected(code)}
              countries={["GB", "RO"]}
              showSelectedLabel={false}
              showOptionLabel={false}
              fullWidth={false}
            />
                <div className="StatisticiButtons">

                    <div className="StatisticiButtons-unu">
                        <input list="perioada" className="input-field" placeholder={localStorage.getItem("perioada")} onChange={onInputChangedMod}/>                    
                        <datalist id="perioada">
                            <option value="saptamanal">{translate.useTranslation("sapt")}</option>
                            <option value="lunar">{translate.useTranslation("lunar")}</option>
                            <option value="anual">{translate.useTranslation("anual")}</option>
                            </datalist>
                    </div>

                    <div className="StatisticiButtons-doi">
                        <input list="sume" className="input-field" placeholder="sume" onChange={onSumChanged}/>
                        <datalist id="sume">
                            <option value="incasate">{translate.useTranslation("sapt")}</option>
                            <option value="platite">{translate.useTranslation("anual")}</option>
                        </datalist>
                    </div>

                </div>
                <div className="StatisticiGrafic">
                 {showSpecificBar(localStorage.getItem("perioada"))}
                </div>
            </div>

        </div>
        
    );
    }
}
export default Statistici