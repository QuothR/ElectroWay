import React, { useEffect, useState } from "react";
import Tabel from "./Tabel";
// import DateTabel from "./DateTabelPrimite.json";
import { Link } from "react-router-dom";
import Slider from "react-animated-slider";
import "react-animated-slider/build/horizontal.css";
import axios from "axios";
import { useHistory } from "react-router";
import { connect } from "react-redux";

function ElementeJosDrp(props) {
  const station2 = JSON.parse(sessionStorage.getItem("station2"));
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [DateLinieTabel, getReviews] = useState([]);

  useEffect(() => {
    console.log(station2.id);

    axios
      .get(`http://localhost:443/review/all/station/${station2.id}`, {
        headers: {
          Authorization: `Basic ${myToken}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        getReviews(Array.from(response.data));
        ///getDate(response.data);
        console.log(DateLinieTabel);
      });
  }, []);

  if (DateLinieTabel.length > 0) {
    return (
      <div className="ElementeJos">
        <Slider>
          {DateLinieTabel.map((val, key) => {
            return (
              <div className="Mijloc" key={key}>
                <Tabel
                  rating={val.rating / 2}
                  data={val.user.username}
                  statie={val.station.address}
                  text={val.textReview}
                />
              </div>
            );
          })}
        </Slider>
      </div>
    );
  } else return <p>Nu exista rating-uri pentru statie</p>;
}
const mapStateToProps = (state) => {
  return {
    user: state,
  };
};

export default connect(mapStateToProps)(ElementeJosDrp);
