<template>
  <div class="student-page">
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
        <div class="menu-item" :class="{ active: currentPage === 'booking' }" @click="currentPage = 'booking'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect width="18" height="18" x="3" y="3" rx="2"/>
            <line x1="9" x2="15" y1="9" y2="9"/>
            <line x1="9" x2="15" y1="15" y2="15"/>
          </svg>
          <span>座位预约</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'myBookings' }" @click="currentPage = 'myBookings'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
            <polyline points="14 2 14 8 20 8"/>
            <line x1="16" x2="8" y1="13" y2="13"/>
            <line x1="16" x2="8" y1="17" y2="17"/>
          </svg>
          <span>我的预约</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'records' }" @click="currentPage = 'records'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="12 6 12 12 16 14"/>
          </svg>
          <span>违约记录</span>
        </div>
        <div class="menu-item" :class="{ active: currentPage === 'profile' }" @click="currentPage = 'profile'">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
            <circle cx="12" cy="7" r="4"/>
          </svg>
          <span>个人信息</span>
        </div>
      </div>
      <div class="main-content" v-if="currentPage === 'home'">
        <div class="welcome-banner">
          <div class="banner-content">
            <h2>欢迎使用智能自习室预约管理系统！</h2>
            <p>灵活预约，智慧选座，便捷签到，高效学习</p>
            <button class="booking-btn" @click="currentPage = 'booking'">立即预约</button>
          </div>
          <div class="banner-icon">
            <div class="icon-head"></div>
            <div class="icon-body"></div>
          </div>
        </div>
        <div class="recent-bookings">
          <h3>最近预约</h3>
          <div class="booking-table">
            <div class="table-header">
              <span>座位编号</span>
              <span>预约时间</span>
              <span>状态</span>
            </div>
            <div class="table-body">
              <div class="table-row" v-for="booking in recentBookingsList" :key="booking.id" @click="openBookingDetail(booking)">
                <span>{{ booking.seatNo }}</span>
                <span>{{ booking.date }} {{ booking.startTime }} - {{ booking.endTime }}</span>
                <span class="status" :class="booking.status">{{ getBookingStatus(booking) }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="booking-rules">
          <h3>预约规则</h3>
          <div class="rules-content">
            <ul>
              <li>预约时间需提前30分钟，迟到15分钟视为违约</li>
              <li>预约时长不限，每天预约次数不限</li>
              <li>预约开放时间：每天6:00 ~ 23:59</li>
              <li>如需取消预约，请在预约开始前操作</li>
              <li>累计违约3次将被限制预约权限7天</li>
              <li>自习室内请保持安静，遵守自习室管理规定</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'booking'">
        <div class="page-header">
          <h2>座位预约</h2>
        </div>
        <div class="booking-area">
          <div class="booking-filters">
            <div class="filter-group">
              <label>选择楼栋</label>
              <select v-model="selectedBuilding" class="filter-select" @change="onBuildingChange">
                <option value="">请选择楼栋</option>
                <option v-for="building in buildings" :key="building.id" :value="building.id">{{ building.name }}</option>
              </select>
            </div>
            <div class="filter-group">
              <label>选择教室</label>
              <select v-model="selectedClassroom" class="filter-select" @change="onClassroomChange">
                <option value="">请选择教室</option>
                <option v-for="classroom in filteredClassrooms" :key="classroom.id" :value="classroom.id">{{ classroom.name }} {{ classroom.hasAirConditioner ? '❄️' : '' }}</option>
              </select>
            </div>
            <div class="filter-group">
              <label>预约日期</label>
              <input type="date" v-model="bookingForm.date" class="filter-select" :min="today" :max="maxDate" />
            </div>
            <div class="filter-group">
              <label>开始时间</label>
              <input type="time" v-model="bookingForm.startTime" class="filter-select" :min="store.systemSettings.openTime" :max="store.systemSettings.closeTime" />
            </div>
            <div class="filter-group">
              <label>结束时间</label>
              <input type="time" v-model="bookingForm.endTime" class="filter-select" :min="store.systemSettings.openTime" :max="store.systemSettings.closeTime" />
            </div>
          </div>
          <div class="classroom-status" v-if="selectedClassroom">
            <div class="status-indicator" :class="currentClassroom?.open ? 'open' : 'closed'">
              {{ currentClassroom?.open ? '开放' : '关闭' }}
            </div>
            <span v-if="currentClassroom?.hasAirConditioner" class="ac-indicator">❄️ 有空调</span>
            <span v-else class="ac-indicator no-ac">无空调</span>
          </div>
          <div class="seat-grid" v-if="selectedClassroom && currentClassroom?.open">
            <div 
              class="seat" 
              v-for="seat in currentSeats" 
              :key="seat.id"
              :class="{ 
                occupied: isSeatOccupied(seat), 
                selected: seat.id === selectedSeat,
                'has-socket': seat.hasSocket
              }"
              @click="selectSeat(seat)"
            >
              {{ seat.no }}
              <span v-if="seat.hasSocket" class="socket-icon">🔌</span>
            </div>
          </div>
          <div class="no-seats" v-else-if="selectedClassroom && !currentClassroom?.open">
            <p>该教室当前不开放</p>
          </div>
          <div class="seat-legend">
            <div class="legend-item"><span class="legend-color available"></span> 可用</div>
            <div class="legend-item"><span class="legend-color occupied"></span> 已占用</div>
            <div class="legend-item"><span class="legend-color selected"></span> 已选</div>
            <div class="legend-item"><span>🔌</span> 带插座</div>
          </div>
          <div class="booking-form" v-if="selectedSeat">
            <div class="form-group">
              <label>座位编号</label>
              <input type="text" :value="getSelectedSeatInfo?.no" disabled class="form-input" />
            </div>
            <div class="form-group">
              <label>预约日期</label>
              <input type="text" :value="bookingForm.date" disabled class="form-input" />
            </div>
            <div class="form-group">
              <label>开始时间</label>
              <input type="text" :value="bookingForm.startTime" disabled class="form-input" />
            </div>
            <div class="form-group">
              <label>结束时间</label>
              <input type="text" :value="bookingForm.endTime" disabled class="form-input" />
            </div>
            <button class="submit-btn" @click="submitBooking">确认预约</button>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'myBookings'">
        <div class="page-header">
          <h2>我的预约</h2>
        </div>
        <div class="my-bookings-list">
          <div class="booking-card" v-for="booking in myBookings" :key="booking.id" @click="openBookingDetail(booking)">
            <div class="card-header">
              <span class="room-name">{{ booking.roomName }}</span>
              <span class="seat-no">座位号：{{ booking.seatNo }}</span>
            </div>
            <div class="card-body">
              <div class="booking-date">{{ booking.date }}</div>
              <div class="booking-time">{{ booking.startTime }} - {{ booking.endTime }}</div>
            </div>
            <div class="card-footer">
              <span class="booking-status" :class="booking.status">{{ getBookingStatus(booking) }}</span>
              <button v-if="canCancel(booking)" class="cancel-btn" @click.stop="cancelBooking(booking.id)">取消预约</button>
              <button v-if="canReleaseSeat(booking)" class="release-btn" @click.stop="releaseSeat(booking)">释放座位</button>
            </div>
          </div>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'records'">
        <div class="page-header">
          <h2>违约记录</h2>
        </div>
        <div class="records-list" v-if="violationRecords.length > 0">
          <div class="record-card" v-for="record in violationRecords" :key="record.id">
            <div class="record-info">
              <div class="record-date">{{ record.date }}</div>
              <div class="record-desc">{{ record.description }}</div>
            </div>
            <div class="record-details">
              <div class="record-type">违约</div>
              <div class="record-deduction">扣{{ record.deduction }}分</div>
            </div>
          </div>
        </div>
        <div class="empty-state" v-else>
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="#DFEEF8" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="16 10 10 16 8 14"/>
          </svg>
          <p>暂无违约记录</p>
        </div>
      </div>
      <div class="main-content" v-else-if="currentPage === 'profile'">
        <div class="page-header">
          <h2>个人信息</h2>
        </div>
        <div class="profile-card">
          <div class="profile-header">
            <div class="avatar">
              <div class="icon-head"></div>
              <div class="icon-body"></div>
            </div>
            <div class="profile-info">
              <h3>学生</h3>
              <p>学号：22920242201234</p>
            </div>
            <div class="credit-score">
              <span class="credit-label">信誉积分</span>
              <span class="credit-value" :class="creditScore >= 80 ? 'high' : 'low'">{{ creditScore }}</span>
              <span v-if="creditScore < 80" class="credit-warning">信誉积分低于80分，暂无法预约</span>
            </div>
          </div>
          <div class="profile-details">
            <div class="detail-item">
              <span class="label">姓名</span>
              <span class="value">张三</span>
            </div>
            <div class="detail-item">
              <span class="label">学院</span>
              <span class="value">计算机学院</span>
            </div>
            <div class="detail-item">
              <span class="label">专业</span>
              <span class="value">软件工程</span>
            </div>
            <div class="detail-item">
              <span class="label">年级</span>
              <span class="value">2024级</span>
            </div>
            <div class="detail-item">
              <span class="label">联系电话</span>
              <div class="edit-field">
                <span v-if="!editingPhone" class="value">{{ maskedPhone }}</span>
                <input v-else type="text" v-model="studentInfo.phone" class="edit-input" />
                <button v-if="!editingPhone" class="edit-btn" @click="editingPhone = true">修改</button>
                <button v-else class="save-btn" @click="savePhone">保存</button>
              </div>
            </div>
            <div class="detail-item">
              <span class="label">邮箱</span>
              <div class="edit-field">
                <span v-if="!editingEmail" class="value">{{ studentInfo.email }}</span>
                <input v-else type="email" v-model="studentInfo.email" class="edit-input" />
                <button v-if="!editingEmail" class="edit-btn" @click="editingEmail = true">修改</button>
                <button v-else class="save-btn" @click="saveEmail">保存</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showBookingModal" @click="closeBookingModal">
      <div class="booking-modal" @click.stop>
        <div class="modal-header">
          <h3>预约详情</h3>
          <button class="close-btn" @click="closeBookingModal">×</button>
        </div>
        <div class="modal-body" v-if="selectedBooking">
          <div class="detail-row">
            <span class="detail-label">座位号</span>
            <span class="detail-value">{{ selectedBooking.seatNo }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">位置</span>
            <span class="detail-value">{{ selectedBooking.roomName }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">开始时间</span>
            <span class="detail-value">{{ selectedBooking.date }} {{ selectedBooking.startTime }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">结束时间</span>
            <span class="detail-value">{{ selectedBooking.date }} {{ selectedBooking.endTime }}</span>
          </div>
          <div class="modal-status" :class="selectedBooking.status">
            {{ getBookingStatus(selectedBooking) }}
          </div>
          <div class="signin-buttons" v-if="getBookingStatus(selectedBooking) === '待签到'">
            <button class="signin-btn qr" @click="openQRSignin">扫码签到</button>
            <button class="signin-btn radar" @click="openRadarSignin">雷达签到</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showQRSigninModal" @click="closeQRSigninModal">
      <div class="signin-modal" @click.stop>
        <div class="modal-header">
          <h3>扫码签到</h3>
          <button class="close-btn" @click="closeQRSigninModal">×</button>
        </div>
        <div class="modal-body">
          <div class="qr-scanner">
            <div class="scanner-frame"></div>
            <div class="scanner-line"></div>
          </div>
          <p class="signin-hint">请扫描座位上的签到二维码</p>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showRadarSigninModal" @click="closeRadarSigninModal">
      <div class="signin-modal" @click.stop>
        <div class="modal-header">
          <h3>雷达签到</h3>
          <button class="close-btn" @click="closeRadarSigninModal">×</button>
        </div>
        <div class="modal-body">
          <div class="radar-icon">📍</div>
          <p class="signin-hint">正在获取您的位置信息...</p>
          <button class="location-btn" @click="getLocation">获取位置</button>
        </div>
      </div>
    </div>
    <div class="modal-overlay" v-if="showSuccessModal" @click="closeSuccessModal">
      <div class="success-modal" @click.stop>
        <div class="success-icon">✓</div>
        <h3>签到成功</h3>
        <p>您已成功签到，开始学习吧！</p>
        <button class="confirm-btn" @click="closeSuccessModal">确定</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCampusStore } from '../store/campusStore'

const router = useRouter()
const store = useCampusStore()

const currentPage = ref('home')
const selectedRoom = ref('')
const selectedSeat = ref(null)

const today = computed(() => {
  const date = new Date()
  return date.toISOString().split('T')[0]
})

const buildings = computed(() => store.buildings.filter(b => !b.deleted))

const classrooms = computed(() => store.classrooms.filter(c => !c.deleted))

const classroomSeats = computed(() => {
  const result = {}
  for (const classroomId in store.seats) {
    result[classroomId] = store.seats[classroomId].filter(s => !s.deleted)
  }
  return result
})

const selectedBuilding = ref('')
const selectedClassroom = ref('')

const currentUser = computed(() => {
  const userStr = localStorage.getItem('user')
  if (!userStr) return null
  const user = JSON.parse(userStr)
  return store.users.find(u => u.id === user.id) || user
})
const creditScore = computed(() => currentUser.value?.creditScore || 85)
const canBook = computed(() => {
  if (!currentUser.value) return false
  store.checkBlacklistExpire(currentUser.value.id)
  return !currentUser.value.inBlacklist
})

const editingPhone = ref(false)
const editingEmail = ref(false)

const studentInfo = reactive({
  phone: '13812341234',
  email: 'zhangsan@example.com'
})

const showBookingModal = ref(false)
const showQRSigninModal = ref(false)
const showRadarSigninModal = ref(false)
const showSuccessModal = ref(false)
const selectedBooking = ref(null)

const myBookings = computed(() => {
  return store.bookings
    .filter(b => b.studentId === currentUser.value?.id)
    .map(b => {
      const classroom = store.classrooms.find(c => c.id === b.roomId)
      const seat = store.seats[b.roomId]?.find(s => s.id === b.seatId)
      return {
        ...b,
        roomName: classroom?.name || '',
        seatNo: seat?.no || ''
      }
    })
    .sort((a, b) => new Date(b.date + ' ' + b.startTime) - new Date(a.date + ' ' + a.startTime))
})

const recentBookings = computed(() => {
  return myBookings.value.map(b => ({
    ...b,
    time: `${b.startTime} - ${b.endTime}`
  }))
})

const violationRecords = ref([
  { id: 1, date: '2026-07-01', description: '预约座位A-03后未按时签到，迟到超过15分钟', deduction: 5 }
])

const bookingForm = reactive({
  date: '',
  startTime: '',
  endTime: ''
})

const maxDate = computed(() => {
  const date = new Date()
  date.setDate(date.getDate() + 7)
  return date.toISOString().split('T')[0]
})

const maskedPhone = computed(() => {
  if (!studentInfo.phone) return ''
  return studentInfo.phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
})

const filteredClassrooms = computed(() => {
  if (!selectedBuilding.value) return []
  const buildingId = parseInt(selectedBuilding.value)
  const buildingClassrooms = store.classrooms.filter(c => c.buildingId === buildingId && !c.deleted)
  return buildingClassrooms.sort((a, b) => {
    if (a.open !== b.open) return a.open ? -1 : 1
    return a.name.localeCompare(b.name, 'zh')
  })
})

const currentClassroom = computed(() => {
  if (!selectedClassroom.value) return null
  return store.classrooms.find(c => c.id === parseInt(selectedClassroom.value))
})

const currentSeats = computed(() => {
  if (!selectedClassroom.value) return []
  const classroomId = parseInt(selectedClassroom.value)
  return classroomSeats.value[classroomId] || []
})

const getSelectedSeatInfo = computed(() => {
  if (!selectedSeat.value) return null
  for (const classroomId in classroomSeats.value) {
    const seat = classroomSeats.value[classroomId].find(s => s.id === selectedSeat.value)
    if (seat) return seat
  }
  return null
})

const recentBookingsList = computed(() => {
  const now = new Date()
  const sevenDaysLater = new Date()
  sevenDaysLater.setDate(now.getDate() + 7)
  return myBookings.value.filter(booking => {
    const bookingDate = new Date(booking.date)
    return bookingDate >= now && bookingDate <= sevenDaysLater
  }).slice(0, 5)
})

const handleLogout = () => {
  router.push('/')
}

const onBuildingChange = () => {
  selectedClassroom.value = ''
  selectedSeat.value = null
}

const onClassroomChange = () => {
  selectedSeat.value = null
}

const isSeatOccupied = (seat) => {
  if (!bookingForm.date || !bookingForm.startTime || !bookingForm.endTime) {
    return false
  }
  if (!currentClassroom.value) return false
  return myBookings.value.some(booking => {
    if (booking.roomName !== currentClassroom.value.name) return false
    if (booking.seatNo !== seat.no) return false
    if (booking.date !== bookingForm.date) return false
    if (booking.status === 'cancelled') return false
    const bookingStart = booking.startTime
    const bookingEnd = booking.endTime
    const newStart = bookingForm.startTime
    const newEnd = bookingForm.endTime
    return !(newEnd <= bookingStart || newStart >= bookingEnd)
  })
}

const selectSeat = (seat) => {
  if (!isSeatOccupied(seat)) {
    selectedSeat.value = seat.id
  }
}

const submitBooking = async () => {
  if (!bookingForm.date || !bookingForm.startTime || !bookingForm.endTime) {
    alert('请填写完整信息')
    return
  }
  
  if (!canBook.value) {
    const reason = currentUser.value?.blacklistReason || '信誉积分不足'
    alert(`您已被限制预约权限：${reason}，无法预约`)
    return
  }
  
  const openTime = store.systemSettings.openTime || '06:00'
  const closeTime = store.systemSettings.closeTime || '23:59'
  
  if (bookingForm.startTime < openTime || bookingForm.endTime > closeTime) {
    alert(`预约时间超出开放时间（${openTime}~${closeTime}）`)
    return
  }
  
  if (bookingForm.startTime >= bookingForm.endTime) {
    alert('开始时间不能晚于或等于结束时间')
    return
  }
  
  const now = new Date()
  const today = now.toISOString().split('T')[0]
  
  const advanceMinutes = store.systemSettings.advanceMinutes || 30
  const minBookingTime = new Date(now.getTime() + advanceMinutes * 60 * 1000)
  const minBookingTimeStr = minBookingTime.toTimeString().slice(0, 5)
  
  if (bookingForm.date === today && bookingForm.startTime <= minBookingTimeStr) {
    alert(`需提前${advanceMinutes}分钟预约，最早可预约时间：${minBookingTimeStr}`)
    return
  }
  
  const seat = getSelectedSeatInfo.value
  const bookingData = {
    studentId: currentUser.value?.id || 1,
    roomId: parseInt(selectedClassroom.value),
    seatId: seat.id,
    date: bookingForm.date,
    startTime: bookingForm.startTime,
    endTime: bookingForm.endTime
  }
  
  try {
    await store.addBooking(bookingData)
    alert('预约成功')
    selectedSeat.value = null
    bookingForm.date = ''
    bookingForm.startTime = ''
    bookingForm.endTime = ''
    currentPage.value = 'myBookings'
  } catch (e) {
    alert('预约失败，请稍后重试')
  }
}

const savePhone = () => {
  editingPhone.value = false
  alert('电话修改成功')
}

const saveEmail = () => {
  editingEmail.value = false
  alert('邮箱修改成功')
}

const getBookingStatus = (booking) => {
  if (booking.status === 'cancelled') {
    return booking.statusText
  }
  if (booking.status === 'completed') {
    return booking.statusText
  }
  if (booking.status === 'in_progress') {
    return booking.statusText
  }
  
  const now = new Date()
  const bookingDate = new Date(`${booking.date}T${booking.startTime}`)
  const advanceMinutes = store.systemSettings.advanceMinutes || 30
  const timeoutMinutes = store.systemSettings.timeoutMinutes || 30
  
  const startMinusAdvance = new Date(bookingDate.getTime() - advanceMinutes * 60 * 1000)
  const startPlusTimeout = new Date(bookingDate.getTime() + timeoutMinutes * 60 * 1000)
  
  if (now < startMinusAdvance) {
    return '未开始'
  } else if (now >= startMinusAdvance && now <= startPlusTimeout) {
    return '待签到'
  } else if (now > startPlusTimeout) {
    return '已超时'
  }
  return booking.statusText
}

const canCancel = (booking) => {
  if (booking.status === 'cancelled' || booking.status === 'completed') {
    return false
  }
  const now = new Date()
  const bookingDate = new Date(`${booking.date}T${booking.startTime}`)
  return now < bookingDate
}

const cancelBooking = async (id) => {
  const booking = myBookings.value.find(b => b.id === id)
  if (booking && canCancel(booking)) {
    try {
      await store.cancelBooking(id)
      alert('取消成功')
    } catch (e) {
      alert('取消失败，请稍后重试')
    }
  }
}

const canReleaseSeat = (booking) => {
  if (booking.status !== 'in_progress') return false
  const endTime = new Date(`${booking.date}T${booking.endTime}`)
  const releaseDeadline = new Date(endTime.getTime() + 30 * 60 * 1000)
  return new Date() <= releaseDeadline
}

const releaseSeat = async (booking) => {
  if (!canReleaseSeat(booking)) return
  if (confirm('确定要释放座位吗？座位将变为空闲状态可供他人预约。')) {
    try {
      await store.releaseSeat(booking.id)
      alert('座位已释放成功！')
    } catch (e) {
      alert('释放座位失败，请稍后重试')
    }
  }
}

const openBookingDetail = (booking) => {
  selectedBooking.value = booking
  showBookingModal.value = true
}

const closeBookingModal = () => {
  showBookingModal.value = false
  selectedBooking.value = null
}

const openQRSignin = () => {
  showBookingModal.value = false
  showQRSigninModal.value = true
}

const closeQRSigninModal = async () => {
  showQRSigninModal.value = false
  try {
    if (selectedBooking.value) {
      await store.signinBooking(selectedBooking.value.id, { method: 'qr' })
    }
    showSuccessModal.value = true
  } catch (e) {
    alert('签到失败，请稍后重试')
  }
}

const openRadarSignin = () => {
  showBookingModal.value = false
  showRadarSigninModal.value = true
}

const closeRadarSigninModal = () => {
  showRadarSigninModal.value = false
}

const getLocation = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      async (position) => {
        closeRadarSigninModal()
        try {
          if (selectedBooking.value) {
            await store.signinBooking(selectedBooking.value.id, { 
              method: 'radar',
              location: {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude
              }
            })
          }
          showSuccessModal.value = true
        } catch (e) {
          alert('签到失败，请稍后重试')
        }
      },
      (error) => {
        alert('获取位置失败，请检查定位权限')
      }
    )
  } else {
    alert('您的浏览器不支持定位功能')
  }
}

