import request from './request'

export const getBookings = (params) => {
  return request.get('/booking/list', { params })
}

export const createBooking = (data) => {
  return request.post('/booking/create', data)
}

export const cancelBooking = (id) => {
  return request.post(`/booking/${id}/cancel`)
}

export const signinBooking = (id, data) => {
  return request.post(`/booking/${id}/signin`, data)
}

export const releaseSeat = (id) => {
  return request.post(`/booking/${id}/release`)
}

export const getMyBookings = () => {
  return request.get('/booking/my')
}

export const updateBooking = (id, data) => {
  return request.put(`/booking/${id}`, data)
}