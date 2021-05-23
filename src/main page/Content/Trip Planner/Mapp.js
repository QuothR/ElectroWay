import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet'

import axios from "axios";
import { connect } from "react-redux";
import { testObj } from './test'


function Mapp(props) {

  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [statii, getStatii] = useState([]);
  const [route, setRoute] = useState(props.ruta);
  const [, rerender] = useState({});
  
  useEffect (() => {
    setRoute(props.ruta)
  },[props.ruta])


  // const testT = () => {
  //   let vector = [];
  //   testObj.legs.map((val, key) => {
  //     console.log(val.points.length);
  //     val.points.map((val1, key1) => {
  //       vector.push(val1);
  //     })
  //   })
  //   return vector;
  // }

  useEffect(() => {
    axios
      .get("http://localhost:443/station/all", {
        headers: {
          Authorization: `Basic ${myToken}`,
        },
      })
      .then((response) => {
        getStatii(Array.from(response.data));
      });

  }, []);



  return (
    <div className="TripBox">
      <MapContainer center={[45.977008817321256, 24.77553248483639]} zoom={7.23} scrollWheelZoom={true}>
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {statii.map(loc => (
          <Marker

            key={loc.id}
            position={[loc.latitude, loc.longitude]}
          >

            <Popup>
              {loc.address}
            </Popup>
          </Marker>

        ))}




        {
          route.points.map((val, key) => {

            if (key < route.points.length - 1) {
              return (
                <Polyline key={key} positions={[
                  [route.points[key].lat, route.points[key].lon], [route.points[key + 1].lat, route.points[key + 1].lon],
                ]} color="blue" />
              );
            }
          })
        }




      </MapContainer>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state,
  };
};

export default connect(mapStateToProps)(Mapp);
