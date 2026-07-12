<template>
  <div class="home-page">
    <div class="left-section">
      <div class="title-row">
        <div class="student-icon">
          <div class="icon-head"></div>
          <div class="icon-body"></div>
        </div>
        <h1 class="title">智慧校园</h1>
      </div>
      <h1 class="title">自习室</h1>
      <h1 class="title">预约管理平台</h1>
    </div>
    <div class="right-section">
      <button 
        class="start-btn" 
        :class="{ bounce: isBouncing }"
        @click="handleStart"
      >
        <span class="start-text">START</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isBouncing = ref(false)

const handleStart = () => {
  isBouncing.value = true
  setTimeout(() => {
    isBouncing.value = false
    router.push('/login')
  }, 300)
}
</script>

<style scoped>
.home-page {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 80px;
  background: linear-gradient(90deg, #5483B3 0%, #7AA8D1 40%, #FFFFFF 100%);
  position: relative;
}

.left-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-start;
  padding-left: 120px;
}

.title-row {
  display: flex;
  align-items: center;
  gap: 20px;
}

.student-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.icon-head {
  width: 32px;
  height: 32px;
  background: white;
  border-radius: 50%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.icon-body {
  width: 48px;
  height: 36px;
  background: white;
  clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-family: 'ZCOOL KuaiLe', cursive, 'Microsoft YaHei', sans-serif;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.15);
  font-size: 96px;
  letter-spacing: 12px;
  line-height: 1.3;
}

.right-section {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-left: 40px;
}

.start-btn {
  width: 240px;
  height: 240px;
  border-radius: 50%;
  background: #5483B3;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  box-shadow: 0 8px 32px rgba(84, 131, 179, 0.4);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.start-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 12px 40px rgba(84, 131, 179, 0.5);
}

.start-btn:active {
  transform: scale(0.95);
}

.start-text {
  font-family: 'ZCOOL KuaiLe', cursive, 'Arial', sans-serif;
  font-size: 36px;
  color: white;
  font-weight: bold;
  letter-spacing: 2px;
}

.start-btn.bounce {
  animation: bounce-animation 0.6s ease;
}

@keyframes bounce-animation {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(0.9);
  }
  50% {
    transform: scale(1.15);
  }
  75% {
    transform: scale(0.95);
  }
  100% {
    transform: scale(1);
  }
}

@media (max-width: 1200px) {
  .home-page {
    flex-direction: column;
    padding: 40px 20px;
    text-align: center;
  }

  .left-section {
    align-items: center;
    padding-left: 0;
  }

  .title-row {
    justify-content: center;
  }

  .title {
    font-size: 56px;
    letter-spacing: 6px;
  }

  .right-section {
    padding-left: 0;
    justify-content: center;
  }

  .start-btn {
    width: 180px;
    height: 180px;
    margin-top: 20px;
  }

  .start-text {
    font-size: 28px;
  }
}
</style>
