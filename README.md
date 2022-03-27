# credit-card-processing-service:

**Technology Stack:**  Springboot, In-memory H2 DataBase, JPA-Hibernate, javax Validation framework, RestTemplate for Api, maven, and Junit 5 as testing framework

**DataBase Configuration:** (Property based)
 - DB console URL: http://localhost:8080/h2-console/
 - JDBC URL: jdbc:h2:mem:carddb
 - Password: Currently No password has added. 
 
**Service description:**
This service provides 2 rest endpoints- 

1._ Adding New Card🥇 

 - URI: POST http://localhost:8080/api/cards  
- This uri is to create a new credit card for a given name, card number, and limit
- Card numbers has been validated using Luhn 10, A validation logic has been written and a custom annotation has been cerated to reuse the validation logic. 
- Appropriate error/exception/validation model has been followed.
- All the validation logic has been respected and verified through junit-5 

Sample Payload : 
POST http://localhost:8080/api/cards
Content-Type: application/json

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

![Screenshot 2022-03-28 004152](https://user-images.githubusercontent.com/27267623/160306272-0ff2f2cf-ff61-48eb-af66-b0d4c3ebb9ca.png)


*** **Deployment Strategy:** a manifest file has been created to deploy the artifact on cloud foundary's contanier based PAsS platofrom. A cmd file also provided to use the manifest for the deployment. This can be further enchanded to deploy on any cloud/contrainer platform.    

