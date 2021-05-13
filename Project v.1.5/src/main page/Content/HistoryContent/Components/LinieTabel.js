import downloadInvoice from './pay'
import { IoMdDownload } from "react-icons/io";

function LinieTabel(props) {

    return (
        <div className="linieTabel">
            <div className="valoareLinie">{props.data}</div>
            <div className="valoareLinie">{props.destinatar}</div>
            <div className="valoareLinie">{props.trimitator}</div>
            <div className="valoareLinie">{props.status}</div>
            <div className="valoareLinie2">{props.sumaInt}.{props.sumaFloat}</div>
            <div className="valoareLinie2">{props.cod}</div>
            <IoMdDownload className="btnDownload" onClick={() => downloadInvoice(props.data, props.destinatar, props.sumaInt, props.cod)}></IoMdDownload>

        </div>
    );
}

export default LinieTabel;