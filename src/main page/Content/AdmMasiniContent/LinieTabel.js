import React from 'react'


const LinieTabel = (props) => (

    <div className="LinieTabel">
        <ul>
            <li>{props.model}</li>
            <li>{props.year}</li>
            <li>{props.batteryCapacity}</li>
            <li>{props.vehicleMaxSpeed}</li>             
        </ul>
    </div>

);

export default LinieTabel;
