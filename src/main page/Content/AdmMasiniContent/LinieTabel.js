import React from 'react'


const LinieTabel = (props) => (

    <div className="LinieTabel">
        <ul>
            <li>{props.producator}</li>
            <li>{props.model}</li>
            <li>{props.consum}</li>
            <li>{props.distanta}</li>             
        </ul>
    </div>

);

export default LinieTabel;
