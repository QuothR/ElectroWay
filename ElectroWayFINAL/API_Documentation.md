  ## API Electroway Documentation 
  
  
   Acest API e folosit pentru a comunica cat mai eficient cu baza de date a aplicatiei Electroway , de a extrage informatiile necesare,  de a adauga sau de a realiza un update asupra lor . Toate cererile si raspunsurile catre API-ul Electroway vor fi in format JSON .
  Principalele functionalitati sunt :
  
1. **REGISTER**
2. **LOGIN**
3. **FORGOT PASSWORD**
4. **RESET PASSWORD**
5. **ADD STATION**
6. **MODIFY STATION**
7. **VIEW STATION**
8. **STATIONS**
9. **DELETE STATIONS**
10. **GET STATIONS** 
11. **GET CURRENT USER** 
12. **GET ALL USERS**
13. **MODIFY USER**
14. **ADD REVIEW**
15. **GET REVIEW STATION**
16. **ADD CAR**
17. **GET ALL CARS**
18. **ADD CHARGING PLUG**
19. **DELETE CHARGIN PLUG**
20. **GET CURRENT CHARGING PLUG**
21. **DELETE CHARGING PLUG**
22. **ADD CHARGING POINT**
23. **GET CURRENT CHARGING POINT**
24. **GET ALL CHARGING POINTS**
25. **GET CURRENT CHARGING POINT** 
26. **DELETE CHARGING POINT**


# REGISTER
   * E o metoda POST . 
   * E folosit pentru a inregistra utilizatorii .
   * URL : http://localhost:443/register
   * MODEL REQUEST : 
   ```json 
   {
    "username": "cxdsasasffdsvddsd",
    "password": "ds5safdsas!Aa1",
    "firstName": "vcxdsfasafsdsdda23dsdzx",
    "lastName": "xczdafsasfssdsxc",
    "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
    "emailAddress": "electroway@mailinator.com",
    "address1": "xzcdsdsaf324dssxzasdsac",
    "city": "cxzsafcx",
    "country": "Rdsafcxzcxz",
    "zipcode": "73sfad0",
    "roles": ["ROLE_DRIVER","ROLE_OWNER"]
}
```
* MODEL RESPONSE
```json
{
    "username": "cxdsasasffdsvddsd",
    "password": "$2a$10$OzgezooSaE4/v6vsMuAeC.It/RdaIPkIspfC3TI6gAk3VT6s1Vwq6",
    "firstName": "vcxdsfasafsdsdda23dsdzx",
    "lastName": null,
    "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
    "emailAddress": "electroway@mailinator.com",
    "address1": "xzcdsdsaf324dssxzasdsac",
    "city": "cxzsafcx",
    "country": "Rdsafcxzcxz",
    "zipcode": "73sfad0",
    "roles": [
        "ROLE_DRIVER",
        "ROLE_OWNER"
    ]
}
```
# LOGIN
 * E o metoda POST 
 * E folosit pentru autentificare , in urma careia se va obtine un token . Dupa ce ati inregistrat un cont pe platforma trebuie sa-l activati , activarea se face prin email .
 * URL : http://localhost:443/login
 * MODEL REQUEST 
 ```json
{
  "email" : "dsada@gmail.com",
  "password" :  "dasdasdadsade121"
}
  ```
 * MODEL RESPONSE
 ```json 
   {
      "token" : "12e23efsdfnsdunfsdfssfsdfsdfsdfsd"
   }
```
# FORGOT PASSWORD 
  * E o metoda POST
  * URL : http://localhost:443/forgot_password?email=email_user
  * Ca si request o sa avem niste parametrii : email 
  * Ca si reponse form-ul pentru forgot password 
  
# RESET PASSWORD 
   * E o metoda POST
   * Deobicei parola se reseteaza dupa ce am uitat-o sau pierdut-o , la forgot password user-ul va primi pe email-ul cu care si-a facut contul un email cu un token , acel token va fi folosit la resetarea parolei . 
   * URL :  http://localhost:443/reset_password?token=token_from_forgot
   * Parametrii : token-ul de la forgot si noua parola 
   * Response , deobicei un mesaj cu rezultatul procesului de resetare . 

# ADD STATION 
   * E o metoda POST 
   * URL : http://localhost:443/station
   * Model request : 
   ```json
   {
      "address" : "-",
      "latitude" : "-",
      "longitude" : "-"
   }
   ```
   * Model response:
   ``` json 
   ```
