import React from 'react'
import Sidebar from './Similar components/Sidebar'
import './Similar components/MainPage.css'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Adm from './Content/Adm'
import HomeContent from './Content/HomeContent'
import Footer from './Similar components/Footer'
function MainPage() {
    return (
        <div className="MainPage">
            <div className="ContentMainPage">
                <Sidebar />

                <Router>
                    <div>
                        <Switch>
                            <Route path="/home/Adm-account" exact component={Adm} />
                            <Route path="/home" exact component={HomeContent} />



                        </Switch>
                    </div>
                </Router>
            </div>
            <footer> <Footer /> </footer>
        </div>
    );
}

export default MainPage;