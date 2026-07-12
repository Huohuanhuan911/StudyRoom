<template>
  <div class="login-page">
    <div class="header-bar">
      <div class="header-left">
        <div class="header-icon">
          <div class="icon-circle"></div>
          <div class="icon-triangle"></div>
        </div>
        <span class="header-text">智能自习室预约管理平台</span>
      </div>
    </div>
    <div class="login-container">
      <div class="login-form">
        <div class="form-group">
          <label class="form-label">学号/用户名</label>
          <input 
            type="text" 
            class="form-input" 
            :class="{ 'error': errors.username }"
            v-model="username" 
            placeholder="请输入学号或用户名"
          />
          <span v-if="errors.username" class="error-message">{{ errors.username }}</span>
        </div>
        <div class="form-group">
          <label class="form-label">密码</label>
          <div class="password-wrapper">
            <input 
              :type="showPassword ? 'text' : 'password'" 
              class="form-input" 
              :class="{ 'error': errors.password }"
              v-model="password" 
              placeholder="请输入密码"
              autocomplete="off"
              autocapitalize="off"
              autocorrect="off"
              spellcheck="false"
            />
            <button class="eye-btn" @click="showPassword = !showPassword" type="button">
              <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#999" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/>
                <circle cx="12" cy="12" r="3"/>
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#999" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M9.87 9.87a3 3 0 1 0 4.26 4.26"/>
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4Z"/>
                <path d="M16.5 16.5c-1.5 1.5-3.5 2.5-5.5 2.5-2 0-4-.5-5.5-2.5"/>
                <path d="M2 2l20 20"/>
              </svg>
            </button>
          </div>
          <span v-if="errors.password" class="error-message">{{ errors.password }}</span>
        </div>
        <button class="login-btn" @click="handleLogin" :disabled="loading">
  <span v-if="loading">登录中...</span>
  <span v-else>登录</span>
</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api/auth'

const router = useRouter()
const username = ref('')
const password = ref('')
const showPassword = ref(false)
const errors = reactive({
  username: '',
  password: ''
})
const loading = ref(false)

const handleLogin = async () => {
  errors.username = ''
  errors.password = ''

  if (!username.value) {
    errors.username = '请输入学号或用户名'
  }
  if (!password.value) {
    errors.password = '请输入密码'
  }

  if (errors.username || errors.password) {
    return
  }

  loading.value = true

  try {
    const res = await login({ username: username.value, password: password.value })
    localStorage.setItem('token', res.token)
    localStorage.setItem('user', JSON.stringify(res.user))
    
    if (res.user.role === 'admin') {
      router.push('/admin')
    } else {
      router.push('/student')
    }
  } catch (e) {
    errors.username = '用户名或密码错误'
    errors.password = '用户名或密码错误'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
}

.login-page::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('../../img/login.png') no-repeat center center;
  background-size: cover;
  filter: blur(3px);
  z-index: -1;
}

.header-bar {
  background: #5483B3;
  height: 60px;
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
  background: #C1E8FF;
  border-radius: 50%;
}

.header-icon .icon-triangle {
  width: 30px;
  height: 22px;
  background: #C1E8FF;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
}

.header-text {
  font-size: 20px;
  color: #C1E8FF;
  font-weight: bold;
  letter-spacing: 2px;
}

.login-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-form {
  width: 500px;
  background: white;
  border-radius: 12px;
  padding: 50px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
}

.form-group {
  margin-bottom: 28px;
  height: 90px;
}

.form-label {
  display: block;
  font-size: 16px;
  color: #333;
  margin-bottom: 8px;
  font-weight: 500;
}

.form-input {
  width: 100%;
  height: 44px;
  border: 1px solid #E0E0E0;
  border-radius: 8px;
  padding: 0 16px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.3s ease;
}

.password-wrapper {
  position: relative;
}

.password-wrapper .form-input {
  padding-right: 48px;
}

.password-wrapper .form-input::-webkit-credentials-auto-fill-button {
  display: none;
}

.password-wrapper .form-input::-ms-reveal,
.password-wrapper .form-input::-ms-clear {
  display: none;
}

.eye-btn {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.eye-btn:hover svg {
  stroke: #5483B3;
}

.form-input:focus {
  border-color: #5483B3;
}

.form-input.error {
  border-color: #E53935;
}

.form-input::placeholder {
  color: #999;
}

.error-message {
  display: block;
  color: #E53935;
  font-size: 12px;
  margin-top: 6px;
}

.login-btn {
  width: 100%;
  height: 48px;
  background: #5483B3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: opacity 0.3s ease, transform 0.2s ease;
}

.login-btn:hover {
  opacity: 0.9;
}

.login-btn:active {
  transform: scale(0.98);
}

@media (max-width: 520px) {
  .login-container {
    justify-content: center;
    padding-right: 0;
    padding: 20px;
  }

  .login-form {
    width: 100%;
    padding: 30px;
  }

  .header-text {
    font-size: 16px;
  }
}
</style>