const closeSuccessModal = () => {
  showSuccessModal.value = false
}

let violationCheckTimer = null

onMounted(async () => {
  await store.init()
  violationCheckTimer = setInterval(() => {
    store.checkSeatReleaseViolation()
  }, 60000)
})

onUnmounted(() => {
  if (violationCheckTimer) {
    clearInterval(violationCheckTimer)
  }
})
</script>

<style scoped>
.student-page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #DFEEF8;
}

.header-bar {
  height: 60px;
  background: #608BB8;
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

.header-text {
  color: #C1E8FE;
  font-size: 18px;
  font-weight: bold;
}

.header-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.icon-circle {
  width: 20px;
  height: 20px;
  background: #C1E8FE;
  border-radius: 50%;
}

.icon-triangle {
  width: 24px;
  height: 14px;
  background: #C1E8FE;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.logout-btn {
  background: none;
  border: none;
  color: #C1E8FE;
  font-size: 14px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 4px;
  transition: background 0.3s ease;
}

.logout-btn:hover {
  background: rgba(255, 255, 255, 0.1);
}

.content {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar {
  width: 180px;
  background: #FFFFFF;
  padding: 20px 0;
  border-right: 1px solid #DFEEF8;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 20px;
  color: #626363;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
}

.menu-item:hover {
  background: #DFEEF8;
}

.menu-item.active {
  background: #C1E8FE;
  color: #608BB8;
  font-weight: 500;
}

.main-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 22px;
  color: #021024;
  margin: 0;
}

.welcome-banner {
  background: #C1E8FE;
  border-radius: 12px;
  padding: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.banner-content h2 {
  font-size: 24px;
  color: #021024;
  margin: 0 0 10px 0;
}

.banner-content p {
  font-size: 14px;
  color: #626363;
  margin: 0 0 20px 0;
}

.booking-btn {
  background: #608BB8;
  color: #FFFFFF;
  border: none;
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: opacity 0.3s ease;
}

.booking-btn:hover {
  opacity: 0.9;
}

.banner-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.banner-icon .icon-head {
  width: 60px;
  height: 60px;
  background: #FFFFFF;
  border-radius: 50%;
}

.banner-icon .icon-body {
  width: 72px;
  height: 42px;
  background: #FFFFFF;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.recent-bookings {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 24px;
}

.recent-bookings h3 {
  font-size: 16px;
  color: #021024;
  margin: 0 0 16px 0;
}

.booking-table {
  width: 100%;
}

.table-header {
  display: flex;
  padding: 10px 15px;
  background: #DFEEF8;
  border-radius: 8px;
  margin-bottom: 8px;
}

.table-header span {
  flex: 1;
  font-size: 14px;
  color: #608BB8;
  font-weight: 500;
}

.table-row {
  display: flex;
  padding: 12px 15px;
  border-bottom: 1px solid #DFEEF8;
}

.table-row:last-child {
  border-bottom: none;
}

.table-row span {
  flex: 1;
  font-size: 14px;
  color: #626363;
}

.table-row .status {
  font-weight: 500;
  padding: 4px 12px;
  border-radius: 20px;
  text-align: center;
}

.table-row .status.pending {
  background: #FFF3E0;
  color: #FF9800;
}

.table-row .status.approved {
  background: #E8F5E9;
  color: #4CAF50;
}

.booking-rules {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
}

.booking-rules h3 {
  font-size: 16px;
  color: #021024;
  margin: 0 0 16px 0;
}

.rules-content ul {
  margin: 0;
  padding-left: 20px;
}

.rules-content li {
  font-size: 14px;
  color: #626363;
  margin-bottom: 10px;
  line-height: 1.6;
}

.rules-content li:last-child {
  margin-bottom: 0;
}

.booking-area {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 24px;
}

.booking-filters {
  display: flex;
  gap: 24px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  flex-direction: column;
}

.filter-group label {
  font-size: 14px;
  color: #626363;
  margin-bottom: 8px;
}

.filter-select {
  width: 200px;
  height: 40px;
  border: 1px solid #DFEEF8;
  border-radius: 8px;
  padding: 0 12px;
  font-size: 14px;
  color: #021024;
  outline: none;
}

.filter-select:focus {
  border-color: #608BB8;
}

.classroom-status {
  margin-bottom: 16px;
}

.status-indicator {
  display: inline-block;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: bold;
}

.status-indicator.open {
  background: #E8F5E9;
  color: #4CAF50;
}

.status-indicator.closed {
  background: #FFEBEE;
  color: #F44336;
}

.ac-indicator {
  display: inline-block;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: bold;
  background: #E3F2FD;
  color: #1976D2;
  margin-left: 12px;
}

.ac-indicator.no-ac {
  background: #F5F5F5;
  color: #9E9E9E;
}

.seat-legend {
  display: flex;
  gap: 24px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #626363;
}

.legend-color {
  width: 20px;
  height: 20px;
  border-radius: 4px;
}

.legend-color.available {
  background: #E8F5E9;
  border: 2px solid #4CAF50;
}

.legend-color.occupied {
  background: #FFCDD2;
  border: 2px solid #F44336;
}

.legend-color.selected {
  background: #608BB8;
}

.socket-icon {
  font-size: 12px;
  margin-left: 4px;
}

.no-seats {
  padding: 40px;
  text-align: center;
  color: #626363;
}

.seat-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
  margin-bottom: 24px;
}

.seat {
  width: 100%;
  height: 48px;
  background: #E8F5E9;
  border: 2px solid #4CAF50;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #388E3C;
  cursor: pointer;
  transition: all 0.3s ease;
}

.seat:hover:not(.occupied) {
  background: #C8E6C9;
}

.seat.occupied {
  background: #FFCDD2;
  border-color: #F44336;
  color: #C62828;
  cursor: not-allowed;
}

.seat.selected {
  background: #608BB8;
  color: #FFFFFF;
  border-color: #608BB8;
}

.booking-form {
  background: #DFEEF8;
  border-radius: 8px;
  padding: 20px;
}

.booking-form .form-group {
  margin-bottom: 16px;
}

.booking-form .form-group label {
  display: block;
  font-size: 14px;
  color: #626363;
  margin-bottom: 8px;
}

.booking-form .form-input {
  width: 100%;
  height: 40px;
  border: 1px solid #C1E8FE;
  border-radius: 8px;
  padding: 0 12px;
  font-size: 14px;
  color: #021024;
  outline: none;
  box-sizing: border-box;
}

.booking-form .form-input:focus {
  border-color: #608BB8;
}

.booking-form .form-input:disabled {
  background: #E0E0E0;
  cursor: not-allowed;
}

.submit-btn {
  background: #608BB8;
  color: #FFFFFF;
  border: none;
  padding: 12px 32px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: opacity 0.3s ease;
}

.submit-btn:hover {
  opacity: 0.9;
}

.my-bookings-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.booking-card {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #DFEEF8;
}

.card-header .room-name {
  font-size: 16px;
  font-weight: bold;
  color: #021024;
}

.card-header .seat-no {
  font-size: 14px;
  color: #608BB8;
}

.card-body {
  margin-bottom: 12px;
}

.booking-date {
  font-size: 14px;
  color: #626363;
  margin-bottom: 4px;
}

.booking-time {
  font-size: 14px;
  color: #608BB8;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.booking-status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.booking-status.pending {
  background: #FFF3E0;
  color: #FF9800;
}

.booking-status.approved {
  background: #E8F5E9;
  color: #4CAF50;
}

.booking-status.completed {
  background: #E3F2FD;
  color: #1976D2;
}

.booking-status.cancelled {
  background: #FFF0F0;
  color: #E53935;
}

.cancel-btn {
  background: #FFF0F0;
  color: #E53935;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  cursor: pointer;
}

.release-btn {
  background: #E3F2FD;
  color: #1976D2;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  cursor: pointer;
  margin-left: 8px;
}

.records-list {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
}

.record-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #DFEEF8;
}

