import { reactive, ref } from 'vue'
import * as authApi from '../api/auth'
import * as campusApi from '../api/campus'
import * as bookingApi from '../api/booking'
import * as adminApi from '../api/admin'

const buildings = reactive([])
const classrooms = reactive([])
const seats = reactive({})
const bookings = reactive([])
const allBookings = reactive([])
const users = reactive([])
const blacklistRecords = reactive([])
const systemSettings = reactive({
  advanceMinutes: 30,
  signinTimeout: 15,
  violationLimit: 3,
  violationBanDays: 7,
  openTime: '06:00',
  closeTime: '23:59'
})

const loading = reactive({
  buildings: false,
  classrooms: false,
  seats: false,
  bookings: false,
  users: false,
  settings: false
})

const currentUser = reactive({})

const store = {
  buildings,
  classrooms,
  seats,
  bookings,
  allBookings,
  users,
  blacklistRecords,
  systemSettings,
  loading,
  currentUser,
  
  async init() {
    await Promise.all([
      this.loadBuildings(),
      this.loadClassrooms(),
      this.loadUsers(),
      this.loadSettings(),
      this.loadBookings(),
      this.loadViolations()
    ])
  },
  
  async loadBuildings() {
    loading.buildings = true
    try {
      const data = await campusApi.getBuildings()
      buildings.length = 0
      buildings.push(...data)
    } catch (e) {
      console.error('加载楼栋失败:', e)
    } finally {
      loading.buildings = false
    }
  },
  
  getBuildingName(id) {
    const building = buildings.find(b => b.id === id)
    return building ? building.name : ''
  },
  
  async loadClassrooms() {
    loading.classrooms = true
    try {
      const data = await campusApi.getClassrooms()
      classrooms.length = 0
      classrooms.push(...data)
    } catch (e) {
      console.error('加载教室失败:', e)
    } finally {
      loading.classrooms = false
    }
  },
  
  async loadSeats(classroomId, date = null, startTime = null, endTime = null) {
    loading.seats = true
    try {
      const params = { classroomId }
      if (date && startTime && endTime) {
        params.date = date
        params.startTime = startTime
        params.endTime = endTime
      }
      const data = await campusApi.getSeats(null, params)
      seats[classroomId] = data || []
    } catch (e) {
      console.error('加载座位失败:', e)
    } finally {
      loading.seats = false
    }
  },
  
  async loadBookings() {
    loading.bookings = true
    try {
      const data = await bookingApi.getMyBookings()
      bookings.length = 0
      bookings.push(...data)
    } catch (e) {
      console.error('加载预约失败:', e)
    } finally {
      loading.bookings = false
    }
  },
  
  async loadViolations() {
    try {
      const data = await bookingApi.getMyViolations()
      blacklistRecords.length = 0
      blacklistRecords.push(...data)
    } catch (e) {
      console.error('加载违约记录失败:', e)
    }
  },
  
  async loadAllBookings() {
    loading.bookings = true
    try {
      const data = await bookingApi.getBookings()
      allBookings.length = 0
      allBookings.push(...data)
    } catch (e) {
      console.error('加载所有预约失败:', e)
    } finally {
      loading.bookings = false
    }
  },
  
  async loadUsers() {
    loading.users = true
    try {
      const data = await adminApi.getUsers()
      users.length = 0
      users.push(...data)
    } catch (e) {
      console.error('加载用户失败:', e)
    } finally {
      loading.users = false
    }
  },
  
  async loadSettings() {
    loading.settings = true
    try {
      const data = await adminApi.getSystemSettings()
      Object.assign(systemSettings, data)
    } catch (e) {
      console.error('加载设置失败:', e)
    } finally {
      loading.settings = false
    }
  },
  
  async addBuilding(building) {
    try {
      const data = await campusApi.createBuilding(building)
      buildings.push(data)
      return data
    } catch (e) {
      console.error('添加楼栋失败:', e)
      throw e
    }
  },
  
  async updateBuilding(id, updates) {
    try {
      const data = await campusApi.updateBuilding(id, updates)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index] = { ...buildings[index], ...data }
      }
      return data
    } catch (e) {
      console.error('更新楼栋失败:', e)
      throw e
    }
  },
  
  async deleteBuilding(id) {
    try {
      const data = await campusApi.deleteBuilding(id)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index].deleted = true
        buildings[index].deletedAt = data.deletedAt
      }
      return data
    } catch (e) {
      console.error('删除楼栋失败:', e)
      throw e
    }
  },
  
  async restoreBuilding(id) {
    try {
      const data = await campusApi.restoreBuilding(id)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index].deleted = false
        buildings[index].deletedAt = null
      }
      return data
    } catch (e) {
      console.error('恢复楼栋失败:', e)
      throw e
    }
  },
  
  async loadDeletedBuildings() {
    try {
      const data = await campusApi.getDeletedBuildings()
      return data
    } catch (e) {
      console.error('加载已删除楼栋失败:', e)
      throw e
    }
  },
  
  getDeletedBuildings() {
    return buildings.filter(b => b.deleted)
  },
  
  async addClassroom(classroom) {
    try {
      const data = await campusApi.createClassroom(classroom)
      classrooms.push(data)
      seats[data.id] = []
      return data
    } catch (e) {
      console.error('添加教室失败:', e)
      throw e
    }
  },
  
  async updateClassroom(id, updates) {
    try {
      const data = await campusApi.updateClassroom(id, updates)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index] = { ...classrooms[index], ...data }
      }
      return data
    } catch (e) {
      console.error('更新教室失败:', e)
      throw e
    }
  },
  
  async deleteClassroom(id) {
    try {
      const data = await campusApi.deleteClassroom(id)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index].deleted = true
        classrooms[index].deletedAt = data.deletedAt
      }
      return data
    } catch (e) {
      console.error('删除教室失败:', e)
      throw e
    }
  },
  
  async restoreClassroom(id) {
    try {
      const data = await campusApi.restoreClassroom(id)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index].deleted = false
        classrooms[index].deletedAt = null
      }
      return data
    } catch (e) {
      console.error('恢复教室失败:', e)
      throw e
    }
  },
  
  async loadDeletedClassrooms() {
    try {
      const data = await campusApi.getDeletedClassrooms()
      return data
    } catch (e) {
      console.error('加载已删除教室失败:', e)
      throw e
    }
  },
  
  getDeletedClassrooms() {
    return classrooms.filter(c => c.deleted)
  },
  
  async addSeat(classroomId, seat) {
    try {
      const data = await campusApi.createSeat({ ...seat, classroomId })
      if (!seats[classroomId]) {
        seats[classroomId] = []
      }
      seats[classroomId].push(data)
      return data
    } catch (e) {
      console.error('添加座位失败:', e)
      throw e
    }
  },
  
  async updateSeat(classroomId, seatId, updates) {
    try {
      const data = await campusApi.updateSeat(seatId, updates)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index] = { ...classroomSeats[index], ...data }
      }
      return data
    } catch (e) {
      console.error('更新座位失败:', e)
      throw e
    }
  },
  
  async deleteSeat(classroomId, seatId) {
    try {
      const data = await campusApi.deleteSeat(seatId)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index].deleted = true
      }
      return data
    } catch (e) {
      console.error('删除座位失败:', e)
      throw e
    }
  },
  
  async restoreSeat(classroomId, seatId) {
    try {
      const data = await campusApi.restoreSeat(seatId)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index].deleted = false
      }
      return data
    } catch (e) {
      console.error('恢复座位失败:', e)
      throw e
    }
  },
  
  async loadDeletedSeats(classroomId) {
    try {
      const data = await campusApi.getDeletedSeats(classroomId)
      return data
    } catch (e) {
      console.error('加载已删除座位失败:', e)
      throw e
    }
  },
  
  async importSeats(classroomId, newSeats) {
    try {
      const data = await adminApi.importSeats(classroomId, newSeats)
      await this.loadSeats(classroomId)
      return data.success || newSeats.length
    } catch (e) {
      console.error('导入座位失败:', e)
      throw e
    }
  },
  
  async addBooking(booking) {
    try {
      const data = await bookingApi.createBooking(booking)
      bookings.push(data)
      return data
    } catch (e) {
      console.error('创建预约失败:', e)
      throw e
    }
  },
  
  async cancelBooking(id) {
    try {
      await bookingApi.cancelBooking(id)
      await this.loadBookings()
    } catch (e) {
      console.error('取消预约失败:', e)
      throw e
    }
  },
  
  async deleteBooking(id) {
    try {
      await bookingApi.deleteBooking(id)
      const index = bookings.findIndex(b => b.id === id)
      if (index !== -1) {
        bookings.splice(index, 1)
      }
    } catch (e) {
      console.error('删除预约失败:', e)
      throw e
    }
  },
  
  async login(credentials) {
    try {
      const data = await authApi.login(credentials)
      localStorage.setItem('token', data.token)
      Object.assign(currentUser, data.user)
      return data
    } catch (e) {
      console.error('登录失败:', e)
      throw e
    }
  },
  
  async logout() {
    try {
      await authApi.logout()
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      Object.keys(currentUser).forEach(key => delete currentUser[key])
    } catch (e) {
      console.error('登出失败:', e)
    }
  },
  
  async getUserInfo() {
    try {
      const data = await authApi.getUserInfo()
      Object.assign(currentUser, data)
      return data
    } catch (e) {
      console.error('获取用户信息失败:', e)
      throw e
    }
  },
  
  async register(user) {
    try {
      return await authApi.register(user)
    } catch (e) {
      console.error('注册失败:', e)
      throw e
    }
  },
  
  async importUsers(newUsers) {
    try {
      await adminApi.importUsers(newUsers)
      await this.loadUsers()
    } catch (e) {
      console.error('导入用户失败:', e)
      throw e
    }
  },
  
  async addUser(userData) {
    try {
      await adminApi.createUser(userData)
      await this.loadUsers()
    } catch (e) {
      console.error('添加用户失败:', e)
      throw e
    }
  },
  
  async updateUser(userId, data) {
    try {
      const result = await adminApi.updateUser(userId, data)
      const user = users.find(u => u.id === userId)
      if (user) {
        Object.assign(user, data)
      }
      const userStr = localStorage.getItem('user')
      if (userStr) {
        const currentUser = JSON.parse(userStr)
        if (currentUser.id === userId) {
          Object.assign(currentUser, data)
          localStorage.setItem('user', JSON.stringify(currentUser))
        }
      }
      return result
    } catch (e) {
      console.error('更新用户信息失败:', e)
      throw e
    }
  },
  
  async addViolation(userId) {
    try {
      const data = await adminApi.updateUser(userId, { violationCount: { $inc: 1 } })
      const user = users.find(u => u.id === userId)
      if (user) {
        user.violationCount++
        user.creditScore = Math.max(0, user.creditScore - 20)
        if (user.creditScore === 0) {
          user.inBlacklist = true
        }
      }
      return data
    } catch (e) {
      console.error('添加违规记录失败:', e)
      throw e
    }
  },
  
  async updateCreditScore(userId, creditScore) {
    try {
      const data = await adminApi.updateUser(userId, { creditScore })
      const user = users.find(u => u.id === userId)
      if (user) {
        user.creditScore = creditScore
      }
      return data
    } catch (e) {
      console.error('更新信誉积分失败:', e)
      throw e
    }
  },
  
  async deleteUser(id) {
    try {
      const data = await adminApi.deleteUser(id)
      const index = users.findIndex(u => u.id === id)
      if (index !== -1) {
        users.splice(index, 1)
      }
      return data
    } catch (e) {
      console.error('删除用户失败:', e)
      throw e
    }
  },
  
  async removeBlacklist(id) {
    try {
      await adminApi.removeBlacklist(id)
      const user = users.find(u => u.id === id)
      if (user) {
        user.inBlacklist = false
        user.violationCount = 0
        user.creditScore = 100
      }
    } catch (e) {
      console.error('解除黑名单失败:', e)
      throw e
    }
  },
  
  async addToBlacklist(id, reason) {
    try {
      const data = await adminApi.addToBlacklist(id, { reason })
      const user = users.find(u => u.id === id)
      if (user) {
        user.inBlacklist = true
        user.blacklistReason = reason
        user.creditScore = 0
        user.violationCount = 3
      }
      return data
    } catch (e) {
      console.error('加入黑名单失败:', e)
      throw e
    }
  },
  
  async loadBlacklist() {
    try {
      const data = await adminApi.getBlacklist()
      blacklistRecords.length = 0
      blacklistRecords.push(...data)
    } catch (e) {
      console.error('加载黑名单失败:', e)
    }
  },
  
  async loadReports(params) {
    try {
      return await adminApi.getReports(params)
    } catch (e) {
      console.error('加载报表失败:', e)
      throw e
    }
  },
  
  async updateSettings(settings) {
    try {
      const data = await adminApi.updateSystemSettings(settings)
      Object.assign(systemSettings, data)
      return data
    } catch (e) {
      console.error('更新设置失败:', e)
      throw e
    }
  },
  
  async batchDeleteBuildings(ids) {
    try {
      await adminApi.batchDeleteBuildings(ids)
      ids.forEach(id => {
        const building = buildings.find(b => b.id === id)
        if (building) building.deleted = true
      })
    } catch (e) {
      console.error('批量删除楼栋失败:', e)
      throw e
    }
  },
  
  async batchRestoreBuildings(ids) {
    try {
      await adminApi.batchRestoreBuildings(ids)
      ids.forEach(id => {
        const building = buildings.find(b => b.id === id)
        if (building) building.deleted = false
      })
    } catch (e) {
      console.error('批量恢复楼栋失败:', e)
      throw e
    }
  },
  
  async batchDeleteClassrooms(ids) {
    try {
      await adminApi.batchDeleteClassrooms(ids)
      ids.forEach(id => {
        const classroom = classrooms.find(c => c.id === id)
        if (classroom) classroom.deleted = true
      })
    } catch (e) {
      console.error('批量删除教室失败:', e)
      throw e
    }
  },
  
  async batchRestoreClassrooms(ids) {
    try {
      await adminApi.batchRestoreClassrooms(ids)
      ids.forEach(id => {
        const classroom = classrooms.find(c => c.id === id)
        if (classroom) classroom.deleted = false
      })
    } catch (e) {
      console.error('批量恢复教室失败:', e)
      throw e
    }
  },
  
  async batchDeleteSeats(classroomId, ids) {
    try {
      await adminApi.batchDeleteSeats(ids)
      const classroomSeats = seats[classroomId] || []
      ids.forEach(id => {
        const seat = classroomSeats.find(s => s.id === id)
        if (seat) seat.deleted = true
      })
    } catch (e) {
      console.error('批量删除座位失败:', e)
      throw e
    }
  },
  
  async batchRestoreSeats(classroomId, ids) {
    try {
      await adminApi.batchRestoreSeats(ids)
      const classroomSeats = seats[classroomId] || []
      ids.forEach(id => {
        const seat = classroomSeats.find(s => s.id === id)
        if (seat) seat.deleted = false
      })
    } catch (e) {
      console.error('批量恢复座位失败:', e)
      throw e
    }
  },
  
  async getReportsData(params) {
    try {
      return await adminApi.getReports(params)
    } catch (e) {
      console.error('获取报表数据失败:', e)
      throw e
    }
  }
}

export const useCampusStore = () => store

export default store
