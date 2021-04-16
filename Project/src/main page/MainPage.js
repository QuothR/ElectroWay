import React from 'react'
import Sidebar from './Similar components/Sidebar'
import './Similar components/MainPage.css'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Adm from './Content/Adm'
import HomeContent from './Content/HomeContent'
import Footer from './Similar components/Footer'
import HistoryContent from './Content/HistoryContent'
import AdmMasini from './Content/AdmMasini'
import Reviews from './Content/Reviews'
import AdmStContent from './Content/AdmStContent'
import TripPlanner from './Content/Trip Planner/TripPlanner'
import Statistici from './Content/Statistici'


function MainPage() {
    return (
        <div className="MainPage">
            <div className="ContentMainPage">
                <Sidebar />

                <Router>
                    <div className="divDreapta">
                        <Switch>
                            <Route path="/home/Adm-account" exact component={Adm} />
                            <Route path="/home" exact component={HomeContent} />
                            <Route path="/home/History" exact component={HistoryContent} />
                            <Route path="/home/Adm-cars" exact component={AdmMasini} />
                            <Route path="/home/Reviews" exact component={Reviews} />
                            <Route path="/home/Adm-station" exact component={AdmStContent} />
                            <Route path="/home/Trip-planner" exact component={TripPlanner} />
                            <Route path="/home/Stats" exact component={Statistici} />
                        </Switch>
                    </div>
                </Router>
            </div>
            <footer> <Footer /> </footer>
        </div>
    );
}

export default MainPage;