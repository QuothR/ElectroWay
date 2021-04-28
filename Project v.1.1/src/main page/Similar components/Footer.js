import React from 'react'
import iFacebook from '../../Images/iFacebook.svg'
import iInstagram from '../../Images/iInstagram.svg'
import iYouTube from '../../Images/iYouTube.svg'
import iGitHub from '../../Images/iGitHub.svg'
import './MainPage.css'
import 'bootstrap/dist/css/bootstrap.min.css'


function Footer(){
    return(
        <div className="foot mx-5"> 
                <div className="FooterIcons">
                    <a href="https://github.com/QuothR/ElectroWay"><img className="Icon img-fluid" src={iGitHub} alt="Icon" /> </a>
                </div>
                <div className="FooterIcons">
                    <a href="https://www.youtube.com/"><img className="Icon img-fluid" src={iYouTube} alt="Icon" /> </a>
                </div>
                <div className="FooterIcons">
                    <a href="https://www.facebook.com/"><img className="Icon img-fluid" src={iFacebook} alt="Icon" /> </a>
                </div>
                <div className = "FooterIcons">
                <a href="https://www.instagram.com/"><img className="Icon img-fluid" src={iInstagram} alt="Icon" /> </a>
                </div>           
            </div>
    
    );
}

export default Footer;