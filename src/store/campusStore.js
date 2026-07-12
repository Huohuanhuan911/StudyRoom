import { reactive, ref } from 'vue'
import * as authApi from '../api/auth'
import * as campusApi from '../api/campus'
import * as bookingApi from '../api/booking'
import * as adminApi from '../api/admin'

const buildings = reactive([])
const classrooms = reactive([])
const seats = reactive({})
const bookings = reactive([])
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
      this.loadSettings()
    ])
  },
  
  async loadBuildings() {
    loading.buildings = true
    try {
      const res = await campusApi.getBuildings()
      buildings.length = 0
      buildings.push(...res.data)
    } catch (e) {
      console.error('加载楼栋失败:', e)
    } finally {
      loading.buildings = false
    }
  },
  
  async loadClassrooms() {
    loading.classrooms = true
    try {
      const res = await campusApi.getClassrooms()
      classrooms.length = 0
      classrooms.push(...res.data)
    } catch (e) {
      console.error('加载教室失败:', e)
    } finally {
      loading.classrooms = false
    }
  },
  
  async loadSeats(classroomId) {
    loading.seats = true
    try {
      const res = await campusApi.getSeats(classroomId)
      seats[classroomId] = res.data || []
    } catch (e) {
      console.error('加载座位失败:', e)
    } finally {
      loading.seats = false
    }
  },
  
  async loadBookings() {
    loading.bookings = true
    try {
      const res = await bookingApi.getMyBookings()
      bookings.length = 0
      bookings.push(...res.data)
    } catch (e) {
      console.error('加载预约失败:', e)
    } finally {
      loading.bookings = false
    }
  },
  
  async loadUsers() {
    loading.users = true
    try {
      const res = await adminApi.getUsers()
      users.length = 0
      users.push(...res.data)
    } catch (e) {
      console.error('加载用户失败:', e)
    } finally {
      loading.users = false
    }
  },
  
  async loadSettings() {
    loading.settings = true
    try {
      const res = await adminApi.getSystemSettings()
      Object.assign(systemSettings, res.data)
    } catch (e) {
      console.error('加载设置失败:', e)
    } finally {
      loading.settings = false
    }
  },
  
  async addBuilding(building) {
    try {
      const res = await campusApi.createBuilding(building)
      buildings.push(res.data)
      return res.data
    } catch (e) {
      console.error('添加楼栋失败:', e)
      throw e
    }
  },
  
  async updateBuilding(id, updates) {
    try {
      const res = await campusApi.updateBuilding(id, updates)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index] = { ...buildings[index], ...res.data }
      }
      return res.data
    } catch (e) {
      console.error('更新楼栋失败:', e)
      throw e
    }
  },
  
  async deleteBuilding(id) {
    try {
      const res = await campusApi.deleteBuilding(id)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index].deleted = true
        buildings[index].deletedAt = res.data.deletedAt
      }
      return res.data
    } catch (e) {
      console.error('删除楼栋失败:', e)
      throw e
    }
  },
  
  async restoreBuilding(id) {
    try {
      const res = await campusApi.restoreBuilding(id)
      const index = buildings.findIndex(b => b.id === id)
      if (index !== -1) {
        buildings[index].deleted = false
        buildings[index].deletedAt = null
      }
      return res.data
    } catch (e) {
      console.error('恢复楼栋失败:', e)
      throw e
    }
  },
  
  async addClassroom(classroom) {
    try {
      const res = await campusApi.createClassroom(classroom)
      classrooms.push(res.data)
      seats[res.data.id] = []
      return res.data
    } catch (e) {
      console.error('添加教室失败:', e)
      throw e
    }
  },
  
  async updateClassroom(id, updates) {
    try {
      const res = await campusApi.updateClassroom(id, updates)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index] = { ...classrooms[index], ...res.data }
      }
      return res.data
    } catch (e) {
      console.error('更新教室失败:', e)
      throw e
    }
  },
  
  async deleteClassroom(id) {
    try {
      const res = await campusApi.deleteClassroom(id)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index].deleted = true
        classrooms[index].deletedAt = res.data.deletedAt
      }
      return res.data
    } catch (e) {
      console.error('删除教室失败:', e)
      throw e
    }
  },
  
  async restoreClassroom(id) {
    try {
      const res = await campusApi.restoreClassroom(id)
      const index = classrooms.findIndex(c => c.id === id)
      if (index !== -1) {
        classrooms[index].deleted = false
        classrooms[index].deletedAt = null
      }
      return res.data
    } catch (e) {
      console.error('恢复教室失败:', e)
      throw e
    }
  },
  
  async addSeat(classroomId, seat) {
    try {
      const res = await campusApi.createSeat({ ...seat, classroomId })
      if (!seats[classroomId]) {
        seats[classroomId] = []
      }
      seats[classroomId].push(res.data)
      return res.data
    } catch (e) {
      console.error('添加座位失败:', e)
      throw e
    }
  },
  
  async updateSeat(classroomId, seatId, updates) {
    try {
      const res = await campusApi.updateSeat(seatId, updates)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index] = { ...classroomSeats[index], ...res.data }
      }
      return res.data
    } catch (e) {
      console.error('更新座位失败:', e)
      throw e
    }
  },
  
  async deleteSeat(classroomId, seatId) {
    try {
      const res = await campusApi.deleteSeat(seatId)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index].deleted = true
      }
      return res.data
    } catch (e) {
      console.error('删除座位失败:', e)
      throw e
    }
  },
  
  async restoreSeat(classroomId, seatId) {
    try {
      const res = await campusApi.restoreSeat(seatId)
      const classroomSeats = seats[classroomId] || []
      const index = classroomSeats.findIndex(s => s.id === seatId)
      if (index !== -1) {
        classroomSeats[index].deleted = false
      }
      return res.data
    } catch (e) {
      console.error('恢复座位失败:', e)
      throw e
    }
  },
  
  async importSeats(classroomId, newSeats) {
    try {
      for (const seat of newSeats) {
        await campusApi.createSeat({ ...seat, classroomId })
      }
      await this.loadSeats(classroomId)
      return newSeats.length
    } catch (e) {
      console.error('导入座位失败:', e)
      throw e
    }
  },
  
  async addBooking(booking) {
    try {
      const res = await bookingApi.createBooking(booking)
      bookings.push(res.data)
      return res.data
    } catch (e) {
      console.error('创建预约失败:', e)
      throw e
    }
  },
  
  async updateBooking(id, updates) {
    try {
      const res = await bookingApi.updateBooking(id, updates)
      const index = bookings.findIndex(b => b.id === id)
      if (index !== -1) {
        bookings[index] = { ...bookings[index], ...res.data }
      }
      return res.data
    } catch (e) {
      console.error('更新预约失败:', e)
      throw e
    }
  },
  
  async cancelBooking(id) {
    try {
      const res = await bookingApi.cancelBooking(id)
      const index = bookings.findIndex(b => b.id === id)
      if (index !== -1) {
        bookings[index].status = 'cancelled'
        bookings[index].statusText = '已取消'
      }
      return res.data
    } catch (e) {
      console.error('取消预约失败:', e)
      throw e
    }
  },
  
  async signinBooking(id, data) {
    try {
      const res = await bookingApi.signinBooking(id, data)
      const index = bookings.findIndex(b => b.id === id)
      if (index !== -1) {
        bookings[index].status = 'in_progress'
        bookings[index].statusText = '学习中'
        bookings[index].signedInAt = res.data.signedInAt
      }
      return res.data
    } catch (e) {
      console.error('签到失败:', e)
      throw e
    }
  },
  
  async releaseSeat(id) {
    try {
      const res = await bookingApi.releaseSeat(id)
      const index = bookings.findIndex(b => b.id === id)
      if (index !== -1) {
        bookings[index].status = 'completed'
        bookings[index].statusText = '已释放'
        bookings[index].releasedAt = res.data.releasedAt
      }
      return res.data
    } catch (e) {
      console.error('释放座位失败:', e)
      throw e
    }
  },
  
  async addToBlacklist(userId, reason) {
    try {
      const res = await adminApi.updateUser(userId, { inBlacklist: true, blacklistReason: reason })
      const user = users.find(u => u.id === userId)
      if (user) {
        user.inBlacklist = true
        user.blacklistReason = reason
      }
      blacklistRecords.push({
        id: Date.now(),
        userId,
        reason,
        createdAt: new Date().toISOString()
      })
      return res.data
    } catch (e) {
      console.error('添加黑名单失败:', e)
      throw e
    }
  },
  
  async removeFromBlacklist(userId) {
    try {
      const res = await adminApi.removeBlacklist(userId)
      const user = users.find(u => u.id === userId)
      if (user) {
        user.inBlacklist = false
        user.blacklistReason = ''
        user.blacklistExpireAt = null
        user.violationCount = 0
      }
      return res.data
    } catch (e) {
      console.error('移除黑名单失败:', e)
      throw e
    }
  },
  
  async updateUser(id, updates) {
    try {
      const res = await adminApi.updateUser(id, updates)
      const index = users.findIndex(u => u.id === id)
      if (index !== -1) {
        users[index] = { ...users[index], ...res.data }
      }
      return res.data
    } catch (e) {
      console.error('更新用户失败:', e)
      throw e
    }
  },
  
  async deleteUser(id) {
    try {
      const res = await adminApi.deleteUser(id)
      const index = users.findIndex(u => u.id === id)
      if (index !== -1) {
        users.splice(index, 1)
      }
      return res.data
    } catch (e) {
      console.error('删除用户失败:', e)
      throw e
    }
  },
  
  async importUsers(newUsers) {
    try {
      for (const user of newUsers) {
        await adminApi.createUser(user)
      }
      await this.loadUsers()
    } catch (e) {
      console.error('导入用户失败:', e)
      throw e
    }
  },
  
  async addViolation(userId) {
    try {
      const res = await adminApi.updateUser(userId, { violationCount: { $inc: 1 } })
      const user = users.find(u => u.id === userId)
      if (user) {
        user.violationCount++
        user.creditScore = Math.max(0, user.creditScore - 20)
        if (user.creditScore === 0) {
          await this.addToBlacklist(userId, '信誉积分归零')
        } else if (user.violationCount >= systemSettings.violationLimit) {
          const expireDate = new Date()
          expireDate.setDate(expireDate.getDate() + systemSettings.violationBanDays)
          await adminApi.updateUser(userId, {
            inBlacklist: true,
            blacklistReason: '累计违约',
            blacklistExpireAt: expireDate.toISOString()
          })
        }
      }
      return res.data
    } catch (e) {
      console.error('添加违约记录失败:', e)
      throw e
    }
  },
  
  async checkBlacklistExpire(userId) {
    const user = users.find(u => u.id === userId)
    if (user && user.inBlacklist && user.blacklistExpireAt) {
      const expireDate = new Date(user.blacklistExpireAt)
      if (new Date() > expireDate) {
        await this.removeFromBlacklist(userId)
      }
    }
  },
  
  async checkSeatReleaseViolation() {
    const now = new Date()
    for (const booking of bookings) {
      if (booking.status === 'in_progress') {
        const endTime = new Date(`${booking.date}T${booking.endTime}`)
        const releaseDeadline = new Date(endTime.getTime() + 30 * 60 * 1000)
        if (now > releaseDeadline) {
          await this.releaseSeat(booking.id)
          await this.addViolation(booking.studentId)
        }
      }
    }
  },
  
  async updateSystemSettings(updates) {
    try {
      const res = await adminApi.updateSystemSettings(updates)
      Object.assign(systemSettings, res.data)
      return res.data
    } catch (e) {
      console.error('更新系统设置失败:', e)
      throw e
    }
  },
  
  async batchDeleteBuildings(ids) {
    try {
      const res = await adminApi.batchDeleteBuildings(ids)
      ids.forEach(id => {
        const index = buildings.findIndex(b => b.id === id)
        if (index !== -1) {
          buildings[index].deleted = true
        }
      })
      return res.data
    } catch (e) {
      console.error('批量删除楼栋失败:', e)
      throw e
    }
  },
  
  async batchRestoreBuildings(ids) {
    try {
      const res = await adminApi.batchRestoreBuildings(ids)
      ids.forEach(id => {
        const index = buildings.findIndex(b => b.id === id)
        if (index !== -1) {
          buildings[index].deleted = false
        }
      })
      return res.data
    } catch (e) {
      console.error('批量恢复楼栋失败:', e)
      throw e
    }
  },
  
  async batchDeleteClassrooms(ids) {
    try {
      const res = await adminApi.batchDeleteClassrooms(ids)
      ids.forEach(id => {
        const index = classrooms.findIndex(c => c.id === id)
        if (index !== -1) {
          classrooms[index].deleted = true
        }
      })
      return res.data
    } catch (e) {
      console.error('批量删除教室失败:', e)
      throw e
    }
  },
  
  async batchRestoreClassrooms(ids) {
    try {
      const res = await adminApi.batchRestoreClassrooms(ids)
      ids.forEach(id => {
        const index = classrooms.findIndex(c => c.id === id)
        if (index !== -1) {
          classrooms[index].deleted = false
        }
      })
      return res.data
    } catch (e) {
      console.error('批量恢复教室失败:', e)
      throw e
    }
  },
  
  async batchDeleteSeats(ids) {
    try {
      const res = await adminApi.batchDeleteSeats(ids)
      for (const classroomId in seats) {
        seats[classroomId].forEach(s => {
          if (ids.includes(s.id)) {
            s.deleted = true
          }
        })
      }
      return res.data
    } catch (e) {
      console.error('批量删除座位失败:', e)
      throw e
    }
  },
  
  async batchRestoreSeats(ids) {
    try {
      const res = await adminApi.batchRestoreSeats(ids)
      for (const classroomId in seats) {
        seats[classroomId].forEach(s => {
          if (ids.includes(s.id)) {
            s.deleted = false
          }
        })
      }
      return res.data
    } catch (e) {
      console.error('批量恢复座位失败:', e)
      throw e
    }
  },
  
  getBuildingById(id) {
    return buildings.find(b => b.id === id)
  },
  
  getBuildingName(id) {
    const building = buildings.find(b => b.id === id)
    return building ? building.name : ''
  },
  
  getClassroomById(id) {
    return classrooms.find(c => c.id === id)
  },
  
  getClassroomsByBuilding(buildingId) {
    return classrooms.filter(c => c.buildingId === buildingId && !c.deleted)
  },
  
  getSeatsByClassroom(classroomId) {
    return seats[classroomId] || []
  },
  
  getBookingsByStudent(studentId) {
    return bookings.filter(b => b.studentId === studentId)
  },
  
  getActiveClassrooms() {
    return classrooms.filter(c => !c.deleted)
  },
  
  getDeletedClassrooms() {
    return classrooms.filter(c => c.deleted)
  }
}

export function useCampusStore() {
  return store
}