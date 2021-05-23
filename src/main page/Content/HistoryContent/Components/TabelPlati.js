import LinieTabel from './LinieTabel'
import IstoricInfo from './IstoricInfo.json'


function TabelPlati() {
    let text = "";
    if(IstoricInfo.length > 6){
        text = "tabelPlati scrollForTabelPlati";
    }
    else{
        text = "tabelPlati";
    }


    return (
        <div className={text}>
            <div className="linieTitlu">
                <div className="blockTitlu">Data</div>
                <div className="blockTitlu">Destinatar</div>
                <div className="blockTitlu">Trimitator</div>
                <div className="blockTitlu">Status</div>
                <div className="blockTitlu2">Suma (RON)</div>
                <div className="blockTitlu2">Cod</div>
                <div className="btnDownload2"></div>
            </div>

            {IstoricInfo.map((val, key) => {
                return (
                        <LinieTabel key={key} 
                            data={val.data}
                            destinatar={val.destinatar}
                            trimitator={val.trimitator}
                            status={val.status}
                            sumaInt={val.sumaInt}
                            sumaFloat={val.sumaFloat}
                            cod={val.cod}
                        />

                );
            })}


        </div>
    );
}

export default TabelPlati;