.record-card:last-child {
  border-bottom: none;
}

.record-date {
  font-size: 14px;
  color: #608BB8;
  margin-bottom: 4px;
}

.record-desc {
  font-size: 14px;
  color: #626363;
}

.record-details {
  display: flex;
  gap: 12px;
  align-items: center;
}

.record-type {
  background: #FFF0F0;
  color: #E53935;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.record-deduction {
  background: #FFEBEE;
  color: #F44336;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: bold;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 0;
  background: #FFFFFF;
  border-radius: 12px;
}

.empty-state p {
  font-size: 16px;
  color: #626363;
  margin-top: 16px;
}

.profile-card {
  background: #FFFFFF;
  border-radius: 12px;
  padding: 30px;
  max-width: 500px;
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 2px solid #DFEEF8;
}

.avatar {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.avatar .icon-head {
  width: 60px;
  height: 60px;
  background: #C1E8FE;
  border-radius: 50%;
}

.avatar .icon-body {
  width: 72px;
  height: 42px;
  background: #C1E8FE;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.profile-info h3 {
  font-size: 20px;
  color: #021024;
  margin: 0 0 8px 0;
}

.profile-info p {
  font-size: 14px;
  color: #626363;
  margin: 0;
}

.credit-score {
  display: flex;
  align-items: center;
  gap: 8px;
}

.credit-label {
  font-size: 14px;
  color: #626363;
}

.credit-value {
  font-size: 24px;
  font-weight: bold;
}

.credit-value.high {
  color: #4CAF50;
}

.credit-value.low {
  color: #F44336;
}

.credit-warning {
  font-size: 12px;
  color: #F44336;
  background: #FFEBEE;
  padding: 4px 8px;
  border-radius: 4px;
}

.profile-details {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #DFEEF8;
}

.detail-item:last-child {
  border-bottom: none;
}

.detail-item .label {
  font-size: 14px;
  color: #626363;
}

.detail-item .value {
  font-size: 14px;
  color: #021024;
  font-weight: 500;
}

.edit-field {
  display: flex;
  gap: 8px;
  align-items: center;
}

.edit-input {
  height: 32px;
  border: 1px solid #DFEEF8;
  border-radius: 6px;
  padding: 0 10px;
  font-size: 14px;
  color: #021024;
  outline: none;
}

.edit-input:focus {
  border-color: #608BB8;
}

.edit-btn {
  height: 32px;
  padding: 0 16px;
  background: none;
  color: #608BB8;
  border: 1px solid #608BB8;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
}

.edit-btn:hover {
  background: #DFEEF8;
}

.save-btn {
  height: 32px;
  padding: 0 16px;
  background: #608BB8;
  color: #FFFFFF;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
}

.save-btn:hover {
  background: #4A7AA0;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.booking-modal, .signin-modal {
  background: #FFFFFF;
  border-radius: 12px;
  width: 400px;
  max-width: 90%;
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: #608BB8;
  color: #FFFFFF;
}

.modal-header h3 {
  margin: 0;
  font-size: 16px;
}

.close-btn {
  background: none;
  border: none;
  color: #FFFFFF;
  font-size: 24px;
  cursor: pointer;
  line-height: 1;
}

.modal-body {
  padding: 24px;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #DFEEF8;
}

.detail-row:last-child {
  border-bottom: none;
}

.detail-label {
  font-size: 14px;
  color: #626363;
}

.detail-value {
  font-size: 14px;
  color: #021024;
  font-weight: 500;
}

.modal-status {
  text-align: right;
  padding: 12px 0;
  font-size: 14px;
  font-weight: bold;
}

.modal-status.waiting_signin {
  color: #FF9800;
}

.modal-status.not_started {
  color: #2196F3;
}

.modal-status.completed {
  color: #4CAF50;
}

.modal-status.cancelled {
  color: #9E9E9E;
}

.modal-status.timeout {
  color: #F44336;
}

.signin-buttons {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.signin-btn {
  flex: 1;
  height: 44px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.signin-btn.qr {
  background: #608BB8;
  color: #FFFFFF;
}

.signin-btn.radar {
  background: #4CAF50;
  color: #FFFFFF;
}

.signin-btn:hover {
  opacity: 0.9;
}

.qr-scanner {
  width: 200px;
  height: 200px;
  margin: 0 auto 20px;
  border: 2px dashed #608BB8;
  border-radius: 12px;
  position: relative;
}

.scanner-frame {
  position: absolute;
  top: 10px;
  left: 10px;
  right: 10px;
  bottom: 10px;
  border: 2px solid #608BB8;
  border-radius: 8px;
}

.scanner-line {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: #608BB8;
  animation: scan 2s linear infinite;
}

@keyframes scan {
  0% { top: 0; }
  100% { top: 100%; }
}

.signin-hint {
  text-align: center;
  color: #626363;
  font-size: 14px;
  margin: 0;
}

.radar-icon {
  font-size: 64px;
  text-align: center;
  margin-bottom: 16px;
}

.location-btn {
  width: 100%;
  height: 44px;
  background: #4CAF50;
  color: #FFFFFF;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 16px;
}

.location-btn:hover {
  background: #43A047;
}

.success-modal {
  background: #FFFFFF;
  border-radius: 12px;
  width: 300px;
  padding: 30px;
  text-align: center;
}

.success-icon {
  width: 64px;
  height: 64px;
  background: #4CAF50;
  color: #FFFFFF;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  margin: 0 auto 16px;
}

.success-modal h3 {
  margin: 0 0 8px 0;
  color: #021024;
}

.success-modal p {
  color: #626363;
  font-size: 14px;
  margin: 0 0 20px 0;
}

.confirm-btn {
  height: 40px;
  padding: 0 32px;
  background: #608BB8;
  color: #FFFFFF;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
}

.confirm-btn:hover {
  background: #4A7AA0;
}
</style>