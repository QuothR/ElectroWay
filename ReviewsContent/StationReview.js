import React from "react";
import "./Reviews.css";
import ElementeReview from "./Components/ElementeReview";
import { Link } from "react-router-dom";

function Reviews() {
  return (
    <div className="Reviews">
      <div className="ReviewsBox">
        <div className="Primite">
          <p className="TabeleTitlu">Review-uri primite</p>
          <ElementeReview />
        </div>
      </div>
    </div>
  );
}

export default Reviews;
