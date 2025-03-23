# Book MicroServices Documentation

This is Demo Book Application, Just a Use of MicroServices architecture,  we Use seperate Esecurity with Keycloak for Authentication and **Role** and **User** Based Authorization and also with Gateway.  

# Backend

## Book 

This is simple Service to fetch the Data. Demo Services.  

## Esecurity

This is security Services intergrated with Keycloak, and dynamic authorization get form the database and dynamic keycloak configuration using Token.

### Keycloak 

Use keycloak Admin Version **25.0.5**

**Angular Package:**   
npm install keycloak-js@25.0.5 --force   
npm i keycloak-angular@14.4.0

---

**Create Realm and Client Frontend Flow :**  

**In Capability config (Authentication Code & Password Flow Config)**

Client authentication need to be **OFF**

Check the **Standard flow** and **Direct access grants** only 

---

**In Access settings :**

Root URL : http://localhost:4200/  

Home URL : http://localhost:4200/home  

Valid redirect URIs : http://localhost:4200/*  

Valid post logout redirect URIs : http://localhost:4200/logout  

Web origins : http://localhost:4200  

Admin URL  : http://localhost:4200/

---

**In Client Scopes (Inside the Client) :**

Make **Auth_Org_Id (This Custome Scope), basic, and web-origins** as **Default**,  **Other** are **Optional.**

---

**In Realm-settings :**  

Add Themes : This is in GitHub for Login.

---

**In POSTMan Request to get Token:**

grant_type:password  
client_id:dev-network  
username:ram  
password:12345  
scope:email

---
**Create Realm and Client Backend Flow :**  

**In Capability config (Client Credential Flow Config)**  

Client authentication need to be **ON**

Check the **Service accounts roles** only 

---

**In Access settings : It is not compulsory to Add**

Root URL : http://localhost:8085/  

Home URL : http://localhost:8085/  

Admin URL  : http://localhost:8085/

---

**In Client Scopes (Inside the Client) :**

Make **Auth_Org_Id (This Custome Scope), roles, and web-origins** as **Default**,  **Other** are **Optional.**  

---

**In Client Service Account Role :**  

Add this Roles Then only you can use Admin APIs

**(realm-management) manage-clients**

**(realm-management) manage-users**

**(realm-management) manage-realm**

---

**In POSTMan Request to get Token:**

grant_type:client_credentials
client_id:dev-network
client_secret:j6mfRsC1QTClu5cEv3XF78YMneQ4h1js

---

**Over All in Keycloak :**

**In Client Scopes:**  

Make **Auth_Org_Id (This Custome Scope), acr, basic, roles, and web-origins** as **Default**,  **Other** are **Optional.**  

----

**Create Client Scope :**

 * Click Create Client Scope 

 * Add Client Scope Name and make Type as Default or Optional
  
 * Go to Mapper select **Configuration a new mapper**

 * Select **Hardcoded claim**  

 * Add the Name, Token Client Name (which will show in Token)

 * Set the Claim Value Eg: (10101)
 
 * Claim JSON Type as String or Long or Integer
  
 * Add to ID token , Add to access token, Add to userinfo, Add to access token response and Add to token introspection need to be ON.

---

**Add Default Role To Users :**

 * Go Realm-settings

 * Click on Assign role
  
 * Assign a Role or Group.

 * If you want to cross check this Go to User -> Role Mapping -> Un Select the **Hide inherited roles**.

---

**Token Life Span :**

 * Go Realm-settings

 * (Token Life Span) Go Token, In **Access tokens**, you can choose the Access Token Lifespan.

 *   (Refresh Token Life Span) Go Session, In **SSO Session Settings**, you can choose the SSO Session Idle.  


## Gateway

Gatways this will be different for projects, So, we can make to call the Esecurity API and validate the Token and give Response to the USER.







