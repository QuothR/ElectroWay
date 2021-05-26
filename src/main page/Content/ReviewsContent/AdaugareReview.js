import React, { useState } from "react";
import "./Reviews.css";
import { connect } from "react-redux";
import axios from "axios";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import StarRating from "react-svg-star-rating";

function AddReviews(props) {
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  const [formImput, setFormInput] = useState({});
  const [rating4, setRating4] = useState(0);
  const station1 = JSON.parse(sessionStorage.getItem("station1"));
  const history = useHistory();

  const handleOnClick4 = (rating) => {
    setRating4(rating * 2);
  };

  function handleAdd() {
    const dataBackend = {
      textReview: formImput.textReview,
      rating: rating4,
    };

    console.log(dataBackend);
    axios
      .post(
        `/review/create/station/${station1.id}`,
        dataBackend,
        {
          headers: {
            'Authorization': `Bearer ${myToken}`,
          },
        }
      )
      .then((res) => {
        console.log(res.data);
      });
  }

  function handleWorkflow() {
    handleAdd();
    setTimeout(() => {
      history.push(`/home/Reviews`);
    }, 200);
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
              <label>Rating: {rating4}</label>
              <StarRating isHalfRating={true} handleOnClick={handleOnClick4} />
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

            <div className="SubmitButtonRow">
              <Link to="/home/Reviews">
                <button>Renunta</button>
              </Link>
              <button type="submit">Submit</button>
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
