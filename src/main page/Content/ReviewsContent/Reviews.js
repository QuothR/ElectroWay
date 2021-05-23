import React, { useEffect, useState } from "react";
import "./Reviews.css";
// import ElementeJosStg from "./Components/ElementeJosStg";
// import ElementeJosDrp from "./Components/ElementeJosDrp";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import axios from "axios";
import { connect } from "react-redux";
import * as ReactBootStrap from "react-bootstrap";
import { GrAdd } from "react-icons/gr";
import { GrView } from "react-icons/gr";

function Reviews(props) {
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [statii, getStatii] = useState([]);
  const [allstatii, getAllStatii] = useState([]);

  sessionStorage.removeItem("station1");
  sessionStorage.removeItem("station2");

  useEffect(() => {
    axios
      .get("http://localhost:443/station", {
        headers: {
          Authorization: `Basic ${myToken}`,
        },
      })
      .then((response) => {
        getStatii(Array.from(response.data));
      });

    axios
      .get("http://localhost:443/station/all", {
        headers: {
          Authorization: `Basic ${myToken}`,
        },
      })
      .then((response) => {
        getAllStatii(Array.from(response.data));
      });
  }, []);

  let text1 = "";
  if (allstatii.length > 5) {
    text1 = "scrollForTabelPlati";
  } else {
    text1 = "";
  }

  let text2 = "";
  if (statii.length > 5) {
    text2 = "scrollForTabelPlati";
  } else {
    text2 = "";
  }

  const renderStation1 = (station, index) => {
    return (
      <tr key={index}>
        <td style={{ width: "100%" }}>{station.address}</td>
        <td>
          <GrAdd
            onClick={(e) => {
              sessionStorage.setItem("station1", JSON.stringify(station));
              history.push("/home/Reviews/add");
            }}
          />
        </td>
        {/* <td>
          <img className="edit-img" src={Edit}></img>
        </td> */}
      </tr>
    );
  };
  const renderStation2 = (station, index) => {
    return (
      <tr key={index}>
        <td style={{ width: "100%" }}>{station.address}</td>
        <td>
          <GrView
            onClick={(e) => {
              sessionStorage.setItem("station2", JSON.stringify(station));
              history.push("/home/Reviews/station");
            }}
          />
        </td>
        {/* <td>
          <img className="edit-img" src={Edit}></img>
        </td> */}
      </tr>
    );
  };

  const history = useHistory();
  const redirectionareReview = () => {
    history.push("/home/Reviews/station");
    window.location.reload();
  };
  const redirectionareAdd = () => {
    history.push("/home/Reviews/add");
    window.location.reload();
  };

  return (
    <div className="Reviews">
      <div className="ReviewsBox">
        <div className="Tabele">
          <p className="TabeleTitlu">Statii existente</p>
          <ReactBootStrap.Table striped bordered hover className={text1}>
            <thead>
              <tr>
                <th>Adresa</th>
              </tr>
            </thead>
            <tbody>{allstatii.map(renderStation1)}</tbody>
          </ReactBootStrap.Table>
          {/* <button
            className="btn-class"
            onClick={(e) => {
              e.preventDefault();
              redirectionareAdd();
            }}
          >
            Add review
          </button> */}
        </div>

        <div className="Tabele">
          <p className="TabeleTitlu">Statii proprii</p>
          <ReactBootStrap.Table striped bordered hover className={text2}>
            <thead>
              <tr>
                <th>Adresa</th>
              </tr>
            </thead>
            <tbody>{statii.map(renderStation2)}</tbody>
          </ReactBootStrap.Table>
          {/* <button
            className="btn-class"
            onClick={(e) => {
              e.preventDefault();
              redirectionareReview();
            }}
          >
            Vezi review
          </button> */}
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state,
  };
};

export default connect(mapStateToProps)(Reviews);
