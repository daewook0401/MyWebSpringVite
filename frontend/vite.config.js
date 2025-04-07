import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

const home = "192.168.219.105"
const out = "biglight.rlaeodnr.kro.kr"
const local = "localhost"
const url  = `http://${local}:80`

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,   // 0.0.0.0 으로 열어주거나 LAN IP 로 접근 가능
    port: 3000,
    proxy: {
      // /board 로 시작하는 요청 → http://localhost:8080 로 프록시
      '/boards': {
        target: url,
        changeOrigin: true,
        secure: false,
      },
      // /users 로 시작하는 요청 → http://192.168.219.105 로 프록시
      '/users': {
        target: url,
        changeOrigin: true,
        secure: false,
      },
      '/auth': {
        target: url,
        changeOrigin: true,
        secure: false,
      },
      '/categories': {
        target: url,
        changeOrigin: true,
        secure: false,
      },
      '/comments':{
        target: url,
        changeOrigin: true,
        secure: false,
      }
    },
  },
})
