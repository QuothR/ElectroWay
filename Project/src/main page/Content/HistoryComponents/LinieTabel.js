

 const LinieTabel = (props) => (
    <div className="linieTabel">
        <div className="valoareLinie">{props.data}</div>
        <div className="valoareLinie">{props.destinatar}</div>
        <div className="valoareLinie">{props.trimitator}</div>
        <div className="valoareLinie">{props.status}</div>
        <div className="valoareLinie2">{props.sumaInt}.{props.sumaFloat}</div>
        <div className="valoareLinie2">{props.cod}</div>
        <div className="btnDownload">(download)</div>

    </div>
);

export default LinieTabel;