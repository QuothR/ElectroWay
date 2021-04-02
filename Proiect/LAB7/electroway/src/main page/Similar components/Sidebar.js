import React from 'react'
import './MainPage.css'
import { SidebarData } from './SidebarData'
import { Link } from 'react-router-dom'
import Logo from '../../login-register/logo3.png'
import 'bootstrap/dist/css/bootstrap.min.css'

function Sidebar() {
    return (
        <div className="sidebar">
            <div className="row content">
                <div className="col-md6 mx-5 my-2">
                    <Link to="./home">
                        <img src={Logo} className="img-fluid img" alt="LogoMainPage" onClick={() => { window.location.pathname = "/home" }} />
                    </Link>
                </div>
                <div className="col-md6 mx-5 mb-4">
                    <button className="btn sign-out-btn btn-class" onClick={() => { window.location.pathname = "/login" }}>
                        Sign out
                    </button>
                </div>
                {SidebarData.map((val, key) => {
                    return (
                        <div className="col-md6 mx-5 my-2 ActiveSidebar"
                            key={key}
                            id={window.location.pathname === val.link ? "active" : ""}
                            onClick={() => { window.location.pathname = val.link }}>
                            <button className="btn btn-class">{val.title}</button>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default Sidebar;