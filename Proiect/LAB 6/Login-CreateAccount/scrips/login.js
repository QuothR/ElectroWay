
document.addEventListener("DOMContentLoaded", () =>{
    const siginForm = document.querySelector("#signin");
    const signupForm = document.querySelector("#signup");

    document.querySelector("#signupLink").addEventListener("click", e => {
        e.preventDefault();
        siginForm.classList.add("form-hidden");
        signupForm.classList.remove("form-hidden");
    });

    document.querySelector("#signinLink").addEventListener("click", e => {
        e.preventDefault();
        signupForm.classList.add("form-hidden");
        siginForm.classList.remove("form-hidden");
    });
});