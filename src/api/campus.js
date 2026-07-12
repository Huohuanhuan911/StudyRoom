import request from './request'

export const getBuildings = () => {
  return request.get('/campus/buildings')
}

export const getBuildingById = (id) => {
  return request.get(`/campus/buildings/${id}`)
}

export const createBuilding = (data) => {
  return request.post('/campus/buildings', data)
}

export const updateBuilding = (id, data) => {
  return request.put(`/campus/buildings/${id}`, data)
}

export const deleteBuilding = (id) => {
  return request.delete(`/campus/buildings/${id}`)
}

export const restoreBuilding = (id) => {
  return request.post(`/campus/buildings/${id}/restore`)
}

export const getClassrooms = (buildingId) => {
  return request.get('/campus/classrooms', { params: { buildingId } })
}

export const getClassroomById = (id) => {
  return request.get(`/campus/classrooms/${id}`)
}

export const createClassroom = (data) => {
  return request.post('/campus/classrooms', data)
}

export const updateClassroom = (id, data) => {
  return request.put(`/campus/classrooms/${id}`, data)
}

export const deleteClassroom = (id) => {
  return request.delete(`/campus/classrooms/${id}`)
}

export const restoreClassroom = (id) => {
  return request.post(`/campus/classrooms/${id}/restore`)
}

export const getSeats = (classroomId) => {
  return request.get('/campus/seats', { params: { classroomId } })
}

export const getSeatById = (id) => {
  return request.get(`/campus/seats/${id}`)
}

export const createSeat = (data) => {
  return request.post('/campus/seats', data)
}

export const updateSeat = (id, data) => {
  return request.put(`/campus/seats/${id}`, data)
}

export const deleteSeat = (id) => {
  return request.delete(`/campus/seats/${id}`)
}

export const restoreSeat = (id) => {
  return request.post(`/campus/seats/${id}/restore`)
}