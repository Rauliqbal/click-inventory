import React, { useState } from 'react'
import { Button } from '../components/ui/button'
import { useNavigate } from 'react-router'
import { useAuthStore } from '../stores/auth'

export default function Login() {
  const navigate = useNavigate()
  const { login, loading, error } = useAuthStore()

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    const success = await login(username, password)
    
    if(success) {
      navigate('/')
    }
  }

  return (
    <div className='flex items-center justify-center h-screen'>
      <div className="bg-gray-100 rounded-xl p-8 w-full max-w-sm shadow-sm">
        <p className='text-3xl font-bold mb-6 text-center'>Welcome back</p>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-2 rounded-lg mb-4 text-sm">
            {error}
          </div>
        )}

        <form onSubmit={handleLogin} className='space-y-4'>
          <div className='flex flex-col gap-2'>
            <label htmlFor="username" className="text-sm font-medium">Username</label>
            <input 
              value={username} 
              onChange={(e) => setUsername(e.target.value)} 
              className='p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500' 
              type="text" 
              name="username" 
              id="username" 
              placeholder='admin' 
              required
            />
          </div>

          <div className='flex flex-col gap-2'>
            <label htmlFor="password" senior className="text-sm font-medium">Password</label>
            <input 
              value={password} 
              onChange={(e) => setPassword(e.target.value)} 
              className='p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500' 
              type="password" 
              name="password" 
              id="password" 
              placeholder='******' 
              required
            />
          </div>

          <Button 
            type='submit' 
            className='w-full' 
            disabled={loading}
          >
            {loading ? (
              <div className="flex items-center gap-2">
                <div className="h-4 w-4 animate-spin rounded-full border-2 border-white border-t-transparent"></div>
                Loading...
              </div>
            ) : "Login"}
          </Button>
        </form>
      </div>
    </div>
  )
}