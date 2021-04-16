import react from 'react'
import './HistoryComponents/HistoryContent.css'
import TabelPlati from './HistoryComponents/TabelPlati'

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