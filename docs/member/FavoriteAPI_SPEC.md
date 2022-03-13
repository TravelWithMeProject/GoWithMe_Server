# API 스펙

## 인증이 필요한 path는 Header에 Bearer 토큰이 필요합니다.

` 예시 : Bearer eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImlsZ29sY0BuYXZlci5jb20iLCJuaWNrbmFtZSI6ImRmc2RhZmFzZCIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE2NDUzNDg4MTN9.vZ9GQGz9AOsp7WWTxPl07Odz8h1D30_Wj90lFS-5zCQfVk4UkzpeeN3qgqywiIsDfBWlEtGtUZ9wIVBWpYFCSw`

인증이 필요한 path에는 * 표시를 붙여 놓아 구분하도록 하겠습니다.

## PreferenceController *

- path : /api/v1/favorite/save


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 401

```JSON
{
  "prefer": {
    "arrival": "유럽",
    "accommodation": "Guest House",
    "startTime": "12-03-2022",
    "endTime": "17-03-2022"
  }
}
```

