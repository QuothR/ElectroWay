
import React from 'react'
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
import Locations from './Locations.json'




function Mapp() {

  const position = [47.151726, 27.587914]
  return (
    <div className="TripBox">
      <MapContainer center={[47.151726, 27.587914]} zoom={13} scrollWheelZoom={true}>
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {Locations.map(loc => (
            <Marker 
            
            key = {loc.id}
            position={[loc.gps.latitude,loc.gps.longitude]}
            >
                
                <Popup>
                    {loc.name}
                </Popup>   
            </Marker>
  
        ))}
        </MapContainer>
    </div>
  );
}

export default Mapp;