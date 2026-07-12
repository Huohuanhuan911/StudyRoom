<template>
  <div class="admin-page">
    <div class="header-bar">
      <div class="header-left">
        <div class="header-icon">
          <div class="icon-circle"></div>
          <div class="icon-triangle"></div>
        </div>
        <span class="header-text">智能自习室预约管理平台</span>
      </div>
      <button class="logout-btn" @click="handleLogout">退出登录</button>
    </div>
    <div class="content">
      <div class="sidebar">
        <div class="menu-item" :class="{ active: currentPage === 'home' }" @click="currentPage = 'home'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
            <polyline points="9 22 9 12 15 12 15 22"/>
          </svg>
          <span>首页</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'resource' }" @click="currentPage = 'resource'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect width="18" height="18" x="3" y="3" rx="2"/>
            <line x1="9" x2="15" y1="9" y2="9"/>
            <line x1="9" x2="15" y1="15" y2="15"/>
          </svg>
          <span>教室管理</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'heatmap' }" @click="currentPage = 'heatmap'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <circle cx="12" cy="12" r="6"/>
            <circle cx="12" cy="12" r="2"/>
          </svg>
          <span>热力图</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'blacklist' }" @click="currentPage = 'blacklist'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
            <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
          </svg>
          <span>用户管理</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'reports' }" @click="currentPage = 'reports'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 20V10"/>
            <path d="M12 20V4"/>
            <path d="M6 20v-6"/>
          </svg>
          <span>预约管理</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'settings' }" @click="currentPage = 'settings'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="3"/>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/>
          </svg>
          <span>系统设置</span>
        </div>
      </div>
      <div class="main-content" v-if="currentPage === 'home'">
        <div class="page-header">
          <h2>欢迎回来，管理员</h2>
        </div>
        <div class="dashboard-stats">
          <div class="stat-card">
            <div class="stat-icon">🏢</div>
            <div class="stat-info">
              <div class="stat-value">{{ buildingCount }}</div>
              <div class="stat-label">楼栋数量</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📚</div>
            <div class="stat-info">
              <div class="stat-value">{{ classroomCount }}</div>
              <div class="stat-label">教室数量</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">💺</div>
            <div class="stat-info">
              <div class="stat-value">{{ seatCount }}</div>
              <div class="stat-label">座位总数</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📊</div>
            <div class="stat-info">
              <div class="stat-value">{{ occupancyRate }}%</div>
              <div class="stat-label">平均使用率</div>
            </div>
          </div>
        </div>
        
      </div>
      <div class="main-content" v-else-if="currentPage === 'resource'">
        <div class="page-header">
          <h2>自习室资源管理</h2>
          <button class="add-btn" @click="resourceTab === 'buildings' ? openAddBuildingModal() : (resourceTab === 'classrooms' ? openAddClassroomModal() : openAddSeatModal())">
            + {{ resourceTab === 'buildings' ? '添加楼栋' : (resourceTab === 'classrooms' ? '添加教室' : '添加座位') }}
          </button>
        </div>
        <div class="resource-tabs">
          <button class="tab-btn" :class="{ active: resourceTab === 'buildings' }" @click="resourceTab = 'buildings'">楼栋管理</button>
          <button class="tab-btn" :class="{ active: resourceTab === 'classrooms' }" @click="resourceTab = 'classrooms'">教室管理</button>
          <button class="tab-btn" :class="{ active: resourceTab === 'seats' }" @click="resourceTab = 'seats'">座位管理</button>
        </div>
        <div v-if="resourceTab === 'classrooms'" class="classroom-filter-wrapper">
          <div class="classroom-tabs">
            <button class="tab-btn" :class="{ active: classroomView === 'active' }" @click="classroomView = 'active'">正常教室</button>
            <button class="tab-btn" :class="{ active: classroomView === 'deleted' }" @click="classroomView = 'deleted'">已删除(保留3个月)</button>
            <button class="tab-btn" @click="toggleClassroomBatchMode">{{ classroomBatchMode ? '取消' : (classroomView === 'active' ? '批量删除' : '批量恢复') }}</button>
            <div v-if="classroomBatchMode" class="batch-actions-inline">
              <label class="select-all-label">
                <input type="checkbox" :checked="selectedClassroomIds.length === (classroomView === 'active' ? filteredClassrooms.length : deletedClassrooms.length) && (classroomView === 'active' ? filteredClassrooms.length : deletedClassrooms.length) > 0" @change="selectAllClassrooms" />
                全选
              </label>
              <button class="batch-delete-btn" :class="{ disabled: selectedClassroomIds.length === 0 }" @click="classroomView === 'active' ? batchDeleteClassrooms() : batchRestoreClassrooms()">{{ classroomView === 'active' ? '删除选中' : '恢复选中' }} ({{ selectedClassroomIds.length }})</button>
            </div>
          </div>
          <div class="buildings-row-inline">
            <button 
              v-for="building in adminBuildings" 
              :key="building.id" 
              class="building-btn"
              :class="{ active: selectedClassroomBuilding === building.id }"
              @click="selectClassroomBuilding(building.id)"
            >
              {{ building.name }}
            </button>
          </div>
        </div>
        <div v-if="resourceTab === 'buildings'">
          <div class="classroom-tabs">
            <button class="tab-btn" :class="{ active: buildingView === 'active' }" @click="buildingView = 'active'">正常楼栋</button>
            <button class="tab-btn" :class="{ active: buildingView === 'deleted' }" @click="buildingView = 'deleted'">已删除(保留3个月)</button>
            <button class="tab-btn" @click="toggleBuildingBatchMode">{{ buildingBatchMode ? '取消' : (buildingView === 'active' ? '批量删除' : '批量恢复') }}</button>
            <div v-if="buildingBatchMode" class="batch-actions-inline">
              <label class="select-all-label">
                <input type="checkbox" :checked="selectedBuildingIds.length === filteredBuildings.length && filteredBuildings.length > 0" @change="selectAllBuildings" />
                全选
              </label>
              <button class="batch-delete-btn" :class="{ disabled: selectedBuildingIds.length === 0 }" @click="buildingView === 'active' ? batchDeleteBuildings() : batchRestoreBuildings()">{{ buildingView === 'active' ? '删除选中' : '恢复选中' }} ({{ selectedBuildingIds.length }})</button>
            </div>
          </div>
          <div class="buildings-list">
            <div class="building-card" v-for="building in filteredBuildings" :key="building.id">
              <input v-if="buildingBatchMode" type="checkbox" :checked="selectedBuildingIds.includes(building.id)" @change="toggleBuildingSelect(building.id)" class="batch-checkbox" />
              <div class="building-info">
                <h4>{{ building.name }}</h4>
                <p>{{ building.description }}</p>
                <p v-if="building.deletedAt" class="deleted-time">删除时间：{{ formatDeletedTime(building.deletedAt) }}</p>
              </div>
              <div class="building-actions">
                <button v-if="buildingView === 'active'" class="edit-btn" @click="openEditBuildingModal(building)">编辑</button>
                <button v-if="buildingView === 'active'" class="delete-btn" @click="deleteBuilding(building.id)">删除</button>
                <button v-if="buildingView === 'deleted'" class="restore-btn" @click="restoreBuilding(building.id)">恢复</button>
              </div>
            </div>
          </div>
        </div>
        <div v-if="resourceTab === 'classrooms'">
          <div class="classrooms-list" v-if="classroomView === 'active'">
            <div class="classroom-card" v-for="classroom in filteredClassrooms" :key="classroom.id">
              <input v-if="classroomBatchMode" type="checkbox" :checked="selectedClassroomIds.includes(classroom.id)" @change="toggleClassroomSelect(classroom.id)" class="batch-checkbox" />
              <div class="classroom-info">
                <h4>{{ classroom.name }}</h4>
                <p>{{ store.getBuildingName(classroom.buildingId) }} - {{ classroom.floor }}楼</p>
                <div class="classroom-tags">
                  <span class="classroom-status" :class="classroom.open ? 'open' : 'closed'">{{ classroom.open ? '开放' : '关闭' }}</span>
                  <span v-if="classroom.hasAirConditioner" class="ac-tag">❄️ 空调</span>
                </div>
              </div>
              <div class="classroom-actions">
                <button class="edit-btn" @click="openEditClassroomModal(classroom)">编辑</button>
                <button class="delete-btn" @click="deleteClassroom(classroom.id)">删除</button>
                <button class="toggle-btn" @click="toggleClassroomStatus(classroom)">{{ classroom.open ? '关闭' : '开放' }}</button>
              </div>
            </div>
          </div>
          <div class="classrooms-list" v-if="classroomView === 'deleted'">
            <div class="classroom-card deleted" v-for="classroom in deletedClassrooms" :key="classroom.id">
              <input v-if="classroomBatchMode" type="checkbox" :checked="selectedClassroomIds.includes(classroom.id)" @change="toggleClassroomSelect(classroom.id)" class="batch-checkbox" />
              <div class="classroom-info">
                <h4>{{ classroom.name }}</h4>
                <p>{{ store.getBuildingName(classroom.buildingId) }} - {{ classroom.floor }}楼</p>
                <p class="deleted-time">删除时间：{{ classroom.deletedAt ? new Date(classroom.deletedAt).toLocaleString() : '' }}</p>
              </div>
              <div class="classroom-actions">
                <button class="restore-btn" @click="restoreClassroom(classroom.id)">恢复</button>
              </div>
            </div>
          </div>
        </div>
        <div class="seats-list" v-if="resourceTab === 'seats'">
          <div class="buildings-row">
            <button 
              v-for="building in adminBuildings" 
              :key="building.id" 
              class="building-btn"
              :class="{ active: selectedSeatBuilding === building.id }"
              @click="selectSeatBuilding(building.id)"
            >
              {{ building.name }}
            </button>
          </div>
          <div v-if="selectedSeatBuilding" class="seats-classroom-row">
            <button 
              v-for="classroom in filteredSeatClassrooms" 
              :key="classroom.id" 
              class="classroom-btn"
              :class="{ active: selectedSeatClassroom === classroom.id }"
              @click="selectSeatClassroom(classroom.id)"
            >
              {{ classroom.name }}
            </button>
            <button v-if="selectedSeatClassroom" class="import-btn" @click="openImportSeatsModal">批量导入座位</button>
            <button v-if="selectedSeatClassroom" class="import-btn" @click="toggleSeatBatchDeleteMode">{{ seatBatchDeleteMode ? '取消' : '批量删除' }}</button>
            <button v-if="selectedSeatClassroom" class="import-btn" @click="toggleSeatBatchRestoreMode">{{ seatBatchRestoreMode ? '取消' : '批量恢复' }}</button>
            <div v-if="selectedSeatClassroom" class="seat-legend">
              <div class="legend-item"><span class="legend-color socket"></span> 有插座</div>
              <div class="legend-item"><span class="legend-color no-socket"></span> 无插座</div>
            </div>
            <div v-if="seatBatchDeleteMode && selectedSeatClassroom" class="batch-actions-inline">
              <label class="select-all-label">
                <input type="checkbox" :checked="selectedSeatIds.length === activeSeats.length && activeSeats.length > 0" @change="selectAllActiveSeats" />
                全选
              </label>
              <button class="batch-delete-btn" :class="{ disabled: selectedSeatIds.length === 0 }" @click="batchDeleteSeats">删除选中 ({{ selectedSeatIds.length }})</button>
            </div>
            <div v-if="seatBatchRestoreMode && selectedSeatClassroom" class="batch-actions-inline">
              <label class="select-all-label">
                <input type="checkbox" :checked="selectedSeatIds.length === deletedSeats.length && deletedSeats.length > 0" @change="selectAllDeletedSeats" :disabled="deletedSeats.length === 0" />
                全选
              </label>
              <button class="batch-delete-btn" :class="{ disabled: selectedSeatIds.length === 0 || deletedSeats.length === 0 }" @click="batchRestoreSeats">恢复选中 ({{ selectedSeatIds.length }})</button>
            </div>
          </div>
          <div class="seat-grid-admin" v-if="selectedSeatClassroom">
            <div class="seat-admin" v-for="seat in filteredSeats" :key="seat.id" :class="{ 'has-socket': seat.hasSocket, 'deleted': seat.deleted }">
              <input v-if="seatBatchDeleteMode && !seat.deleted" type="checkbox" :checked="selectedSeatIds.includes(seat.id)" @change="toggleSeatSelect(seat.id)" class="seat-checkbox" />
              <input v-if="seatBatchRestoreMode && seat.deleted" type="checkbox" :checked="selectedSeatIds.includes(seat.id)" @change="toggleSeatSelect(seat.id)" class="seat-checkbox" />
              <div class="seat-content">
                {{ seat.no }}
                <span v-if="seat.hasSocket" class="socket-icon">🔌</span>
              </div>
              <div class="seat-actions">
                <button class="seat-edit-btn" @click="openEditSeatModal(seat)">编辑</button>
                <button class="seat-delete-btn" @click="toggleSeatDeleted(seat)">{{ seat.deleted ? '恢复' : '删除' }}</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'heatmap'">
        <div class="page-header">
          <h2>实时座位热力图</h2>
        </div>
        <div class="heatmap-filters">
          <div class="buildings-row">
            <button 
              v-for="building in adminBuildings" 
              :key="building.id" 
              class="building-btn"
              :class="{ active: heatmapBuilding === building.id }"
              @click="selectHeatmapBuilding(building.id)"
            >
              {{ building.name }}
            </button>
          </div>
          <div v-if="heatmapBuilding" class="seats-classroom-row">
            <button 
              v-for="classroom in filteredHeatmapClassrooms" 
              :key="classroom.id" 
              class="classroom-btn"
              :class="{ active: heatmapClassroom === classroom.id }"
              @click="selectHeatmapClassroom(classroom.id)"
            >
              {{ classroom.name }}
            </button>
            <button class="refresh-btn" @click="refreshHeatmap">刷新</button>
            <div class="heatmap-legend">
              <div class="legend-item"><span class="legend-color available"></span> 空闲</div>
              <div class="legend-item"><span class="legend-color occupied"></span> 占用</div>
              <div class="legend-item"><span class="legend-color reserved"></span> 预约中</div>
            </div>
          </div>
        </div>
        <div class="heatmap-container" v-if="heatmapClassroom">
          <div class="heatmap-grid">
            <div class="heatmap-seat" v-for="seat in heatmapSeats" :key="seat.id" :class="seat.status">
              <span class="seat-no">{{ seat.no }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'blacklist'">
        <div class="page-header">
          <h2>用户管理</h2>
          <button class="import-btn" @click="openImportModal">导入学生</button>
        </div>
        <div class="user-tabs">
          <button class="tab-btn" :class="{ active: userTab === 'all' }" @click="userTab = 'all'">全部用户</button>
          <button class="tab-btn" :class="{ active: userTab === 'blacklist' }" @click="userTab = 'blacklist'">黑名单</button>
        </div>
        <div class="user-list" v-if="userTab === 'all'">
          <div class="user-card" v-for="user in allUsers" :key="user.id">
            <div class="user-info">
              <div class="user-avatar">
                <div class="icon-head"></div>
                <div class="icon-body"></div>
              </div>
              <div class="user-details">
                <h4>{{ user.name }}</h4>
                <p>学号：{{ user.studentId }}</p>
                <p>信誉积分：<span :class="user.creditScore >= 80 ? 'high' : 'low'">{{ user.creditScore }}</span>
                  <button class="edit-credit-btn" @click="openEditCreditModal(user)">修改</button>
                </p>
                <p>违约次数：{{ user.violationCount || 0 }}次</p>
              </div>
            </div>
            <div class="user-status" :class="user.inBlacklist ? 'blacklisted' : 'normal'">
              {{ user.inBlacklist ? (user.blacklistExpireAt ? `限制中(${formatDeletedTime(user.blacklistExpireAt)}到期)` : '已拉黑') : '正常' }}
            </div>
            <div class="user-actions">
              <button v-if="!user.inBlacklist" class="blacklist-btn" @click="addToBlacklist(user.id)">加入黑名单</button>
              <button v-else class="whitelist-btn" @click="removeFromBlacklist(user.id)">解除黑名单</button>
              <button class="delete-user-btn" @click="confirmDeleteUser(user)">删除</button>
            </div>
          </div>
        </div>
        <div class="blacklist-list" v-if="userTab === 'blacklist'">
          <div class="blacklist-card" v-for="user in blacklistedUsers" :key="user.id">
            <div class="user-info">
              <div class="user-avatar">
                <div class="icon-head"></div>
                <div class="icon-body"></div>
              </div>
              <div class="user-details">
                <h4>{{ user.name }}</h4>
                <p>学号：{{ user.studentId }}</p>
                <p>拉黑原因：{{ user.blacklistReason }}</p>
              </div>
            </div>
            <button class="whitelist-btn" @click="removeFromBlacklist(user.id)">解除黑名单</button>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'reports'">
        <div class="page-header">
          <h2>数据报表分析</h2>
          <button class="export-btn" @click="exportReports">导出报表</button>
        </div>
        <div class="reports-tabs">
          <button class="tab-btn" :class="{ active: reportTab === 'daily' }" @click="reportTab = 'daily'">日均使用率</button>
          <button class="tab-btn" :class="{ active: reportTab === 'hourly' }" @click="reportTab = 'hourly'">时段热度</button>
          <button class="tab-btn" :class="{ active: reportTab === 'booking' }" @click="reportTab = 'booking'">预约统计</button>
        </div>
        <div class="chart-container" v-if="reportTab === 'daily'">
          <h3>近7天日均使用率</h3>
          <div class="bar-chart">
            <div class="chart-grid">
              <div class="grid-line" v-for="i in 5" :key="i" :style="{ bottom: (i * 20) + '%' }">
                <span class="grid-label">{{ i * 20 }}%</span>
              </div>
            </div>
            <div class="chart-bars">
              <div class="bar-item" v-for="(data, index) in dailyUsageData" :key="index">
                <div class="bar-wrapper">
                  <div class="bar-value">{{ data.value }}%</div>
                  <div class="bar" :style="{ height: data.value + '%' }"></div>
                </div>
                <div class="bar-label">{{ data.label }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="chart-container" v-if="reportTab === 'hourly'">
          <h3>各时段热度分布</h3>
          <div class="line-chart">
            <div class="chart-line">
              <svg viewBox="0 0 800 400" class="line-svg">
                <defs>
                  <linearGradient id="lineGradient" x1="0%" y1="0%" x2="100%" y2="0%">
                    <stop offset="0%" style="stop-color:#B3B205;stop-opacity:1" />
                    <stop offset="100%" style="stop-color:#D4D33D;stop-opacity:1" />
                  </linearGradient>
                  <linearGradient id="areaGradient" x1="0%" y1="0%" x2="0%" y2="100%">
                    <stop offset="0%" style="stop-color:#B3B205;stop-opacity:0.3" />
                    <stop offset="100%" style="stop-color:#B3B205;stop-opacity:0.05" />
                  </linearGradient>
                </defs>
                <path :d="hourlyAreaPath" fill="url(#areaGradient)"/>
                <path :d="hourlyLinePath" fill="none" stroke="url(#lineGradient)" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                <circle v-for="(point, index) in hourlyDataPoints" :key="index" :cx="point.x" :cy="point.y" r="6" fill="#FFFFFF" stroke="#B3B205" stroke-width="2"/>
                <text v-for="(point, index) in hourlyDataPoints" :key="'label-'+index" :x="point.x" :y="point.y - 15" text-anchor="middle" font-size="11" fill="#B3B205" font-weight="bold">{{ hourlyData[index].value }}%</text>
              </svg>
              <div class="line-labels">
                <span v-for="(label, index) in hourlyLabels" :key="index">{{ label }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="chart-container" v-if="reportTab === 'booking'">
          <h3>预约统计</h3>
          <div class="stats-grid">
            <div class="stat-item">
              <div class="stat-num">{{ totalBookings }}</div>
              <div class="stat-name">总预约数</div>
            </div>
            <div class="stat-item">
              <div class="stat-num">{{ completedBookings }}</div>
              <div class="stat-name">已完成</div>
            </div>
            <div class="stat-item">
              <div class="stat-num">{{ cancelledBookings }}</div>
              <div class="stat-name">已取消</div>
            </div>
            <div class="stat-item">
              <div class="stat-num">{{ timeoutBookings }}</div>
              <div class="stat-name">已超时</div>
            </div>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'settings'">
        <div class="page-header">
          <h2>系统设置</h2>
        </div>
        <div class="settings-form">
          <div class="form-group">
            <label>预约提前时间（分钟）- 需提前30分钟预约</label>
            <input type="number" v-model="settings.advanceMinutes" class="form-input" />
          </div>
          <div class="form-group">
            <label>迟到判定时间（分钟）- 开始后多久未签到算违约</label>
            <input type="number" v-model="settings.signinTimeout" class="form-input" />
          </div>
          <div class="form-group">
            <label>累计违约次数限制（次）- 超过后限制预约</label>
            <input type="number" v-model="settings.violationLimit" class="form-input" />
          </div>
          <div class="form-group">
            <label>违约限制预约天数（天）- 累计违约后的限制时长</label>
            <input type="number" v-model="settings.violationBanDays" class="form-input" />
          </div>
          <div class="form-group">
            <label>开放时间</label>
            <input type="time" v-model="settings.openTime" class="form-input" />
          </div>
          <div class="form-group">
            <label>关闭时间</label>
            <input type="time" v-model="settings.closeTime" class="form-input" />
          </div>
          <button class="save-settings-btn" @click="saveSettings">保存设置</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showAddBuildingModal" @click="closeAddBuildingModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>{{ editingBuilding ? '编辑楼栋' : '添加楼栋' }}</h3>
          <button class="close-btn" @click="closeAddBuildingModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>楼栋名称</label>
            <input type="text" v-model="buildingForm.name" class="form-input" />
          </div>
          <div class="form-group">
            <label>楼栋描述</label>
            <textarea v-model="buildingForm.description" class="form-input"></textarea>
          </div>
          <button class="submit-btn" @click="saveBuilding">{{ editingBuilding ? '保存修改' : '添加楼栋' }}</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showEditClassroomModal" @click="closeEditClassroomModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>编辑教室</h3>
          <button class="close-btn" @click="closeEditClassroomModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>教室名称</label>
            <input type="text" v-model="classroomForm.name" class="form-input" />
          </div>
          <div class="form-group">
            <label>所属楼栋</label>
            <select v-model="classroomForm.buildingId" class="form-input">
              <option v-for="building in store.buildings" :key="building.id" :value="building.id">{{ building.name }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>楼层</label>
            <input type="number" v-model="classroomForm.floor" class="form-input" />
          </div>
          <div class="form-group">
            <label>开放状态</label>
            <select v-model="classroomForm.open" class="form-input">
              <option :value="true">开放</option>
              <option :value="false">关闭</option>
            </select>
          </div>
          <div class="form-group">
            <label>是否有空调</label>
            <select v-model="classroomForm.hasAirConditioner" class="form-input">
              <option :value="true">有</option>
              <option :value="false">无</option>
            </select>
          </div>
          <button class="submit-btn" @click="saveClassroom">保存修改</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showAddClassroomModal" @click="closeAddClassroomModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>添加教室</h3>
          <button class="close-btn" @click="closeAddClassroomModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>教室名称</label>
            <input type="text" v-model="addClassroomForm.name" class="form-input" />
          </div>
          <div class="form-group">
            <label>所属楼栋</label>
            <select v-model="addClassroomForm.buildingId" class="form-input">
              <option value="">请选择楼栋</option>
              <option v-for="building in store.buildings" :key="building.id" :value="building.id">{{ building.name }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>楼层</label>
            <input type="number" v-model="addClassroomForm.floor" class="form-input" />
          </div>
          <div class="form-group">
            <label>开放状态</label>
            <select v-model="addClassroomForm.open" class="form-input">
              <option :value="true">开放</option>
              <option :value="false">关闭</option>
            </select>
          </div>
          <div class="form-group">
            <label>是否有空调</label>
            <select v-model="addClassroomForm.hasAirConditioner" class="form-input">
              <option :value="true">有</option>
              <option :value="false">无</option>
            </select>
          </div>
          <button class="submit-btn" @click="addClassroom">添加教室</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showAddSeatModal" @click="closeAddSeatModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>{{ editingSeat ? '编辑座位' : '添加座位' }}</h3>
          <button class="close-btn" @click="closeAddSeatModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>所属楼栋</label>
            <select v-model="seatForm.buildingId" class="form-input" @change="onSeatFormBuildingChange">
              <option value="">请选择楼栋</option>
              <option v-for="building in store.buildings" :key="building.id" :value="building.id">{{ building.name }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>所属教室</label>
            <select v-model="seatForm.classroomId" class="form-input">
              <option value="">请选择教室</option>
              <option v-for="classroom in seatFormClassrooms" :key="classroom.id" :value="classroom.id">{{ classroom.name }}</option>
            </select>
          </div>
          <div class="form-group">
            <label>座位编号</label>
            <input type="text" v-model="seatForm.no" class="form-input" />
          </div>
          <div class="form-group">
            <label>是否有插座</label>
            <select v-model="seatForm.hasSocket" class="form-input">
              <option :value="true">有</option>
              <option :value="false">无</option>
            </select>
          </div>
          <button class="submit-btn" @click="saveSeat">{{ editingSeat ? '保存修改' : '添加座位' }}</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showImportModal" @click="closeImportModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>导入学生</h3>
          <button class="close-btn" @click="closeImportModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>姓名</label>
            <input v-model="newStudent.name" type="text" class="form-input" placeholder="请输入学生姓名" />
          </div>
          <div class="form-group">
            <label>学号</label>
            <input v-model="newStudent.studentId" type="text" class="form-input" placeholder="请输入学号" />
          </div>
          <div class="form-group">
            <label>电话</label>
            <input v-model="newStudent.phone" type="text" class="form-input" placeholder="请输入电话号码" />
          </div>
          <div class="form-group">
            <label>邮箱</label>
            <input v-model="newStudent.email" type="email" class="form-input" placeholder="请输入邮箱地址" />
          </div>
          <div class="form-group">
            <label>信誉积分</label>
            <input v-model="newStudent.creditScore" type="number" class="form-input" placeholder="100" />
          </div>
          <button class="submit-btn" @click="importStudents">导入</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showImportSeatsModal" @click="closeImportSeatsModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>批量导入座位</h3>
          <button class="close-btn" @click="closeImportSeatsModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>座位数据（JSON格式）</label>
            <textarea v-model="importSeatsData" class="form-input import-textarea" rows="8" placeholder='[{"no": "A-1", "hasSocket": true}, {"no": "A-2", "hasSocket": false}]'></textarea>
          </div>
          <div class="form-group">
            <label>或自动生成座位：</label>
            <div class="auto-generate">
              <label>行数：<input type="number" v-model="autoRows" class="form-input small" min="1" max="26" /></label>
              <label>列数：<input type="number" v-model="autoCols" class="form-input small" min="1" max="50" /></label>
              <button class="generate-btn" @click="generateSeats">自动生成</button>
            </div>
          </div>
          <button class="submit-btn" @click="importSeats">导入座位</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showEditCreditModal" @click="closeEditCreditModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>修改信誉积分</h3>
          <button class="close-btn" @click="closeEditCreditModal">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>{{ editingCreditUser?.name }}（{{ editingCreditUser?.studentId }}）</label>
            <input type="number" v-model="creditScore" class="form-input" min="0" max="100" />
          </div>
          <button class="submit-btn" @click="saveCreditScore">保存</button>
        </div>
      </div>
    </div>
      <Toast 
      :visible="toast.visible" 
      :message="toast.message" 
      :type="toast.type" 
      @close="toast.visible = false" 
    />
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useCampusStore } from '../store/campusStore'
import Toast from './Toast.vue'

const router = useRouter()
const store = useCampusStore()

const currentPage = ref('home')
const resourceTab = ref('buildings')
const userTab = ref('all')
const reportTab = ref('daily')
const classroomView = ref('active')
const buildingView = ref('active')

const buildingBatchMode = ref(false)
const selectedBuildingIds = ref([])
const classroomBatchMode = ref(false)
const selectedClassroomIds = ref([])
const seatBatchDeleteMode = ref(false)
const seatBatchRestoreMode = ref(false)
const selectedSeatIds = ref([])

const toast = reactive({
  visible: false,
  message: '',
  type: 'info'
})

const showToast = (message, type = 'info') => {
  toast.message = message
  toast.type = type
  toast.visible = true
}

const buildingForm = reactive({
  name: '',
  description: ''
})

const classroomForm = reactive({
  id: '',
  name: '',
  buildingId: '',
  floor: 1,
  open: true,
  hasAirConditioner: false
})

const addClassroomForm = reactive({
  name: '',
  buildingId: '',
  floor: 1,
  open: true,
  hasAirConditioner: false
})

const seatForm = reactive({
  buildingId: '',
  classroomId: '',
  no: '',
  hasSocket: false
})

const settings = reactive({
  advanceMinutes: store.systemSettings.advanceMinutes,
  signinTimeout: store.systemSettings.signinTimeout,
  violationLimit: store.systemSettings.violationLimit,
  violationBanDays: store.systemSettings.violationBanDays,
  openTime: store.systemSettings.openTime,
  closeTime: store.systemSettings.closeTime
})

const editingBuilding = ref(null)
const editingSeat = ref(null)
const showAddBuildingModal = ref(false)
const showEditClassroomModal = ref(false)
const showAddClassroomModal = ref(false)
const showAddSeatModal = ref(false)
const showImportModal = ref(false)
const showEditCreditModal = ref(false)
const showImportSeatsModal = ref(false)
const newStudent = reactive({ name: '', studentId: '', phone: '', email: '', creditScore: 100 })
const importSeatsData = ref('')
const autoRows = ref(4)
const autoCols = ref(6)
const editingCreditUser = ref(null)
const creditScore = ref(100)

const selectedSeatBuilding = ref(1)
const selectedSeatClassroom = ref('')
const heatmapBuilding = ref(1)
const heatmapClassroom = ref('')
const selectedClassroomBuilding = ref(null)

const dailyUsageData = [
  { label: '周一', value: 65 },
  { label: '周二', value: 72 },
  { label: '周三', value: 68 },
  { label: '周四', value: 75 },
  { label: '周五', value: 80 },
  { label: '周六', value: 90 },
  { label: '周日', value: 85 }
]

const hourlyData = [
  { hour: '06:00', value: 20 },
  { hour: '08:00', value: 50 },
  { hour: '10:00', value: 70 },
  { hour: '12:00', value: 45 },
  { hour: '14:00', value: 65 },
  { hour: '16:00', value: 75 },
  { hour: '18:00', value: 85 },
  { hour: '20:00', value: 90 },
  { hour: '22:00', value: 60 }
]

const buildingCount = computed(() => store.buildings.length)
const classroomCount = computed(() => store.classrooms.filter(c => !c.deleted).length)
const seatCount = computed(() => {
  let count = 0
  for (const key in store.seats) {
    count += store.seats[key].length
  }
  return count
})
const occupancyRate = computed(() => 72)

const filteredSeatClassrooms = computed(() => {
  if (!selectedSeatBuilding.value) return []
  return store.classrooms.filter(c => c.buildingId === parseInt(selectedSeatBuilding.value) && !c.deleted)
})

const filteredSeats = computed(() => {
  if (!selectedSeatClassroom.value) return []
  return store.seats[selectedSeatClassroom.value] || []
})

const activeSeats = computed(() => {
  return filteredSeats.value.filter(s => !s.deleted)
})

const deletedSeats = computed(() => {
  return filteredSeats.value.filter(s => s.deleted)
})

const filteredHeatmapClassrooms = computed(() => {
  if (!heatmapBuilding.value) return []
  return store.classrooms.filter(c => c.buildingId === parseInt(heatmapBuilding.value) && !c.deleted)
})

const lastRefresh = ref(Date.now())

const heatmapSeats = computed(() => {
  if (!heatmapClassroom.value) return []
  const seats = store.seats[heatmapClassroom.value] || []
  const roomId = parseInt(heatmapClassroom.value)
  
  const now = new Date()
  const today = now.toISOString().split('T')[0]
  const currentTime = now.toTimeString().slice(0, 5)
  
  return seats.map(seat => {
    const booking = store.bookings.find(b => 
      b.roomId === roomId && 
      b.seatId === seat.id && 
      b.date === today &&
      b.status !== 'cancelled' &&
      b.startTime <= currentTime && 
      b.endTime > currentTime
    )
    
    let status = 'available'
    if (booking) {
      if (booking.status === 'completed') {
        status = 'occupied'
      } else if (booking.status === 'waiting_signin' || booking.status === 'not_started') {
        status = 'reserved'
      }
    }
    
    return { ...seat, status }
  })
})

const filteredClassrooms = computed(() => {
  let classrooms = store.classrooms.filter(c => !c.deleted)
  if (selectedClassroomBuilding.value) {
    classrooms = classrooms.filter(c => c.buildingId === parseInt(selectedClassroomBuilding.value))
  }
  return classrooms
})

const deletedClassrooms = computed(() => store.getDeletedClassrooms())

const blacklistedUsers = computed(() => store.users.filter(u => u.inBlacklist))
const allUsers = computed(() => store.users)
const adminBuildings = computed(() => store.buildings.filter(b => !b.deleted))

const filteredBuildings = computed(() => {
  if (buildingView.value === 'active') {
    return store.buildings.filter(b => !b.deleted)
  } else {
    return store.buildings.filter(b => b.deleted)
  }
})

const totalBookings = computed(() => store.bookings.length)
const completedBookings = computed(() => store.bookings.filter(b => b.status === 'completed').length)
const cancelledBookings = computed(() => store.bookings.filter(b => b.status === 'cancelled').length)
const timeoutBookings = computed(() => store.bookings.filter(b => b.status === 'timeout').length)

const hourlyLabels = hourlyData.map(d => d.hour)
const hourlyDataPoints = computed(() => {
  const maxVal = Math.max(...hourlyData.map(d => d.value))
  return hourlyData.map((d, i) => ({
    x: 50 + (i * (700 / (hourlyData.length - 1))),
    y: 350 - ((d.value / maxVal) * 300)
  }))
})

const hourlyLinePath = computed(() => {
  if (hourlyDataPoints.value.length === 0) return ''
  return hourlyDataPoints.value.map((p, i) => `${i === 0 ? 'M' : 'L'} ${p.x} ${p.y}`).join(' ')
})

const hourlyAreaPath = computed(() => {
  if (hourlyDataPoints.value.length === 0) return ''
  const linePath = hourlyLinePath.value
  const lastPoint = hourlyDataPoints.value[hourlyDataPoints.value.length - 1]
  const firstPoint = hourlyDataPoints.value[0]
  return `${linePath} L ${lastPoint.x} 350 L ${firstPoint.x} 350 Z`
})

const getBuildingName = (id) => {
  const building = store.buildings.find(b => b.id === id)
  return building ? building.name : ''
}

const selectSeatBuilding = (buildingId) => {
  selectedSeatBuilding.value = buildingId
  const classrooms = store.classrooms.filter(c => c.buildingId === parseInt(buildingId) && !c.deleted)
  selectedSeatClassroom.value = classrooms.length > 0 ? classrooms[0].id : ''
}

const selectSeatClassroom = (classroomId) => {
  selectedSeatClassroom.value = classroomId
}

watch(resourceTab, (newVal) => {
  if (newVal === 'seats') {
    const classrooms = store.classrooms.filter(c => c.buildingId === selectedSeatBuilding.value && !c.deleted)
    if (classrooms.length > 0) {
      selectedSeatClassroom.value = classrooms[0].id
    }
  }
})

const selectClassroomBuilding = (buildingId) => {
  selectedClassroomBuilding.value = buildingId
}

const selectHeatmapBuilding = (buildingId) => {
  heatmapBuilding.value = buildingId
  const classrooms = store.classrooms.filter(c => c.buildingId === parseInt(buildingId) && !c.deleted)
  if (classrooms.length > 0) {
    heatmapClassroom.value = classrooms[0].id
  } else {
    heatmapClassroom.value = ''
  }
}

const selectHeatmapClassroom = (classroomId) => {
  heatmapClassroom.value = classroomId
}

const refreshHeatmap = () => {
  lastRefresh.value = Date.now()
}

let refreshInterval = null

onMounted(async () => {
  await store.init()
  refreshInterval = setInterval(() => {
    lastRefresh.value = Date.now()
  }, 30000)
  const classrooms = store.classrooms.filter(c => c.buildingId === parseInt(heatmapBuilding.value) && !c.deleted)
  if (classrooms.length > 0) {
    heatmapClassroom.value = classrooms[0].id
  }
})

onUnmounted(() => {
  if (refreshInterval) {
    clearInterval(refreshInterval)
  }
})

const openAddBuildingModal = () => {
  editingBuilding.value = null
  buildingForm.name = ''
  buildingForm.description = ''
  showAddBuildingModal.value = true
}

const openEditBuildingModal = (building) => {
  editingBuilding.value = building
  buildingForm.name = building.name
  buildingForm.description = building.description
  showAddBuildingModal.value = true
}

const closeAddBuildingModal = () => {
  showAddBuildingModal.value = false
}

const saveBuilding = async () => {
  if (!buildingForm.name) {
    showToast('请输入楼栋名称', 'warning')
    return
  }
  try {
    if (editingBuilding.value) {
      await store.updateBuilding(editingBuilding.value.id, {
        name: buildingForm.name,
        description: buildingForm.description
      })
    } else {
      await store.addBuilding({
        name: buildingForm.name,
        description: buildingForm.description
      })
    }
    closeAddBuildingModal()
    showToast('保存成功', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const deleteBuilding = async (id) => {
  if (confirm('确定要删除该楼栋吗？')) {
    try {
      await store.deleteBuilding(id)
      showToast('删除成功', 'success')
    } catch (e) {
      showToast('删除失败，请稍后重试', 'error')
    }
  }
}

const restoreBuilding = async (id) => {
  if (confirm('确定要恢复该楼栋吗？')) {
    try {
      await store.restoreBuilding(id)
      showToast('恢复成功', 'success')
    } catch (e) {
      showToast('恢复失败，请稍后重试', 'error')
    }
  }
}

const toggleBuildingBatchMode = () => {
  buildingBatchMode.value = !buildingBatchMode.value
  if (!buildingBatchMode.value) {
    selectedBuildingIds.value = []
  }
}

const toggleBuildingSelect = (id) => {
  const index = selectedBuildingIds.value.indexOf(id)
  if (index === -1) {
    selectedBuildingIds.value.push(id)
  } else {
    selectedBuildingIds.value.splice(index, 1)
  }
}

const selectAllBuildings = () => {
  if (selectedBuildingIds.value.length === filteredBuildings.value.length) {
    selectedBuildingIds.value = []
  } else {
    selectedBuildingIds.value = filteredBuildings.value.map(b => b.id)
  }
}

const batchDeleteBuildings = async () => {
  if (selectedBuildingIds.value.length === 0) return
  if (confirm(`确定要删除选中的 ${selectedBuildingIds.value.length} 个楼栋吗？`)) {
    try {
      await store.batchDeleteBuildings(selectedBuildingIds.value)
      showToast(`成功删除 ${selectedBuildingIds.value.length} 个楼栋`, 'success')
    } catch (e) {
      showToast('批量删除失败，请稍后重试', 'error')
    }
    selectedBuildingIds.value = []
    buildingBatchMode.value = false
  }
}

const batchRestoreBuildings = async () => {
  if (selectedBuildingIds.value.length === 0) return
  if (confirm(`确定要恢复选中的 ${selectedBuildingIds.value.length} 个楼栋吗？`)) {
    try {
      await store.batchRestoreBuildings(selectedBuildingIds.value)
      showToast(`成功恢复 ${selectedBuildingIds.value.length} 个楼栋`, 'success')
    } catch (e) {
      showToast('批量恢复失败，请稍后重试', 'error')
    }
    selectedBuildingIds.value = []
    buildingBatchMode.value = false
  }
}

const toggleClassroomBatchMode = () => {
  classroomBatchMode.value = !classroomBatchMode.value
  if (!classroomBatchMode.value) {
    selectedClassroomIds.value = []
  }
}

const toggleClassroomSelect = (id) => {
  const index = selectedClassroomIds.value.indexOf(id)
  if (index === -1) {
    selectedClassroomIds.value.push(id)
  } else {
    selectedClassroomIds.value.splice(index, 1)
  }
}

const selectAllClassrooms = () => {
  const targetClassrooms = classroomView.value === 'active' ? filteredClassrooms.value : deletedClassrooms.value
  if (selectedClassroomIds.value.length === targetClassrooms.length) {
    selectedClassroomIds.value = []
  } else {
    selectedClassroomIds.value = targetClassrooms.map(c => c.id)
  }
}

const batchDeleteClassrooms = async () => {
  if (selectedClassroomIds.value.length === 0) return
  if (confirm(`确定要删除选中的 ${selectedClassroomIds.value.length} 个教室吗？`)) {
    try {
      await store.batchDeleteClassrooms(selectedClassroomIds.value)
      showToast(`成功删除 ${selectedClassroomIds.value.length} 个教室`, 'success')
    } catch (e) {
      showToast('批量删除失败，请稍后重试', 'error')
    }
    selectedClassroomIds.value = []
    classroomBatchMode.value = false
  }
}

const batchRestoreClassrooms = async () => {
  if (selectedClassroomIds.value.length === 0) return
  if (confirm(`确定要恢复选中的 ${selectedClassroomIds.value.length} 个教室吗？`)) {
    try {
      await store.batchRestoreClassrooms(selectedClassroomIds.value)
      showToast(`成功恢复 ${selectedClassroomIds.value.length} 个教室`, 'success')
    } catch (e) {
      showToast('批量恢复失败，请稍后重试', 'error')
    }
    selectedClassroomIds.value = []
    classroomBatchMode.value = false
  }
}

const toggleSeatBatchDeleteMode = () => {
  seatBatchDeleteMode.value = !seatBatchDeleteMode.value
  if (!seatBatchDeleteMode.value) {
    selectedSeatIds.value = []
  }
  seatBatchRestoreMode.value = false
}

const toggleSeatBatchRestoreMode = () => {
  seatBatchRestoreMode.value = !seatBatchRestoreMode.value
  if (!seatBatchRestoreMode.value) {
    selectedSeatIds.value = []
  }
  seatBatchDeleteMode.value = false
}

const toggleSeatSelect = (id) => {
  const index = selectedSeatIds.value.indexOf(id)
  if (index === -1) {
    selectedSeatIds.value.push(id)
  } else {
    selectedSeatIds.value.splice(index, 1)
  }
}

const selectAllActiveSeats = () => {
  const activeSeatsList = filteredSeats.value.filter(s => !s.deleted)
  if (selectedSeatIds.value.length === activeSeatsList.length) {
    selectedSeatIds.value = []
  } else {
    selectedSeatIds.value = activeSeatsList.map(s => s.id)
  }
}

const selectAllDeletedSeats = () => {
  const deletedSeatsList = filteredSeats.value.filter(s => s.deleted)
  if (selectedSeatIds.value.length === deletedSeatsList.length) {
    selectedSeatIds.value = []
  } else {
    selectedSeatIds.value = deletedSeatsList.map(s => s.id)
  }
}

const batchDeleteSeats = async () => {
  if (selectedSeatIds.value.length === 0) return
  if (confirm(`确定要删除选中的 ${selectedSeatIds.value.length} 个座位吗？`)) {
    try {
      await store.batchDeleteSeats(selectedSeatClassroom.value, selectedSeatIds.value)
      showToast(`成功删除 ${selectedSeatIds.value.length} 个座位`, 'success')
    } catch (e) {
      showToast('批量删除失败，请稍后重试', 'error')
    }
    selectedSeatIds.value = []
    seatBatchDeleteMode.value = false
  }
}

const batchRestoreSeats = async () => {
  if (selectedSeatIds.value.length === 0) return
  if (confirm(`确定要恢复选中的 ${selectedSeatIds.value.length} 个座位吗？`)) {
    try {
      await store.batchRestoreSeats(selectedSeatClassroom.value, selectedSeatIds.value)
      showToast(`成功恢复 ${selectedSeatIds.value.length} 个座位`, 'success')
    } catch (e) {
      showToast('批量恢复失败，请稍后重试', 'error')
    }
    selectedSeatIds.value = []
    seatBatchRestoreMode.value = false
  }
}

const formatDeletedTime = (isoString) => {
  if (!isoString) return ''
  const date = new Date(isoString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

const openEditClassroomModal = (classroom) => {
  classroomForm.id = classroom.id
  classroomForm.name = classroom.name
  classroomForm.buildingId = classroom.buildingId
  classroomForm.floor = classroom.floor
  classroomForm.open = classroom.open
  classroomForm.hasAirConditioner = classroom.hasAirConditioner
  showEditClassroomModal.value = true
}

const closeEditClassroomModal = () => {
  showEditClassroomModal.value = false
}

const saveClassroom = async () => {
  if (!classroomForm.name) {
    showToast('请输入教室名称', 'warning')
    return
  }
  try {
    const classroom = store.classrooms.find(c => c.id === classroomForm.id)
    if (classroom) {
      await store.updateClassroom(classroom.id, {
        name: classroomForm.name,
        buildingId: classroomForm.buildingId,
        floor: classroomForm.floor,
        open: classroomForm.open,
        hasAirConditioner: classroomForm.hasAirConditioner
      })
    }
    closeEditClassroomModal()
    showToast('保存成功', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const deleteClassroom = async (id) => {
  if (confirm('确定要删除该教室吗？')) {
    try {
      await store.deleteClassroom(id)
      showToast('删除成功', 'success')
    } catch (e) {
      showToast('删除失败，请稍后重试', 'error')
    }
  }
}

const toggleClassroomStatus = async (classroom) => {
  try {
    await store.updateClassroom(classroom.id, { open: !classroom.open })
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const restoreClassroom = async (id) => {
  if (confirm('确定要恢复该教室吗？')) {
    try {
      await store.restoreClassroom(id)
      showToast('恢复成功', 'success')
    } catch (e) {
      showToast('恢复失败，请稍后重试', 'error')
    }
  }
}

const openAddClassroomModal = () => {
  addClassroomForm.name = ''
  addClassroomForm.buildingId = selectedClassroomBuilding.value
  addClassroomForm.floor = 1
  addClassroomForm.open = true
  showAddClassroomModal.value = true
}

const closeAddClassroomModal = () => {
  showAddClassroomModal.value = false
}

const addClassroom = async () => {
  if (!addClassroomForm.name || !addClassroomForm.buildingId) {
    showToast('请填写完整信息', 'warning')
    return
  }
  try {
    await store.addClassroom({
      name: addClassroomForm.name,
      buildingId: parseInt(addClassroomForm.buildingId),
      floor: addClassroomForm.floor,
      open: addClassroomForm.open,
      hasAirConditioner: addClassroomForm.hasAirConditioner
    })
    closeAddClassroomModal()
    showToast('添加成功', 'success')
  } catch (e) {
    showToast('添加失败，请稍后重试', 'error')
  }
}

const openAddSeatModal = () => {
  editingSeat.value = null
  seatForm.buildingId = selectedSeatBuilding.value
  seatForm.classroomId = selectedSeatClassroom.value
  seatForm.no = ''
  seatForm.hasSocket = false
  showAddSeatModal.value = true
}

const openEditSeatModal = (seat) => {
  editingSeat.value = seat
  seatForm.buildingId = selectedSeatBuilding.value
  seatForm.classroomId = selectedSeatClassroom.value
  seatForm.no = seat.no
  seatForm.hasSocket = seat.hasSocket
  showAddSeatModal.value = true
}

const closeAddSeatModal = () => {
  showAddSeatModal.value = false
}

const openImportSeatsModal = () => {
  importSeatsData.value = ''
  autoRows.value = 4
  autoCols.value = 6
  showImportSeatsModal.value = true
}

const closeImportSeatsModal = () => {
  showImportSeatsModal.value = false
}

const generateSeats = () => {
  const rows = autoRows.value || 4
  const cols = autoCols.value || 6
  const seats = []
  for (let i = 0; i < rows; i++) {
    const row = String.fromCharCode(65 + i)
    for (let j = 1; j <= cols; j++) {
      seats.push({
        no: `${row}-${j}`,
        hasSocket: j % 4 === 0 || j % 4 === 1
      })
    }
  }
  importSeatsData.value = JSON.stringify(seats, null, 2)
}

const importSeats = async () => {
  if (!importSeatsData.value.trim()) {
    showToast('请输入座位数据或自动生成座位', 'warning')
    return
  }
  try {
    const seats = JSON.parse(importSeatsData.value)
    const addedCount = await store.importSeats(parseInt(selectedSeatClassroom.value), seats)
    closeImportSeatsModal()
    showToast(`成功导入 ${addedCount} 个座位（重复座位已跳过）`, 'success')
  } catch (e) {
    showToast('导入失败，请检查数据格式或稍后重试', 'error')
  }
}

const onSeatFormBuildingChange = () => {
  seatForm.classroomId = ''
}

const seatFormClassrooms = computed(() => {
  if (!seatForm.buildingId) return []
  return store.classrooms.filter(c => c.buildingId === parseInt(seatForm.buildingId) && !c.deleted)
})

const saveSeat = async () => {
  if (!seatForm.classroomId || !seatForm.no) {
    showToast('请填写完整信息', 'warning')
    return
  }
  try {
    if (editingSeat.value) {
      await store.updateSeat(seatForm.classroomId, editingSeat.value.id, {
        no: seatForm.no,
        hasSocket: seatForm.hasSocket
      })
    } else {
      await store.addSeat(seatForm.classroomId, {
        no: seatForm.no,
        hasSocket: seatForm.hasSocket
      })
    }
    closeAddSeatModal()
    showToast('保存成功', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const toggleSeatDeleted = async (seat) => {
  try {
    if (seat.deleted) {
      await store.restoreSeat(selectedSeatClassroom.value, seat.id)
    } else {
      await store.deleteSeat(selectedSeatClassroom.value, seat.id)
    }
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const addToBlacklist = async (id) => {
  try {
    await store.addToBlacklist(id, '管理员手动拉黑')
    showToast('已加入黑名单', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const removeFromBlacklist = async (id) => {
  try {
    await store.removeFromBlacklist(id)
    showToast('已解除黑名单', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const exportReports = () => {
  const reportData = {
    dailyUsage: dailyUsageData,
    hourlyData: hourlyData,
    totalBookings: totalBookings.value,
    completedBookings: completedBookings.value,
    cancelledBookings: cancelledBookings.value,
    timeoutBookings: timeoutBookings.value,
    exportTime: new Date().toISOString()
  }
  const blob = new Blob([JSON.stringify(reportData, null, 2)], { type: 'application/json' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `reports_${new Date().toISOString().split('T')[0]}.json`
  a.click()
  URL.revokeObjectURL(url)
}

const openImportModal = () => {
  newStudent.name = ''
  newStudent.studentId = ''
  newStudent.phone = ''
  newStudent.email = ''
  newStudent.creditScore = 100
  showImportModal.value = true
}

const closeImportModal = () => {
  showImportModal.value = false
}

const importStudents = async () => {
  if (!newStudent.name || !newStudent.studentId) {
    showToast('请输入姓名和学号', 'warning')
    return
  }
  try {
    await store.addUser(newStudent)
    closeImportModal()
    showToast('添加成功', 'success')
  } catch (e) {
    showToast('添加失败，请稍后重试', 'error')
  }
}

const openEditCreditModal = (user) => {
  editingCreditUser.value = user
  creditScore.value = user.creditScore
  showEditCreditModal.value = true
}

const closeEditCreditModal = () => {
  showEditCreditModal.value = false
}

const saveCreditScore = async () => {
  if (!editingCreditUser.value) return
  try {
    await store.updateCreditScore(editingCreditUser.value.id, creditScore.value)
    closeEditCreditModal()
    showToast('信誉积分修改成功', 'success')
  } catch (e) {
    showToast('操作失败，请稍后重试', 'error')
  }
}

const confirmDeleteUser = async (user) => {
  if (confirm(`确定要删除学生 ${user.name}（学号：${user.studentId}）吗？此操作不可恢复！`)) {
    try {
      await store.deleteUser(user.id)
      showToast('删除成功', 'success')
    } catch (e) {
      showToast('删除失败，请稍后重试', 'error')
    }
  }
}

const saveSettings = async () => {
  try {
    await store.updateSystemSettings({
      advanceMinutes: settings.advanceMinutes,
      signinTimeout: settings.signinTimeout,
      violationLimit: settings.violationLimit,
      violationBanDays: settings.violationBanDays,
      openTime: settings.openTime,
      closeTime: settings.closeTime
    })
    showToast('设置保存成功', 'success')
  } catch (e) {
    showToast('保存失败，请稍后重试', 'error')
  }
}

const handleLogout = () => {
  router.push('/')
}
</script>

<style scoped>
.admin-page {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #F2F4DB;
}

.header-bar {
  height: 60px;
  background: #3B3925;
  display: flex;
  align-items: center;
  padding: 0 24px;
  justify-content: space-between;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.header-icon .icon-circle {
  width: 20px;
  height: 20px;
  background: #FFFEC1;
  border-radius: 50%;
}

.header-icon .icon-triangle {
  width: 30px;
  height: 22px;
  background: #FFFEC1;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.header-text {
  color: #FFFEC1;
  font-size: 18px;
  font-weight: bold;
}

.logout-btn {
  background: none;
  color: #FFFEC1;
  border: 1px solid #FFFEC1;
  padding: 6px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.logout-btn:hover {
  background: rgba(255, 254, 193, 0.1);
}

.content {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar {
  width: 200px;
  background: #FFFFFF;
  display: flex;
  flex-direction: column;
  padding-top: 20px;
  border-right: 1px solid #CAC27D;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 20px;
  color: #626363;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.menu-item:hover {
  background: #F2F4DB;
}

.menu-item.active {
  background: #CAC27D;
  color: #3B3925;
}

.main-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.page-header h2 {
  color: #3B3925;
  font-size: 24px;
}

.add-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.add-btn:hover {
  background: #9a9904;
}

.dashboard-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 32px;
}

.stat-card {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.stat-icon {
  font-size: 36px;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #3B3925;
}

.stat-label {
  font-size: 14px;
  color: #626363;
}

.dashboard-section {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.dashboard-section h3 {
  color: #3B3925;
  margin-bottom: 16px;
}

.booking-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.booking-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  background: #F2F4DB;
  border-radius: 8px;
}

.booking-info {
  display: flex;
  gap: 16px;
}

.room-name {
  font-weight: bold;
  color: #3B3925;
}

.seat-no {
  color: #626363;
}

.booking-time {
  color: #626363;
}

.booking-status {
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
}

.booking-status.completed {
  background: #E8F5E9;
  color: #4CAF50;
}

.booking-status.waiting_signin {
  background: #FFF3E0;
  color: #FF9800;
}

.booking-status.not_started {
  background: #E3F2FD;
  color: #2196F3;
}

.resource-tabs, .user-tabs, .reports-tabs {
  display: flex;
  gap: 12px;
  align-items: center;
  margin-bottom: 12px;
}

.classroom-tabs {
  display: flex;
  gap: 12px;
  align-items: center;
  margin-bottom: 8px;
}

.classroom-filter-wrapper {
  margin-bottom: 0;
}

.buildings-row-inline {
  display: flex;
  gap: 12px;
  flex-wrap: nowrap;
  overflow-x: auto;
}

.classroom-actions-group {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.classroom-filter {
  display: flex;
}

.tab-btn {
  background: #FFFFFF;
  color: #626363;
  border: 1px solid #CAC27D;
  padding: 8px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.tab-btn.active {
  background: #B3B205;
  color: #FFFFFF;
  border-color: #B3B205;
}

.buildings-list, .classrooms-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-top: 20px;
}

.deleted-time {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.batch-actions {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 16px;
  padding: 12px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 12px;
}

.batch-actions-inline {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-left: auto;
  flex-shrink: 0;
}

.select-all-label {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #3B3925;
  cursor: pointer;
}

.select-all-label input[type="checkbox"] {
  width: 16px;
  height: 16px;
  cursor: pointer;
}

.batch-delete-btn {
  padding: 6px 12px;
  background-color: #E53935;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  transition: background-color 0.2s;
}

.batch-delete-btn:hover:not(.disabled) {
  background-color: #C62828;
}

.batch-delete-btn.disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.batch-checkbox {
  width: 16px;
  height: 16px;
  cursor: pointer;
  margin-right: 8px;
  flex-shrink: 0;
  align-self: center;
}

.building-card, .classroom-card {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  flex-direction: row;
}

.building-info, .classroom-info {
  flex: 1;
}

.building-info h4, .classroom-info h4 {
  color: #3B3925;
  margin-bottom: 8px;
}

.building-info p, .classroom-info p {
  color: #626363;
  font-size: 14px;
}

.classroom-status {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
  margin-top: 8px;
}

.classroom-status.open {
  background: #E8F5E9;
  color: #4CAF50;
}

.classroom-status.closed {
  background: #FFEBEE;
  color: #F44336;
}

.building-actions, .classroom-actions {
  display: flex;
  gap: 8px;
}

.building-actions .edit-btn, .classroom-actions .edit-btn {
  background: none;
  color: #B3B205;
  border: 1px solid #B3B205;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.building-actions .delete-btn, .classroom-actions .delete-btn {
  background: none;
  color: #F44336;
  border: 1px solid #F44336;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.classroom-actions .toggle-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.seats-building-row {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
  flex-wrap: nowrap;
  overflow-x: auto;
}

.buildings-row {
  display: flex;
  gap: 12px;
  flex-wrap: nowrap;
  overflow-x: auto;
}

.building-btn {
  background: #E0E0E0;
  color: #666666;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
  width: 160px;
  min-width: 160px;
  max-width: 160px;
  flex-shrink: 0;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.building-btn.active {
  background: #B3B205;
  color: #FFFFFF;
  text-decoration: underline;
}

.seats-classroom-row {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
  flex-wrap: wrap;
  align-items: center;
}

.classroom-btn {
  background: #FFFFFF;
  color: #626363;
  border: 1px solid #CAC27D;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.classroom-btn.active {
  background: #B3B205;
  color: #FFFFFF;
  border-color: #B3B205;
}

.filter-select {
  padding: 8px 12px;
  border: 1px solid #CAC27D;
  border-radius: 6px;
  background: #FFFFFF;
  color: #3B3925;
}

.seat-grid-admin {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 12px;
}

.seat-admin {
  background: #FFFFFF;
  border: 2px solid #66BB6A;
  border-radius: 8px;
  padding: 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  position: relative;
}

.seat-checkbox {
  position: absolute;
  top: 4px;
  left: 4px;
  width: 14px;
  height: 14px;
  cursor: pointer;
}

.seat-admin.has-socket {
  background: #E8F5E9;
  border-color: #66BB6A;
}

.seat-admin.deleted {
  background: #F5F5F5;
  opacity: 0.6;
}

.seat-content {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #3B3925;
  font-weight: 500;
}

.socket-icon {
  font-size: 12px;
}

.seat-actions {
  display: flex;
  gap: 6px;
}

.seat-edit-btn {
  background: none;
  color: #B3B205;
  border: 1px solid #B3B205;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.seat-delete-btn {
  background: none;
  color: #F44336;
  border: 1px solid #F44336;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.heatmap-filters {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 24px;
}

.refresh-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
}

.refresh-time {
  margin-left: 16px;
  font-size: 14px;
  color: #888888;
  line-height: 36px;
}

.heatmap-container {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.heatmap-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 12px;
  margin-bottom: 24px;
}

.heatmap-seat {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.2s;
}

.heatmap-seat:hover {
  transform: scale(1.1);
}

.heatmap-seat.available {
  background: #E8F5E9;
  border: 2px solid #66BB6A;
}

.heatmap-seat.occupied {
  background: #FFEBEE;
  border: 2px solid #EF5350;
}

.heatmap-seat.reserved {
  background: #FFF3E0;
  border: 2px solid #FFA726;
}

.seat-no {
  font-size: 12px;
  color: #3B3925;
}

.heatmap-legend {
  display: flex;
  gap: 16px;
  align-items: center;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #626363;
}

.legend-color {
  width: 18px;
  height: 18px;
  border-radius: 4px;
  border-width: 2px;
  border-style: solid;
}

.legend-color.available {
  background: #E8F5E9;
  border-color: #66BB6A;
}

.legend-color.occupied {
  background: #FFEBEE;
  border-color: #EF5350;
}

.legend-color.reserved {
  background: #FFF3E0;
  border-color: #FFA726;
}

.legend-color.socket {
  background: #E8F5E9;
  border-color: #66BB6A;
}

.legend-color.no-socket {
  background: #FFFFFF;
  border-color: #66BB6A;
}

.seat-legend {
  display: flex;
  gap: 16px;
  align-items: center;
  flex-shrink: 0;
}

.user-list, .blacklist-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.user-card, .blacklist-card {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.user-info {
  display: flex;
  gap: 16px;
}

.user-avatar {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-avatar .icon-head {
  width: 40px;
  height: 40px;
  background: #CAC27D;
  border-radius: 50%;
}

.user-avatar .icon-body {
  width: 56px;
  height: 32px;
  background: #CAC27D;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.user-details h4 {
  color: #3B3925;
  margin-bottom: 4px;
}

.user-details p {
  color: #626363;
  font-size: 14px;
}

.user-details p span.high {
  color: #4CAF50;
}

.user-details p span.low {
  color: #F44336;
}

.user-status {
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
}

.user-status.normal {
  background: #E8F5E9;
  color: #4CAF50;
}

.user-status.blacklisted {
  background: #FFEBEE;
  color: #F44336;
}

.blacklist-btn {
  background: #F44336;
  color: #FFFFFF;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.whitelist-btn {
  background: #4CAF50;
  color: #FFFFFF;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.user-actions {
  display: flex;
  gap: 8px;
}

.edit-credit-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
  margin-left: 8px;
}

.delete-user-btn {
  background: #626363;
  color: #FFFFFF;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.export-btn, .import-btn {
  background: #653005;
  color: #FFFFFF;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.export-btn:hover, .import-btn:hover {
  background: #4a2304;
}

.import-textarea {
  width: 100%;
  box-sizing: border-box;
}

.auto-generate {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.auto-generate .form-input.small {
  width: 80px;
}

.generate-btn {
  background: #653005;
  color: #FFFFFF;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.generate-btn:hover {
  background: #4a2304;
}

.chart-container {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 24px 24px 24px 60px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.chart-container h3 {
  color: #3B3925;
  margin-bottom: 24px;
}

.bar-chart {
  height: 350px;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  position: relative;
}

.chart-grid {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 40px;
  pointer-events: none;
}

.grid-line {
  position: absolute;
  left: 0;
  right: 0;
  border-top: 1px dashed #E0E0E0;
}

.grid-label {
  position: absolute;
  left: -45px;
  top: -10px;
  font-size: 10px;
  color: #9E9E9E;
}

.chart-bars {
  display: flex;
  justify-content: space-around;
  align-items: stretch;
  height: calc(100% - 40px);
  padding: 0 20px;
}

.bar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  flex: 1;
  position: relative;
}

.bar-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  justify-content: flex-end;
}

.bar {
  width: 50px;
  min-height: 5px;
  background: linear-gradient(to top, #B3B205, #D4D33D);
  border-radius: 8px 8px 0 0;
  transition: height 0.5s ease;
  box-shadow: 0 2px 4px rgba(179, 178, 5, 0.3);
}

.bar:hover {
  background: linear-gradient(to top, #D4D33D, #E8E76B);
  transform: scaleX(1.1);
}

.bar-label {
  font-size: 12px;
  color: #626363;
  font-weight: 500;
  margin-top: 8px;
}

.bar-value {
  font-size: 11px;
  color: #B3B205;
  font-weight: bold;
  position: absolute;
  top: -20px;
}

.line-chart {
  height: 350px;
  display: flex;
  flex-direction: column;
}

.chart-line {
  position: relative;
  height: calc(100% - 40px);
  background: linear-gradient(to bottom, rgba(179, 178, 5, 0.1), transparent);
  border-radius: 8px;
  padding: 20px;
}

.line-svg {
  width: 100%;
  height: 100%;
}

.line-labels {
  display: flex;
  justify-content: space-around;
  padding-top: 10px;
}

.line-labels span {
  font-size: 12px;
  color: #626363;
  font-weight: 500;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.stat-item {
  text-align: center;
  padding: 28px 20px;
  background: linear-gradient(135deg, #F2F4DB 0%, #FEFEF0 100%);
  border-radius: 16px;
  border: 1px solid #E8E9C9;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.stat-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(179, 178, 5, 0.15);
}

.stat-num {
  font-size: 40px;
  font-weight: bold;
  color: #B3B205;
  background: linear-gradient(135deg, #B3B205 0%, #D4D33D 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stat-name {
  font-size: 14px;
  color: #626363;
  margin-top: 8px;
}

.settings-form {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 32px;
}

.settings-form button {
  grid-column: span 2;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  color: #3B3925;
  margin-bottom: 8px;
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #CAC27D;
  border-radius: 6px;
  font-size: 14px;
  color: #3B3925;
}

.save-settings-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  width: 100%;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 24px;
  width: 400px;
  max-height: 80vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.modal-header h3 {
  color: #3B3925;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  color: #626363;
  cursor: pointer;
}

.modal-body {
  display: flex;
  flex-direction: column;
}

.submit-btn {
  background: #B3B205;
  color: #FFFFFF;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  margin-top: 20px;
}
</style>