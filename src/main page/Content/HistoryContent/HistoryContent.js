import React from 'react'
import './HistoryContent.css'
import TabelPlati from './Components/TabelPlati'
import MyTranslator from '../../../MyTranslator.js'
import ReactFlagsSelect from 'react-flags-select';

function HistoryContent() {

    return (
        <div className="historyContent">
            <div className="blockCenter">
                <TabelPlati />
            </div>
        </div>
    );

}

export default HistoryContent;