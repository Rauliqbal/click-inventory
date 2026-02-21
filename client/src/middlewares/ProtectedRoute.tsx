import { Navigate, Outlet } from 'react-router'
import { useAuthStore } from '../stores/auth'

interface RouteProps {
  type: 'public' | 'private'
}

export default function ProtectedRoute({ type }: RouteProps) {
  const { user, isInitialized } = useAuthStore()

  if (!isInitialized) {
    return <div className="h-screen flex items-center justify-center">Loading...</div>
  }

  if (type === 'private' && !user) {
    return <Navigate to="/login" replace />
  }

  if (type === 'public' && user) {
    return <Navigate to="/" replace />
  }

  return <Outlet />
}