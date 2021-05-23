import React, { useState } from "react";
import carPicture from "./Electric-car.svg";
import logo from "./logo3.png";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import "./login-register.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { connect } from "react-redux";
import { LoginAuthAction } from "../redux/actions/LoginAction";
import axios from "axios";
import { RO } from "../locales/ro/roTran"
import { ENG } from "../locales/en/engTran"

function ResetPassword(props) {
  const { user, login } = props;
  const [userState, setUser] = useState({});
  const history = useHistory();
  const [errorHandler, setErrorHandler] = useState({
    message: "",
  });

  const language = sessionStorage.getItem('language') ? sessionStorage.getItem('language') : 1
  let languageText = language === 1 ? ENG : RO;

  function handleAddEmail() {
    console.log(userState.email);
    axios
      .post(`http://localhost:443/forgot_password?email=${userState.email}`)
      .then((res) => {
        console.log(res.data);
      });
  }

  function handleChange() {
    console.log(userState);
    axios
      .post(
        `http://localhost:443/reset_password?token=${userState.token}&password=${userState.password}`
      )
      .then((res) => {
        console.log(res.data);
      });

    setTimeout(() => {
      history.push(`/login`);
    }, 50)

  }


  return (
    <div className="login-register">
      <div className="container">
        <div className="row content">
          <div className="col-md-6 mb-3">
            <img src={carPicture} className="img-fluid img" alt="carP" />
          </div>
          <div className="col-md6 mx-5 sigin-col" id="signin">
            <h3 className="sigin-text">
              {" "}
              <img src={logo} className="img-fluid logo" alt="logo" /> Reset
              Password{" "}
            </h3>

            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault();
                handleAddEmail();
                ///login(userState, history, setErrorHandler);
              }}
            >
              <div className="form-group">
                <label htmlFor="email">Email</label>
                <input
                  type="email"
                  className="form-control"
                  placeholder="name@example.com"
                  required
                  onChange={(e) => {
                    const email = e.target.value;
                    setUser({ ...userState, ...{ email } });
                    setErrorHandler("");
                  }}
                />
              </div>
              <button type="submit" className="btn btn-class1">
                Get token
                </button>
              <div className="error-response">
                <p>{errorHandler.message}</p>
              </div>
            </form>

            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault();
                handleChange();
                // login(userState, history, setErrorHandler);
              }}
            >
              <div className="form-group">
                <label htmlFor="token">TOKEN</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter reset token"
                  required
                  onChange={(e) => {
                    const token = e.target.value;
                    setUser({ ...userState, ...{ token } });
                    setErrorHandler("");
                  }}
                />
              </div>
              <div className="form-group">
                <label htmlFor="password">Password</label>
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  placeholder="Enter new password"
                  required
                  onChange={(e) => {
                    const password = e.target.value;
                    setUser({ ...userState, ...{ password } });
                    setErrorHandler("");
                  }}
                />
              </div>
              <div className="form-group">
                <p>
                  Don't have an account?
                  <Link to="./register"> Register here.</Link>
                </p>

              </div>


              <div className=" form-group-bottom">
                <button type="submit" className="btn btn-class">
                  Change
                </button>
                <div className="error-response">
                  <p>{errorHandler.message}</p>
                </div>
              </div>
            </form>
          </div>
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

const mapDispatchToProps = (dispatch) => {
  return {
    login: (userState, history, setErrorHandler) => {
      dispatch(LoginAuthAction(userState, history, setErrorHandler));
    },
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ResetPassword);
