import React, { useState, useEffect } from 'react'
import './MainPage.css'
import { SidebarData } from './SidebarData'
import { Link, useHistory } from 'react-router-dom'
import Logo from '../../login-register/logo3.png'
import 'bootstrap/dist/css/bootstrap.min.css'
import { connect } from 'react-redux'
import { LogoutAuthAction } from '../../redux/actions/LoginAction'
import { ENG } from '../../locales/en/engTran'
import { RO } from "../../locales/ro/roTran"

function Sidebar(props) {
    const { user, logout } = props;
    const history = useHistory();
    const [language, setLanguage] = useState(sessionStorage.getItem('language') ? sessionStorage.getItem('language') : 1)

    let languageText = language == 1 ? ENG : RO
    useEffect(() => {
        languageText = language == 1 ? ENG : RO
        sessionStorage.setItem('language', language);
    });

    return (
        <div className="sidebar">
            <div className="row content">

                <div className="blockSelectpicker">
                    <select className="selectpicker" data-width="fit" defaultValue={language}
                        onChange={(e) => {
                            setLanguage(e.target.value);
                            setTimeout(() => {
                                window.location.reload();
                            }, 10)
                        }}>
                        <option value='1'>English</option>
                        <option value='0'>Romana</option>
                    </select>
                </div>


                <div className="col-md6 mx-5 my-2">
                    <Link to="./home">
                        <img src={Logo} className="img-fluid img" alt="LogoMainPage" onClick={() => { window.location.pathname = "/home" }} />
                    </Link>
                </div>
                <div className="col-md6 mx-5 mb-4">
                    <button className="btn sign-out-btn btn-class"
                        onClick={() => {
                            localStorage.removeItem("perioada");
                            logout(history);
                        }}>
                            Sign out
                        
                        {/* 
                        asa ar trebui scris pentru fiecare text: {text.sidebarBtn.signOut} 
                        butoanele de "sign in" si "sign out" raman identice pentru ro si en
                        */}
                    </button>
                </div>
                {SidebarData.map((val, key) => {


                    // a fost nevoie de switch doar in componenta asta pentru ca butoanele
                    // au fost implementate cu un mapping in loc sa fie scrise toate de mana
                    const test = () => {
                        switch (val.title) {
                            case "admAccount":
                                return languageText.sidebarBtn.admAccount
                                break;
                            case "admCars":
                                return languageText.sidebarBtn.admCars
                                break;
                            case "admStations":
                                return languageText.sidebarBtn.admStations
                                break;
                            case "history":
                                return languageText.sidebarBtn.history
                                break;
                            case "tripPlanner":
                                return languageText.sidebarBtn.tripPlanner
                                break;
                            case "reviews":
                                return languageText.sidebarBtn.reviews
                                break;
                            case "statistics":
                                return languageText.sidebarBtn.statistics
                                break;

                            default:
                                break;
                        }
                    }
                    const value = test();
                    return (
                        <div className="col-md6 mx-5 my-2 ActiveSidebar"
                            key={key}
                            id={window.location.pathname === val.link ? "active" : ""}
                            onClick={() => { window.location.pathname = val.link }}>
                            <button className="btn btn-class">{
                                value
                            }

                            </button>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}
const mapStateToProps = (state) => {
    return {
        user: state,
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        logout: (history) => {
            dispatch(LogoutAuthAction(history));
        },
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Sidebar);