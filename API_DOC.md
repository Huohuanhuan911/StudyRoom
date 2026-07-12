# 自习室预约系统 API 文档

## 基础信息

- **API 基础路径**: `http://localhost:8080/api`
- **认证方式**: JWT Token（在请求头中携带 `Authorization: Bearer <token>`）
- **统一响应格式**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": "2024-01-15 10:30:00"
}
```

## 状态码说明

| 状态码 | 含义 |
|--------|------|
| 200 | 操作成功 |
| 400 | 参数错误 |
| 401 | 未登录或Token已过期 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 409 | 重复操作 |
| 500 | 系统错误/业务异常 |

---

## 一、认证管理

### 1. 用户登录

- **URL**: `POST /auth/login`
- **描述**: 学生用户登录系统，成功后返回JWT令牌

**请求参数**:
```json
{
  "studentId": "2024001",
  "password": "password123"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studentId | String | 是 | 学号 |
| password | String | 是 | 密码 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "studentId": "2024001",
    "name": "张三",
    "role": "ROLE_STUDENT"
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 2. 用户注册

- **URL**: `POST /auth/register`
- **描述**: 学生用户注册账号

**请求参数**:
```json
{
  "studentId": "2024001",
  "name": "张三",
  "password": "password123",
  "phone": "13800138000",
  "email": "zhangsan@example.com"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studentId | String | 是 | 学号（最大50字符） |
| name | String | 是 | 姓名（最大100字符） |
| password | String | 是 | 密码（6-100字符） |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "注册成功",
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 二、公共查询（无需登录）

### 1. 查询所有校区

- **URL**: `GET /public/campuses`
- **描述**: 获取所有校区列表（公开）

**请求参数**: 无

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "主校区",
      "address": "北京市海淀区",
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 2. 查询校区楼栋

- **URL**: `GET /public/buildings/{campusId}`
- **描述**: 根据校区ID查询楼栋列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| campusId | Long | 是 | 校区ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "campusId": 1,
      "name": "A栋",
      "floorCount": 5,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 3. 查询楼栋楼层

- **URL**: `GET /public/floors/{buildingId}`
- **描述**: 根据楼栋ID查询楼层列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| buildingId | Long | 是 | 楼栋ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "buildingId": 1,
      "floorNumber": 3,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 4. 查询楼层自习室

- **URL**: `GET /public/rooms/{floorId}`
- **描述**: 根据楼层ID查询自习室列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| floorId | Long | 是 | 楼层ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "floorId": 1,
      "roomName": "301室",
      "capacity": 40,
      "status": 0,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 5. 查询可用自习室

- **URL**: `GET /public/rooms/active`
- **描述**: 获取所有可用的自习室列表

**请求参数**: 无

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "floorId": 1,
      "roomName": "301室",
      "capacity": 40,
      "status": 0,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 三、预约管理（需登录）

### 1. 预约座位

- **URL**: `POST /reservations/reserve`
- **描述**: 学生选择座位进行预约，需校验黑名单、每日预约次数、时间有效性等

**请求参数**:
```json
{
  "seatId": 1,
  "startTime": "2024-01-15 14:00:00",
  "endTime": "2024-01-15 16:00:00"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| seatId | Long | 是 | 座位ID |
| startTime | LocalDateTime | 是 | 预约开始时间 |
| endTime | LocalDateTime | 是 | 预约结束时间 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "userId": 1,
    "seatId": 1,
    "seatNumber": "A-301-01",
    "studyRoomId": 1,
    "studyRoomName": "301室",
    "floorId": 1,
    "floorNumber": 3,
    "buildingId": 1,
    "buildingName": "A栋",
    "startTime": "2024-01-15 14:00:00",
    "endTime": "2024-01-15 16:00:00",
    "status": 0,
    "statusText": "待签到",
    "createTime": "2024-01-15 10:30:00",
    "updateTime": "2024-01-15 10:30:00"
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 2. 取消预约

- **URL**: `POST /reservations/{id}/cancel`
- **描述**: 学生可取消自己待签到的预约，管理员可取消任何人的预约

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 是 | 预约ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "取消成功",
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 3. 查询我的预约

- **URL**: `GET /reservations/me`
- **描述**: 查询当前用户的预约列表，支持分页和状态筛选

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | 否 | 状态筛选：0-待签到，1-已签到，2-已取消，3-爽约，4-已结束 |
| page | Integer | 否 | 页码（默认0） |
| size | Integer | 否 | 每页数量（默认10） |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "items": [
      {
        "id": 1,
        "userId": 1,
        "seatId": 1,
        "seatNumber": "A-301-01",
        "studyRoomId": 1,
        "studyRoomName": "301室",
        "floorId": 1,
        "floorNumber": 3,
        "buildingId": 1,
        "buildingName": "A栋",
        "startTime": "2024-01-15 14:00:00",
        "endTime": "2024-01-15 16:00:00",
        "status": 0,
        "statusText": "待签到",
        "createTime": "2024-01-15 10:30:00",
        "updateTime": "2024-01-15 10:30:00"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 4. 查询座位状态

- **URL**: `GET /reservations/seat/{seatId}`
- **描述**: 查询指定座位的当前状态及状态描述

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| seatId | Long | 是 | 座位ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "studyRoomId": 1,
    "seatNumber": "A-301-01",
    "status": 0,
    "xCoordinate": 1,
    "yCoordinate": 1,
    "createTime": "2024-01-10 00:00:00",
    "updateTime": "2024-01-10 00:00:00"
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 四、签到管理（需登录）

### 1. 签到

- **URL**: `POST /checkin`
- **描述**: 学生对自己的待签到预约进行签到，需在预约开始前30分钟至开始后15分钟内完成

**请求参数**:
```json
{
  "reservationId": 1
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reservationId | Long | 是 | 预约ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "签到成功",
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 五、自习室查询（需登录）

### 1. 查询校区楼栋

- **URL**: `GET /studyroom/buildings/{campusId}`
- **描述**: 根据校区ID获取该校区下的所有楼栋列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| campusId | Long | 是 | 校区ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "campusId": 1,
      "name": "A栋",
      "floorCount": 5,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 2. 查询楼栋楼层

- **URL**: `GET /studyroom/floors/{buildingId}`
- **描述**: 根据楼栋ID获取该楼栋下的所有楼层列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| buildingId | Long | 是 | 楼栋ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "buildingId": 1,
      "floorNumber": 3,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 3. 查询楼层自习室

- **URL**: `GET /studyroom/rooms/{floorId}`
- **描述**: 根据楼层ID获取该楼层下状态正常的自习室列表

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| floorId | Long | 是 | 楼层ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "floorId": 1,
      "roomName": "301室",
      "capacity": 40,
      "status": 0,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 4. 查询自习室座位

- **URL**: `GET /studyroom/seats/{roomId}`
- **描述**: 获取自习室所有座位及其状态信息

**路径参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| roomId | Long | 是 | 自习室ID |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "studyRoomId": 1,
      "seatNumber": "A-301-01",
      "status": 0,
      "xCoordinate": 1,
      "yCoordinate": 1,
      "createTime": "2024-01-10 00:00:00",
      "updateTime": "2024-01-10 00:00:00"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 5. 查询空闲座位

- **URL**: `POST /studyroom/free-seats`
- **描述**: 根据校区、楼栋、楼层和时间条件查询空闲座位列表

**请求参数**:
```json
{
  "campusId": 1,
  "buildingId": 1,
  "floorId": 1,
  "startTime": "2024-01-15 14:00:00",
  "endTime": "2024-01-15 16:00:00"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| campusId | Long | 是 | 校区ID |
| buildingId | Long | 否 | 楼栋ID |
| floorId | Long | 否 | 楼层ID |
| startTime | LocalDateTime | 是 | 预约开始时间 |
| endTime | LocalDateTime | 是 | 预约结束时间 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "seatId": 1,
      "seatNumber": "A-301-01",
      "seatStatus": 0,
      "studyRoomId": 1,
      "studyRoomName": "301室",
      "studyRoomCapacity": 40,
      "floorId": 1,
      "floorNumber": 3,
      "buildingId": 1,
      "buildingName": "A栋",
      "campusId": 1,
      "campusName": "主校区"
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 六、统计管理（需登录）

### 1. 实时统计

- **URL**: `GET /statistics/current`
- **描述**: 获取当前全校使用人数、座位数及各自习室使用情况

**请求参数**: 无

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "totalUsers": 150,
    "totalSeats": 500,
    "overallOccupancyRate": 0.3,
    "roomStatistics": [
      {
        "roomId": 1,
        "roomName": "301室",
        "currentUsers": 20,
        "freeSeats": 20,
        "totalSeats": 40,
        "occupancyRate": 0.5
      }
    ]
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

### 2. 历史统计

- **URL**: `GET /statistics/history`
- **描述**: 查询指定时间段的历史统计数据

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studyRoomId | Long | 否 | 自习室ID（为空时查询全部） |
| startDate | LocalDate | 是 | 开始日期 |
| endDate | LocalDate | 是 | 结束日期 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "totalReservations": 100,
      "completedReservations": 80,
      "noShowReservations": 5,
      "dailyUsageRate": 0.6,
      "hourlyReservationCount": {
        "08": 10,
        "09": 20,
        "10": 30
      },
      "roomUsages": [
        {
          "roomId": 1,
          "roomName": "301室",
          "reservationCount": 20,
          "usageRate": 0.5
        }
      ]
    }
  ],
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 七、AI智能查询（需登录）

### 1. 自然语言查询

- **URL**: `POST /ai/query`
- **描述**: 通过自然语言提问查询自习室相关信息

**请求参数**:
```json
{
  "question": "今天下午3点A区有哪些空教室"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| question | String | 是 | 自然语言问题 |

**成功响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "sql": "SELECT * FROM study_room WHERE ...",
    "result": [
      {
        "roomName": "301室",
        "capacity": 40
      }
    ],
    "naturalLanguage": "今天下午3点A区有空教室：301室（40座）",
    "success": true,
    "errorMessage": null
  },
  "timestamp": "2024-01-15 10:30:00"
}
```

---

## 八、管理员管理（需管理员权限）

### 1. 校区管理

#### 新增校区
- **URL**: `POST /admin/campus`
- **请求参数**:
```json
{
  "name": "主校区",
  "address": "北京市海淀区"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 是 | 校区名称（最大100字符） |
| address | String | 否 | 校区地址（最大200字符） |

#### 修改校区
- **URL**: `PUT /admin/campus/{id}`

#### 删除校区
- **URL**: `DELETE /admin/campus/{id}`
- **注意**: 需确保该校区下无关联楼栋

#### 查询所有校区
- **URL**: `GET /admin/campus`

---

### 2. 楼栋管理

#### 新增楼栋
- **URL**: `POST /admin/building`
- **请求参数**:
```json
{
  "campusId": 1,
  "name": "A栋",
  "floorCount": 5
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| campusId | Long | 是 | 校区ID |
| name | String | 是 | 楼栋名称（最大100字符） |
| floorCount | Integer | 否 | 楼层数 |

#### 修改楼栋
- **URL**: `PUT /admin/building/{id}`

#### 删除楼栋
- **URL**: `DELETE /admin/building/{id}`
- **注意**: 需确保该楼栋下无关联楼层

#### 根据校区查询楼栋
- **URL**: `GET /admin/building/campus/{campusId}`

---

### 3. 楼层管理

#### 新增楼层
- **URL**: `POST /admin/floor`
- **请求参数**:
```json
{
  "buildingId": 1,
  "floorNumber": 3
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| buildingId | Long | 是 | 楼栋ID |
| floorNumber | Integer | 是 | 楼层号 |

#### 修改楼层
- **URL**: `PUT /admin/floor/{id}`

#### 删除楼层
- **URL**: `DELETE /admin/floor/{id}`
- **注意**: 需确保该楼层下无关联自习室

#### 根据楼栋查询楼层
- **URL**: `GET /admin/floor/building/{buildingId}`

---

### 4. 自习室管理

#### 新增自习室
- **URL**: `POST /admin/studyroom`
- **请求参数**:
```json
{
  "floorId": 1,
  "roomName": "301室",
  "capacity": 40,
  "status": 0
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| floorId | Long | 是 | 楼层ID |
| roomName | String | 是 | 自习室名称（最大100字符） |
| capacity | Integer | 否 | 座位容量 |
| status | Integer | 否 | 状态：0-正常，1-停用 |

#### 修改自习室
- **URL**: `PUT /admin/studyroom/{id}`

#### 删除自习室
- **URL**: `DELETE /admin/studyroom/{id}`
- **注意**: 需确保该自习室下无关联座位

#### 根据楼层查询自习室
- **URL**: `GET /admin/studyroom/floor/{floorId}`

---

### 5. 座位管理

#### 批量新增座位
- **URL**: `POST /admin/seat/batch`
- **请求参数**:
```json
{
  "studyRoomId": 1,
  "count": 40
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studyRoomId | Long | 是 | 自习室ID |
| count | Integer | 是 | 座位数量 |

#### 修改座位
- **URL**: `PUT /admin/seat/{id}`
- **请求参数**:
```json
{
  "studyRoomId": 1,
  "seatNumber": "A-301-01",
  "status": 0,
  "xCoordinate": 1,
  "yCoordinate": 1
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studyRoomId | Long | 是 | 自习室ID |
| seatNumber | String | 否 | 座位编号（最大50字符） |
| status | Integer | 否 | 状态：0-空闲，1-已预约，2-已占用 |
| xCoordinate | Integer | 否 | 座位排布X坐标 |
| yCoordinate | Integer | 否 | 座位排布Y坐标 |

#### 删除座位
- **URL**: `DELETE /admin/seat/{id}`
- **注意**: 需确保该座位无未完成预约

#### 根据自习室查询座位
- **URL**: `GET /admin/seat/room/{roomId}`

---

## 附录：状态值说明

### 自习室状态
| 值 | 含义 |
|----|------|
| 0 | 正常 |
| 1 | 停用 |

### 座位状态
| 值 | 含义 |
|----|------|
| 0 | 空闲 |
| 1 | 已预约 |
| 2 | 已占用 |

### 预约状态
| 值 | 含义 |
|----|------|
| 0 | 待签到 |
| 1 | 已签到 |
| 2 | 已取消 |
| 3 | 爽约 |
| 4 | 已结束 |

### 用户角色
| 值 | 含义 |
|----|------|
| 0 | 学生 |
| 1 | 管理员 |

### 用户状态
| 值 | 含义 |
|----|------|
| 0 | 正常 |
| 1 | 禁用 |

---

## 业务配置说明

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| max-daily-times | 3 | 每日最大预约次数 |
| advance-minutes | 30 | 可提前预约的最短时间（分钟） |
| max-duration-hours | 4 | 单次预约最大时长（小时） |
| cancel-before-minutes | 15 | 取消预约需提前的时间（分钟） |
| checkin-late-minutes | 15 | 签到允许迟到的时间（分钟） |
| checkin-early-minutes | 30 | 可提前签到的时间（分钟） |
| default-days | 7 | 默认拉黑天数 |
| trigger-count | 3 | 触发拉黑的爽约次数 |