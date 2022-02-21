# API 스펙

## 인증이 필요한 path는 Header에 Bearer 토큰이 필요합니다. 

` 예시 : Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDUzNDg4MTN9.vZ9GQGz9AOsp7WWTxPl07Odz8h1D30_Wj90lFS-5zCQfVk4UkzpeeN3qgqywiIsDfBWlEtGtUZ9wIVBWpYFCSw`

인증이 필요한 path에는 * 표시를 붙여 놓아 구분하도록 하겠습니다. 

## Auth Controller

### 1. Login

- path : /api/v1/member/login


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 401

- Request

```JSON
{
    "user": {
        "email": "ilgolc@naver.com",
        "password": "1234"
    }
}
```

- Response

```JSON
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDU0MjQ5MjN9.oX1ZkmSeVydVW9SA6N0_cPrw6tNwWzbgozz9ENRQF571hGKewrcRSSb3RadSRIVYWlf-YE-BR-cC5kfTCdAm1A",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiZW1haWwiOiJpbGdvbGNAbmF2ZXIuY29tIiwibmlja25hbWUiOiJkZnNkYWZhc2QiLCJleHAiOjE2NDU0NDExMjN9.8XkzvKTOkdMxvtkCDQSvr7J9hQMcXpQpNx7QPn20G58J1TR7ASiOk7WIrsmE11VxZQuslF8DHGiEvZzh6uxTQA"
}
```

### 2. Logout *

- path : /api/v1/member/logout


- HttpMethod : DELETE


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 401


-Request

```JSON
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsIkF1dGhvcml6YXRpb24iOiJST0xFX1VTRVIiLCJleHAiOjE2NDM4OTU4MzZ9.fAu28xrgABO4BphiRAIrqXsa8KM1FeFA3Nim1GloLKWs8KPFLx72Lq8OF-6TgdJrF5t_1huGyrqhJ_ETc63tBQ",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsIkF1dGhvcml6YXRpb24iOiJST0xFX1VTRVIiLCJleHAiOjE2NDM5MTIwMzZ9.4AQ3rEwvFkkn_2LJo7r3FiT97xDowGhYjY6Qb8naCUt2hl8zAx5Xo4jXPb0scqcV3FnL_hsnyEE9W66XXPqnOw"
}
```


-Response : nothing


### reissue

- path : /api/v1/member/reissue


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400


- Request 

```JSON
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDUzNDc5OTl9.XPhcAd2f-MLiRkBOE4c99oUDUO5jOqpCsL8PombBewwhLfFuhU2B5GMbirb9eL6xfwnHxDgrv-BhYmSUkVtJ6Q",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiZW1haWwiOiJpbGdvbGNAbmF2ZXIuY29tIiwibmlja25hbWUiOiJkZnNkYWZhc2QiLCJleHAiOjE2NDUzNjQxOTl9.jRJvqU4cQ6Rg6RLplRBdJzFDfgxYpJCiAPYjUHGxZXxuAexpYDIRdRouMaq1pVBwKctnf9v2id_RqfOBZ6QkHg"
}
```


- Response

```JSON
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDU0MjQ5MjN9.oX1ZkmSeVydVW9SA6N0_cPrw6tNwWzbgozz9ENRQF571hGKewrcRSSb3RadSRIVYWlf-YE-BR-cC5kfTCdAm1A",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiZW1haWwiOiJpbGdvbGNAbmF2ZXIuY29tIiwibmlja25hbWUiOiJkZnNkYWZhc2QiLCJleHAiOjE2NDU0NDExMjN9.8XkzvKTOkdMxvtkCDQSvr7J9hQMcXpQpNx7QPn20G58J1TR7ASiOk7WIrsmE11VxZQuslF8DHGiEvZzh6uxTQA"
}
```


## Member Controller

### 1. Join

- path : /api/v1/member/join


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 201 Created


- Fail : 400


- Request

```JSON
{
    "user": {
        "email": "ilgolc@naver.com",
        "password": "1234",
        "name": "ssar",
        "nickname": "dfsdafasd",
        "birth": "10-12-1999",
        "profile": "asdfasdf"
    }
}
```


- Response

```JSON
{
    "user": {
        "email": "ilgolc@naver.com",
        "name": "ssar",
        "nickname": "dfsdafasd",
        "birth": "10-12-1999",
        "profile": "/users/desktop/fdsf.img"
    }
}
```


### 2. FindOne *

- path : /api/v1/member/{id}


- HttpMethod : GET


- contentType : application/json


- RequestParam : id (**Required**)


- SuccessStatus : 200 OK


- Response 

```JSON
{
  "user": {
    "email": "ilgolc@naver.com",
    "name": "ssar",
    "nickname": "dfsdafasd",
    "birth": "10-12-1999",
    "profile": "/users/desktop/fdsf.img"
  }
}
```


- Fail : 400, 401


### 3. Update *

- path : /api/v1/member


- HttpMethod : PATCH


- contentType : application/json


- SuccessStatus : 200 Created


- Fail : 400, 401


-Request

```JSON
{
  "user" : {
        "email": "ilgolf@naver.com",
        "password": "1230",
        "nickname": "ssar",
        "profile": "/users/desktop/kim.img",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDUzNDg4MTN9.vZ9GQGz9AOsp7WWTxPl07Odz8h1D30_Wj90lFS-5zCQfVk4UkzpeeN3qgqywiIsDfBWlEtGtUZ9wIVBWpYFCSw"
  }
}
```


- Response : nothing

### 4. Delete *

- path : /api/v1/member


- HttpMethod : DELETE


- contentType : application/json


- SuccessStatus : 200 Created


- Fail : 400, 401


- Request

```JSON
    {
      "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDUzNDg4MTN9.vZ9GQGz9AOsp7WWTxPl07Odz8h1D30_Wj90lFS-5zCQfVk4UkzpeeN3qgqywiIsDfBWlEtGtUZ9wIVBWpYFCSw"
    }
```


- Response : nothing