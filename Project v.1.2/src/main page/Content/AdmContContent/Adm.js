import React from 'react'
import './adm.css'
function Adm() {
    return (

        <div className="admin">
            <h2>Administrate your account</h2>
            <div className="change-forms">
                <div className="user-name">
                    <form method="post" className="form-administrate">
                        <div className="form-in">
                            <label htmlFor="username" required>Username</label>
                            <input type="text" name="username" id="username" placeholder="username" />
                        </div>
                        <div className="form-in" >
                            <label htmlFor="password">Email</label>
                            <input type="password" name="password" id="password" placeholder="user@ymail.com" />
                        </div>
                        <div className="change">
                            <input type="submit" value="Submit" />
                        </div>
                    </form>
                </div>

                <div class="password">
                    <form method="post" className="form-administrate">
                        <div className="form-in">
                            <label htmlFor="username" required>Old password</label>
                            <input type="text" name="username" id="oldPassword" placeholder="old password" />
                        </div>
                        <div className="form-in">
                            <label htmlFor="password">New password</label>
                            <input type="password" name="password" id="newPassword" placeholder="new password" />
                        </div>
                        <div className="change">
                            <input type="submit" value="Save" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

    );

}
export default Adm;
