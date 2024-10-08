# Accounts Service
## Overview
Accounts are created for end users. Each user can have 0 or more accounts
with Extend. This microservice exposes a REST API to 1) view all user accounts and 2) create a user account.
## REST Endpoints
### GET `api/v1/accounts`
This endpoint returns all the user accounts if the caller is authorized to invoke the endpoint. An Account has simply an `id` (account id) and
`userId` (end user's id) of the owning user. This endpoint returns a list of accounts as an array using `application/json` content-type.
### POST `api/v1/accounts`
This endpoint create a new user account if the caller is authorized to invoke the endpoint. `userId` is the only attribute that needs to be sent
in for the Account as this endpoint created an accountId only the fly. This endpoint returns the new account created using `application/json` content-type. 
Callers that belong to ADMIN group are only allowed to invoke the endpoint. 

## Authorization

### Design considerations
Checking for authorization is implemented as a http request filter that is automatically invoked on the configured endpoint paths.
This eliminates the need for an AOP advice as annotation type of approaches that might not be fully effective as developers might
forget to add them to the methods or classes as needed.

### Implementation
Caller of this API is checked for authorization to the endpoint by using Authorization service (another microservice) before 
the controller code is invoked by using a http request filter called `JwtRequestFilter`. A valid JWT token needs to be sent 
as `Bearer` token in the `Authorization` http header, with the `Subject` of the JWT token set to the `userId` of the caller. 
It is assumed that an authentication service is already called before making the call to this endpoint; and the authentication service populated the JWT token with the
right Subject and claims that correspond to the caller.


For example A JWT token might look like this:
```json
{
    "iss": "Extends Auth",
    "iat": 1728400209,
    "exp": 1759936209,
    "aud": "authz.extend.com",
    "sub": "2115be7f-68c2-419d-9781-789d16588071",
    "users": "ADMINS"
}
```
A signed JWT (HS256, with the symmetric key `myReallyReallyReallyLongJwtSecretKey`) might look like this for the above
JWT token:
```text
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3Mjg0MDAyMDksImV4cCI6MTc1OTkzNjIwOSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjIxMTViZTdmLTY4YzItNDE5ZC05NzgxLTc4OWQxNjU4ODA3MSIsInVzZXJzIjoiQURNSU5TIn0.EK27kkABUWiccYDqepeRQQ-27qJ41UKkDjRCCKh-M24
```

##Persistence
For demo purposes this microservice uses H2 database with file based URL (`jdbc:h2:file:~/accountsdb`) that is populated freshly every time the
service is started. This database configuration can be changed in application.yaml. There is a DDL file called `schema-h2.sql` and a DML file
called `data-h2.sql` under `src/main/resources` folder used for automatic data population.

## How to run the service locally
Start the service from command line using `gradle bootRun`. By default, the service should be available at `http://localhost:8000/api/v1/accounts` for GET and POST http methods.
### GET
Using Httpie, a GET request with valid JWT token might look like this:
` http GET localhost:8000/api/v1/accounts Authorization:'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3MjgzMzEwMzEsImV4cCI6MTc1OTg4MDgzNSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjY5ZjExYmUyLWExNTUtNDZhNC04MDIyLTM1YjAyYWQ0MDhmMCIsImdyb3VwcyI6InVzZXJzIn0.Rf6v_vFPLWaycIqO2UTRnQtE4x01ByyAum3OUymiknw'`
Response might look like this:
```json
HTTP/1.1 200
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Tue, 08 Oct 2024 16:26:38 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 0

[
    {
        "id": "35b02be2-a155-46a4-8022-35b02ad408f0",
        "userId": "end-userid-100"
    },
    {
        "id": "43a71be2-a155-46a4-8022-35b02ad408f0",
        "userId": "end-userid-200"
    },
    {
        "id": "80221be2-a155-46a4-8022-35b02ad408f0",
        "userId": "end-userid-300"
    },
    {
        "id": "8534a7ba-f046-49e5-8450-2caa4820b44e",
        "userId": "end-user-id-1"
    }
]

```
For a GET request with Httpie, and without Authorization header will fail like this:
```json
http GET localhost:8000/api/v1/accounts
HTTP/1.1 403
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Length: 0
Date: Tue, 08 Oct 2024 16:25:32 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 0

```
### POST
A POST request to create a new user account with valid JWT token might look like this:
```json
http POST localhost:8000/api/v1/accounts Authorization:'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3Mjg0MDAyMDksImV4cCI6MTc1OTkzNjIwOSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjIxMTViZTdmLTY4YzItNDE5ZC05NzgxLTc4OWQxNjU4ODA3MSIsInVzZXJzIjoiQURNSU5TIn0.EK27kkABUWiccYDqepeRQQ-27qJ41UKkDjRCCKh-M24' userId=test-user-id-1
HTTP/1.1 200
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Tue, 08 Oct 2024 16:31:54 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 0

{
    "id": "3a74f0b3-22d6-4eef-98f0-4e550e49cd70",
    "userId": "test-user-id-1"
}
```
An invalid request (due to invalid JWT token, see ZZZZ added at the end of token value) might be like this:
```json
http POST localhost:8000/api/v1/accounts Authorization:'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeHRlbmRzIEF1dGgiLCJpYXQiOjE3Mjg0MDAyMDksImV4cCI6MTc1OTkzNjIwOSwiYXVkIjoiYXV0aHouZXh0ZW5kLmNvbSIsInN1YiI6IjIxMTViZTdmLTY4YzItNDE5ZC05NzgxLTc4OWQxNjU4ODA3MSIsInVzZXJzIjoiQURNSU5TIn0.EK27kkABUWiccYDqepeRQQ-27qJ41UKkDjRCCKh-M24ZZZZ' userId=test-user-id-2
HTTP/1.1 403
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Length: 47
Date: Tue, 08 Oct 2024 16:33:19 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 0

{"message":"Not authorized","authorized":false}
```