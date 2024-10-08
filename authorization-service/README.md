# Authorization Service
## Overview
This service is used to authorize JWT token passed for the API endpoint it is invoking. It has one rest endpoint: POST `/v1/authorize`
For example a JWT token (passed as Bearer token in the Authorization header of a GET call to `/api/v1/accounts` endpoint) 
can be checked with a call to this service's `v1/authorize` POST endpoint.
Invoker of this endpoint must pass in the `token`, `requestedUri` and `requestedMethod` attribute of request payload (represented
by the class `AuthzRequest`). This endpoint responds with `isAuthorized` (true/false) and a `message` (represented by `AuthzResponse` class)
This microservice exposes a REST API to 1) view all user accounts and 2) create a user account.

## Data Model
![Data model](authz-datamodel.jpg)
Each `User` is linked to 0 or more `Group`s. Each `Group` is linked to
0 or more `Role`s. Each `Role` has 0 or more `Permission`s. `Permission` determines if a given URI and method can be allowed for the `User`.
Logic for checking permissions against requested URI and method is hardcoded in the code, but this can easily be moved to database.
For example URI patterns and allowed http methods (verbs) can be linked to `Permission`s via a linking table.

## REST Endpoints
### POST `v1/authorize`
This endpoint decrypts and unpacks the JWT token passed in as the `token` request body attribute, and validates if the `Subject`
of the JWT token is authorized to invoke the `requestUri` endpoint with the `requestedMethod` http verb. See above for the data model.
`Subject` is assumed to be userId of a user in `Users` database table. 

A token passed in might look like the below. (This must be a signed JWT with HS256, and with the symmetric key `myReallyReallyReallyLongJwtSecretKey`).
```text
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3Mjg0MDAyMDksImV4cCI6MTc1OTkzNjIwOSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjIxMTViZTdmLTY4YzItNDE5ZC05NzgxLTc4OWQxNjU4ODA3MSIsInVzZXJzIjoiQURNSU5TIn0.EK27kkABUWiccYDqepeRQQ-27qJ41UKkDjRCCKh-M24

```

## Persistence
For demo purposes this microservice uses H2 database with file based URL (`jdbc:h2:file:~/authzdb`) that is populated freshly every time the
service is started. This database configuration can be changed in application.yaml. There is a DDL file called `schema-h2.sql` and a DML file
called `data-h2.sql` under `src/main/resources` folder used for automatic data population.

## How to run the service locally
Start the service from command line using `gradle bootRun`. By default, the service should be available at `http://localhost:9001/api/v1/accounts` for POST http method.

### POST
For a granted access, the request (using Httpie) might look like this:
```json
http POST localhost:9001/v1/authorize token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3Mjg0MDAyMDksImV4cCI6MTc1OTkzNjIwOSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjIxMTViZTdmLTY4YzItNDE5ZC05NzgxLTc4OWQxNjU4ODA3MSIsInVzZXJzIjoiQURNSU5TIn0.EK27kkABUWiccYDqepeRQQ-27qJ41UKkDjRCCKh-M24 requestedUri=v1/accounts requestedMethod=GET
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 08 Oct 2024 16:37:31 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "authorized": true,
    "message": "Access Granted for GET on v1/accounts"
}

```
For a denied access, the request might look like this:
```json
http POST localhost:9001/v1/authorize token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3MjgzMzEwMzEsImV4cCI6MTc1OTg4MDgzNSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjY5ZjExYmUyLWExNTUtNDZhNC04MDIyLTM1YjAyYWQ0MDhmMCIsImdyb3VwcyI6InVzZXJzIn0.Rf6v_vFPLWaycIqO2UTRnQtE4x01ByyAum3OUymiknw requestedUri=v1/accounts requestedMethod=POST
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 08 Oct 2024 16:40:28 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "authorized": false,
    "message": "Access Denied for POST on v1/accounts"
}
```
