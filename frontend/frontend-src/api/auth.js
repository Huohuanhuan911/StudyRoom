import request from './request'

export const login = (data) => {
  return request.post('/auth/login', data)
}

export const logout = () => {
  return request.post('/auth/logout')
}

export const getUserInfo = () => {
  return request.get('/auth/userinfo')
}

export const register = (data) => {
  return request.post('/auth/register', data)
}

export const updateUserInfo = (data) => {
  return request.put('/auth/userinfo', data)
}