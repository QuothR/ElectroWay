import React, { useEffect, useState } from "react";
import "./adm.css";
import axios from "axios";
import { useHistory } from "react-router";

import { connect } from "react-redux";
// import DateCont from "./DateCont.json";

function Adm(props) {
  const { user } = props;
  const myToken = user.loginReducer.user.token;
  //const [dateCont, getDate] = useState("");
  var [formImput, setFormInput] = useState({});

  function handleAdd() {
    const dataBackend = {
      id: formImput.id,
      username: formImput.username,
      password: formImput.password,
      firstName: formImput.firstName,
      lastName: formImput.lastName,
      phoneNumber: formImput.phoneNumber,
      emailAddress: formImput.emailAddress,
      address1: formImput.address1,
      address2: formImput.address2,
      city: formImput.city,
      region: formImput.region,
      country: formImput.country,
      zipcode: formImput.zipcode
    };
    console.log(dataBackend);
    axios
      .put("/user", dataBackend, {
        headers: {
          'Authorization': `Bearer ${myToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
      });
  }


  function handleWorkflow() {
    handleAdd();

  }

  useEffect(() => {
    axios
      .get("/user", {
        headers: {
          'Authorization': `Bearer ${myToken}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        ///getDate(response.data);
        setFormInput(response.data);
      });
  }, []);

  function handleSetRoles() {
    const body = {}

    // add role owner
    axios
      .post("/user/addrole?roleName=ROLE_OWNER", body, {
        headers: {
          'Authorization': `Bearer ${myToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
      });

      axios
      .post("/user/addrole?roleName=ROLE_DRIVER", body, {
        headers: {
          'Authorization': `Bearer ${myToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
      });


  }
  return (
    <div className="admin">
      <h3>Administrate your account</h3>
      <form
        className="change-forms"
        onSubmit={(e) => {
          e.preventDefault();
          handleWorkflow();
        }}
      >
        <div className="form-administrate">
          <div className="form-in">
            <label htmlFor="username" required>
              Username
            </label>
            <input
              className="inputDate"
              type="text"
              name="username"
              id="username"
              defaultValue={formImput.username}
              required
              onChange={(e) => {
                const username = e.target.value;
                setFormInput({ ...formImput, ...{ username } });
              }}
            />
          </div>
          <div className="form-in">
            <label htmlFor="email">Email</label>
            <input
              className="inputDate"
              type="text"
              name="email"
              id="email"
              defaultValue={formImput.emailAddress}
              required
              onChange={(e) => {
                const emailAddress = e.target.value;
                setFormInput({ ...formImput, ...{ emailAddress } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="nume">Nume</label>
            <input
              className="inputDate"
              type="text"
              name="nume"
              id="nume"
              defaultValue={formImput.lastName}
              required
              onChange={(e) => {
                const lastName = e.target.value;
                setFormInput({ ...formImput, ...{ lastName } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="prenume">Prenume</label>
            <input
              className="inputDate"
              type="text"
              name="prenume"
              id="prenume"
              defaultValue={formImput.firstName}
              required
              onChange={(e) => {
                const firstName = e.target.value;
                setFormInput({ ...formImput, ...{ firstName } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="numarDeTelefon">Numar de telefon</label>
            <input
              className="inputDate"
              type="text"
              name="telefon"
              id="telefon"
              defaultValue={formImput.phoneNumber}
              required
              onChange={(e) => {
                const phoneNumber = e.target.value;
                setFormInput({ ...formImput, ...{ phoneNumber } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="codPostal">Cod postal</label>
            <input
              className="inputDate"
              type="text"
              name="codPostal"
              id="codPostal"
              defaultValue={formImput.zipcode}
              required
              onChange={(e) => {
                const zipcode = e.target.value;
                setFormInput({ ...formImput, ...{ zipcode } });
              }}
            />
          </div>

        </div>

        <div className="form-administrate">
          <div className="form-in">
            <label htmlFor="tara">Tara</label>
            <input
              className="inputDate"
              type="text"
              name="tara"
              id="tara"
              defaultValue={formImput.country}
              required
              onChange={(e) => {
                const country = e.target.value;
                setFormInput({ ...formImput, ...{ country } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="regiune">Regiune</label>
            <input
              className="inputDate"
              type="text"
              name="regiune"
              id="regiune"
              defaultValue={formImput.region}
              onChange={(e) => {
                const region = e.target.value;
                setFormInput({ ...formImput, ...{ region } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="oras">Oras</label>
            <input
              className="inputDate"
              type="text"
              name="oras"
              id="oras"
              defaultValue={formImput.city}
              required
              onChange={(e) => {
                const city = e.target.value;
                setFormInput({ ...formImput, ...{ city } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="adresa1">Adresa 1</label>
            <input
              className="inputDate"
              type="text"
              name="ad1"
              id="ad1"
              defaultValue={formImput.address1}
              required
              onChange={(e) => {
                const address1 = e.target.value;
                setFormInput({ ...formImput, ...{ address1 } });
              }}
            />
          </div>

          <div className="form-in">
            <label htmlFor="adresa2">Adresa 2</label>
            <input
              className="inputDate"
              type="text"
              name="ad2"
              id="ad2"
              defaultValue={formImput.address2}
              onChange={(e) => {
                const address2 = e.target.value;
                setFormInput({ ...formImput, ...{ address2 } });
              }}
            />
          </div>


          <div className="change">
            <button className="rolesBtn" onClick={(e) => {
              e.preventDefault();
              handleSetRoles();
            }}>Get roles</button>
          </div>

          <div className="change">
            <input type="submit" defaultValue="Save" />
          </div>
        </div>
      </form>
    </div>
  );
}
const mapStateToProps = (state) => {
  return {
    user: state,
  };
};

export default connect(mapStateToProps)(Adm);
