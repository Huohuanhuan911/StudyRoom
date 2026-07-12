import request from './request'

export const getUsers = (params) => {
  return request.get('/admin/users', { params })
}

export const getUserById = (id) => {
  return request.get(`/admin/users/${id}`)
}

export const updateUser = (id, data) => {
  return request.put(`/admin/users/${id}`, data)
}

export const removeBlacklist = (id) => {
  return request.post(`/admin/users/${id}/remove-blacklist`)
}

export const getBlacklist = () => {
  return request.get('/admin/blacklist')
}

export const getReports = (params) => {
  return request.get('/admin/reports', { params })
}

export const exportReports = (params) => {
  return request.get('/admin/reports/export', { params, responseType: 'blob' })
}

export const getSystemSettings = () => {
  return request.get('/admin/settings')
}

export const updateSystemSettings = (data) => {
  return request.put('/admin/settings', data)
}

export const batchDeleteBuildings = (ids) => {
  return request.post('/admin/buildings/batch-delete', { ids })
}

export const batchRestoreBuildings = (ids) => {
  return request.post('/admin/buildings/batch-restore', { ids })
}

export const batchDeleteClassrooms = (ids) => {
  return request.post('/admin/classrooms/batch-delete', { ids })
}

export const batchRestoreClassrooms = (ids) => {
  return request.post('/admin/classrooms/batch-restore', { ids })
}

export const batchDeleteSeats = (ids) => {
  return request.post('/admin/seats/batch-delete', { ids })
}

export const batchRestoreSeats = (ids) => {
  return request.post('/admin/seats/batch-restore', { ids })
}

export const deleteUser = (id) => {
  return request.delete(`/admin/users/${id}`)
}

export const createUser = (data) => {
  return request.post('/admin/users', data)
}