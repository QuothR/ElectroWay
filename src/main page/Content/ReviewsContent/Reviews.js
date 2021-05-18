import React from "react";
import "./Reviews.css";
import ElementeJosStg from "./Components/ElementeJosStg";
import ElementeJosDrp from "./Components/ElementeJosDrp";

function Reviews() {
  return (
    <div className="Reviews">
      <div className="ReviewsBox">
        <div className="Lasate">
          <p className="TabeleTitlu">Review-uri lasate</p>
          <ElementeJosStg />
        </div>
        <div className="Primite">
          <p className="TabeleTitlu">Review-uri primite</p>
          <ElementeJosDrp />
        </div>
      </div>
    </div>
  );
}

export default Reviews;
