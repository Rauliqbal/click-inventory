import { useEffect } from 'react'
import { useAuthStore } from './stores/auth'
import Router from './Router'

function App() {
  const checkAuth = useAuthStore((state) => state.checkAuth)

  useEffect(() => {
    checkAuth()
  }, [checkAuth])
  return (
    <>
      <Router />
    </>
  )
}

export default App
