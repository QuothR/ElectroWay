import react from 'react'
import { Route, Redirect } from 'react-router-dom'


function ProtectedLoginRoute({ isAuth: isAuth, component: Component, ...rest }) {
    return(
        <Route {...rest} render={(props) => {
            if(!isAuth) {
                return <Component />
            } else{
                return (
                    <Redirect to={{ pathname:"/home", state: {from: props.location}}} />
                );
            }

        } 
    } />
    );
}


export default ProtectedLoginRoute;