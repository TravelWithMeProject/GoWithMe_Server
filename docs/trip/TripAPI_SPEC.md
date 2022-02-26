# Trip API 스펙

###시간표(전체 일정, TimeTable) 및 세부일정(Schedule) 관련 api입니다.

## TimeTable Controller(전체 일정 관련)

### 1. 특정 시간표 가져오기

- path : /api/v1/timeTable/{memberId}/{timeTableId}


- HttpMethod : GET


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
    - PathVariable : memberId, timeTableId
    - ex) GET : [serverAddress]/api/v1/timeTable/3/4
    

- Response

```JSON
{
  "content": "이번 온천 여행은 몽골로 가보기로 했다.",
  "tableName": "몽골 온천 여행",
  "timeTableId": 1,
  "totalPeriodEnd": "2022-02-24T17:58:51.401Z",
  "totalPeriodStart": "2022-02-24T17:58:51.401Z"
}
```

### 2. 사용자의 모든 시간표 가져오기

- path : /api/v1/timeTable/{memberId}


- HttpMethod : GET


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
    - PathVariable : memberId
    - ex) GET : [serverAddress]/api/v1/timeTable/3


- Response

```JSON
{
  "timeTableList" : [
    {
      "content": "이번 온천 여행은 몽골로 가보기로 했다.",
      "tableName": "몽골 온천 여행",
      "timeTableId": 1,
      "totalPeriodEnd": "2022-02-24T17:58:51.401Z",
      "totalPeriodStart": "2022-02-24T17:58:51.401Z"
    },
    {
      "content": "이번 스파 여행은 뉴욕으로 가보기로 했다.",
      "tableName": "뉴욕 스파 여행",
      "timeTableId": 2,
      "totalPeriodEnd": "2022-02-24T17:58:51.401Z",
      "totalPeriodStart": "2022-02-24T17:58:51.401Z"
    }
  ]
}
```


### 3. 시간표 생성

- path : /api/v1/timeTable/create


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400


- Request

```JSON
{
  "timeTableContent": "이번 온천 여행은 몽골로 가보기로 했다.(Null은 안됨)",
  "timeTableName": "몽골 온천 여행",
  "totalPeriodEnd": "2022-02-24T18:06:11.858Z",
  "totalPeriodStart": "2022-02-24T18:06:11.858Z",
  "userId": 1
}
```


- Response

```JSON
{
  "success": true,
  "timeTableId": 1 
}
```


### 4. 시간표 수정(업데이트)

- path : /api/v1/timeTable/{timeTableId}


- HttpMethod : PUT


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
    - PathVariable : timeTableId
    - RequestBody
        ```JSON
         {
         "timeTableContent": "이번 온천 여행은 몽골로 가보기로 했다.(Null은 안됨)",
         "timeTableName": "몽골 온천 여행",
         "totalPeriodEnd": "2022-02-24T18:09:09.356Z",
         "totalPeriodStart": "2022-02-24T18:09:09.356Z",
         "userId": 1
         }
        ```

    
- Response 
  - nothing
    


### 5. 시간표 삭제

- path : /api/v1/timeTable/{memberId}/{timeTableId}


- HttpMethod : DELETE


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
    - PathVariable : memberId, timeTableId
    - ex) DELETE : [serverAddress]/api/v1/timeTable/2/3


- Response
    - nothing


## Schedule Controller (세부 일정 관련)

### 1. 특정 세부일정 가져오기

- path : /api/v1/schedule/{timeTableId}/{scheduleId}


- HttpMethod : GET


- contentType : application/json


- SuccessStatus : 200 success


- Fail : 400, 404


- Request
  - PathVariable : timeTableId, scheduleId
  - ex) GET : [serverAddress]/api/v1/schedule/1/4


- Response

```JSON
{
  "cost": 10000,
  "scheduleContent": "시장 들렀다가 XXX라는 맛집 방문 예정",
  "scheduleId": 1,
  "scheduleTitle": "몽골 수도 맛집 XXX 방문",
  "totalPeriodEnd": "2022-02-24T18:26:11.741Z",
  "totalPeriodStart": "2022-02-24T18:26:11.741Z"
}
```


### 2. 사용자의 해당 시간표(전체일정)의 모든 세부일정 가져오기

- path : /api/v1/schedule/{timeTableId}


- HttpMethod : GET


- contentType : application/json


- SuccessStatus : 200 success


- Fail : 400, 404


- Request
  - PathVariable : timeTableId
  - ex) GET : [serverAddress]/api/v1/schedule/1/4


- Response

```JSON
{
  "scheduleList" : [
    {
      "cost": 10000,
      "scheduleContent": "시장 들렀다가 XXX라는 맛집 방문 예정",
      "scheduleId": 1,
      "scheduleTitle": "몽골 수도 맛집 XXX 방문",
      "totalPeriodEnd": "2022-02-24T18:26:11.741Z",
      "totalPeriodStart": "2022-02-24T18:26:11.741Z"
    },
    {
      "cost": 4000,
      "scheduleContent": "에버랜드 가서 베거파질때까지 놀기",
      "scheduleId": 2,
      "scheduleTitle": "에버랜드 가기",
      "totalPeriodEnd": "2022-02-25T18:26:11.741Z",
      "totalPeriodStart": "2022-02-25T18:26:11.741Z"
    }
  ]
}
```


### 3. 세부 일정 생성

- path : /api/v1/schedule/create


- HttpMethod : POST


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400


- Request

```JSON
{
  "cost": 10000,
  "detailPeriodEnd": "2022-02-24T18:29:54.211Z",
  "detailPeriodStart": "2022-02-24T18:29:54.211Z",
  "scheduleContent": "시장 들렀다가 XXX라는 맛집 방문 예정",
  "scheduleTitle": "몽골 수도 맛집 XXX 방문",
  "timeTableId": 1
}
```


- Response

```JSON
{
  "success": true,
  "scheduleId": 1 
}
```


### 4. 세부일정 수정(업데이트)

- path : /api/v1/schedule/{scheduleId}


- HttpMethod : PUT


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
  - PathVariable : scheduleId
  - RequestBody
      ```JSON
       {
        "cost": 10000,
        "detailPeriodEnd": "2022-02-24T18:31:40.832Z",
        "detailPeriodStart": "2022-02-24T18:31:40.832Z",
        "scheduleContent": "시장 들렀다가 XXX라는 맛집 방문 예정",
        "scheduleTitle": "몽골 수도 맛집 XXX 방문",
        "timeTableId": 1
       }
      ```


- Response
  - nothing



### 5. 시간표 삭제

- path : /api/v1/schedule/{timeTableId}/{scheduleId}


- HttpMethod : DELETE


- contentType : application/json


- SuccessStatus : 200 Ok


- Fail : 400, 404


- Request
  - PathVariable : timeTableId, scheduleId
  - ex) DELETE : [serverAddress]/api/v1/schedule/2/3


- Response
  - nothing