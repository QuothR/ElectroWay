import React, { useState } from "react";
import "./Reviews.css";
import { connect } from "react-redux";
import axios from "axios";
// import { Link } from "react-router-dom";
import { useHistory } from "react-router";

function AddReviews(props) {
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [formImput, setFormInput] = useState({});

  const station1 = JSON.parse(sessionStorage.getItem("station1"));

  const history = useHistory();
  const redirectionare = () => {
    history.push("/home/Reviews");
    window.location.reload();
  };

  function handleAdd() {
    const dataBackend = {
      textReview: formImput.textReview,
      rating: formImput.rating,
    };

    console.log(dataBackend);
    axios
      .post(
        `http://localhost:443/review/create/station/${station1.id}`,
        dataBackend,
        {
          headers: {
            Authorization: `Basic ${myToken}`,
          },
        }
      )
      .then((res) => {
        console.log(res.data);
      });
  }

  function refreshPage() {
    window.location.reload();
  }

  function handleWorkflow() {
    // if (
    //   formImput.textReview != null &&
    //   formImput.rating > 0 &&
    //   formImput.rating < 11
    // ) {
    handleAdd();
    //redirectionare();
    // refreshPage();
    // } else {
    //   return <h1>null</h1>;
    // }
  }

  return (
    <div className="Reviews">
      <div className="ReviewsBox">
        <form
          className="change-forms"
          onSubmit={(e) => {
            e.preventDefault();
            handleWorkflow();
          }}
        >
          <div className="form-administrate">
            <h3>Add review</h3>
            <div className="form-in">
              <label>Rating</label>
              <input
                type="text"
                id="rating"
                placeholder="introdu rating"
                required
                onChange={(e) => {
                  const rating = e.target.value;
                  setFormInput({ ...formImput, ...{ rating } });
                }}
              />
            </div>
            <div className="form-in">
              <label>Text Review</label>
              <textarea
                type="text"
                id="textReview"
                rows="4"
                cols="50"
                placeholder="introdu text Review"
                required
                onChange={(e) => {
                  const textReview = e.target.value;
                  setFormInput({ ...formImput, ...{ textReview } });
                }}
              />{" "}
            </div>

            <div className="change">
              <input type="submit" defaultValue="Save" />
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state,
  };
};

export default connect(mapStateToProps)(AddReviews);
