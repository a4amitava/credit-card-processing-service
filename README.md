# credit-card-processing-service:

**Technology Stack:**  Java11, Springboot, Lombok, In-memory H2 DataBase, JPA-Hibernate, javax Validation framework, RestTemplate for Api, maven, and Junit 5 as testing framework

 
**Service description:**
This service provides 2 rest endpoints(json formatted)- 

1._ Adding New Card🥇 

 - URI: POST http://localhost:8080/api/cards  
- This uri is to create a new credit card for a given name, card number, and limit
- Card numbers has been validated using Luhn 10, A validation logic has been written and a custom annotation has been cerated to reuse the validation logic. 
- Appropriate error/exception/validation model has been followed.
- All the validation logic has been respected and verified through junit-5
- Coding has been follwed SOLID principles
- A manifest file has been created for the deployment to cloud foundary's container based PaaS platform. This can be modified to any other cloud/contanier based platform.   

Sample Payload : Below are sample payload, but please refer the scratches file under resource folder to test the endpoints 


POST http://localhost:8080/api/cards
Content-Type: application/json
Authorization: Basic admin password

{
  "printableName": "CardHolder Name", "cardNumber" : "4847352989263094", "cardLimit" : 10
}



2. **Fetching All the Card Details ** 🥇 🥇 
  - URI: GET http://localhost:8080/api/cards 
	Sample Response: 
	[
  {
    "cardNumber": 4847352989263094,
    "printableName": "CardHolder Name",
    "cardLimit": 10.00,
    "balance": 0.00
  }
]



*** **Junit Coverage:**
Junit 5 has used to test and verify the logic. For Api/controller testing RestTemplate framework has been used endpoint

![image](https://user-images.githubusercontent.com/27267623/160946480-75202771-f02e-492e-a29e-6de8488f9804.png)


**Security :**  Spring security has been added to protect the API endpints. Spring PreAutorize annotation will ensure that only the Athurized User Role can access the APIs, for example, adding card is protected by authenticated ADMIN ROLE, and Any authenticated USER role can access to retrive all card details. The Users are configured in the property, and Inmemory AuthBuilder used for this. However, real production will have 3rd patry authenticator like OpenId with OAuth2 , or Ldap based Kerberos Authentication. 
Note: To disable the security below property to update- 
spring.security.enabled: false 

**DataBase Configuration:** (Property based)
 - DB console URL: http://localhost:8080/h2-console/
 - JDBC URL: jdbc:h2:mem:carddb
 - Password: Currently No password has added. 

**Actuator:** Health endpoint has been enabled to check whether the application is up/down
- http://localhost:8080/actuator/ 
- http://localhost:8080/actuator/health 

*** **Deployment Strategy:** a manifest file has been created to deploy the artifact on cloud foundary's contanier based PAsS platofrom. A cmd file also provided to use the manifest for the deployment. This can be further enchanded to deploy on any cloud/contrainer platform.    

