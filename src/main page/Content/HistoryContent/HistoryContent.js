  
import react from 'react'
import './HistoryContent.css'
import TabelPlati from './Components/TabelPlati'

function HistoryContent() {
    return (
        <div className="historyContent">
            <div className="blockCenter">
                <div className="title">
                    Istoric plati
               </div>
                <TabelPlati />
            </div>
        </div>
    );
}

export default HistoryContent;