import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './styling.css'
import CarImage from '../main pictures/image1.png'
import Charging from '../main pictures/charging.png'
import History from '../main pictures/history.png'
import Pay from '../main pictures/credit-card.png'
import Compass from '../main pictures/compass.png'
import Feeling from '../main pictures/feeling.png'
import  Navigation from '../main pictures/navigation.png'
import Third from '../main pictures/Electric_car-rafiki.png'
import iFacebook from '../main pictures/iFacebook.svg'
import iYouTube from '../main pictures/iYouTube.svg'
import iGitHub from '../main pictures/iGitHub.svg'
import iInstagram from '../main pictures/iInstagram.svg'
import Logo from '../../login-register/logo3.png'
import './styling.css'
import Mapp from '../../main page/Content/Trip Planner/Mapp'
function MainContent() {
    return (
        <div className="page">
            <aside class="my-aside">
                <div class="asider">
            <div class="col-md6 mx-5 my-2">
                <a href="#"><img src={Logo} class="img-fluid img" alt="LogoMainPage"/></a></div>
         
                <button class="btn-class" onClick={() => { window.location.pathname = "/login" }}>Sing in</button>
                </div>
            </aside>

        <main className="main">
            <div className="intro">
                <section className="main_section">
                    <img id="image" src={CarImage} alt="imagecar" />
                    <h1 id="text-over-picture">Our goal is to charge your way</h1>
                </section>

            </div>
            <section className=" pinnes-no-pt pinnes-no-pb bg-purple">
                <div className="container">
                    <div className="row g-0 ">
                        <div className="col-md-12 service-wrap">
                            <div className="row g-3">
                                <div className="col-md-4 col-lg-2 text-center d-flex align-items-stretch">
                                    <div className="pinned">
                                        <div className="icon">
                                            <img className="icon-img" src={Charging} alt="charging" />
                                        </div>
                                        <div className="text">
                                            <h5>Fast charge</h5>

                                        </div>

                                    </div>

                                </div>

                                <div className="col-md-4 col-lg-2 text-center d-flex align-items-stretch">
                                    <div className="pinned">
                                        <div className="icon">
                                            <img className="icon-img" src={Pay} alt="credit" />
                                        </div>
                                        <div className="text">
                                            <h5>Easy to pay</h5>

                                        </div>

                                    </div>

                                </div>
                                <div className="col-md-4 col-lg-2 text-center d-flex align-items-stretch">
                                    <div className="pinned">
                                        <div className="icon">
                                            <img className="icon-img" src={History} alt="history" />
                                        </div>
                                        <div className="text">
                                            <h5>Save your trips</h5>

                                        </div>

                                    </div>

                                </div>
                                <div className="col-md-4 col-lg-2 text-center d-flex align-items-stretch">
                                    <div className="pinned">
                                        <div className="icon">
                                            <img className="icon-img" src={Compass} alt="compass" />
                                        </div>
                                        <div className="text">
                                            <h5>Navigation</h5>

                                        </div>

                                    </div>

                                </div>

                            </div>




                        </div>


                    </div>
                </div>

            </section>

            <section className="storys">
                <div className="combine">
                    <p className="combine-p">Have you ever run out of battery and didn't know where to charge your electric
                    car?
            Have you ever had problems with your car's battery level on long trips? </p>
                    <img className="combine-image" src={Feeling} alt="first" />
                </div>
                <div className="combine combine-list">
                    <p className="combine-p-right">We are here to help you! Our website will show you a map of the charging
                    stations and
                    our application will calculate the shortest distance to your destination, taking into account
            the charging stations on the road. </p>
                    <img className="combine-image-left" src={Navigation} alt="second" />
                </div>
                <div className="combine-final">
                    <p className="combine-p-third"> Enjoy the electric experince!
                    Find out how efficient is to travel with us!
                    An optimal way to travel without thinking
            you could run out of battery. </p>
                    <img className="combine-image-right-third" src={Third} alt="third" />
                </div>
            </section>
            <section>
                <div class="map-section">
               <div class="map-container">
                 
                 
               <Mapp/>
               </div>
               </div>
            </section>
       
            <footer className="footer-class">
                        <div className="footer-div">

                            <img className="icon-img" src={iFacebook} alt="facebook" />


                            <img className="icon-img" src={iInstagram} alt="instagram" />


                            <img className="icon-img" src={iYouTube} alt="youtube" />
                            <img className="icon-img" src={iGitHub} alt="github" />
                        </div>

                    </footer>
        </main>
</div>



    );
}
export default MainContent;