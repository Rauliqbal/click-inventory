import { create } from "zustand";
import { api } from "../service/api";

interface AuthState {
  user: User | null;
  token: string | null;
  loading: boolean
  error: string | null;
  isInitialized: boolean
  checkAuth: () => Promise<void>

  login: (username: string, password: string) => Promise<boolean>
  logout: () => Promise<void>
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  token: null,
  loading: false,
  error: null,
  isInitialized: false,

  checkAuth: async () => {
    try {
      const { data } = await api.get('/user');
      set({ user: data.data, isInitialized: true });

    } catch {
      set({ user: null, isInitialized: true });
    }
  },

  login: async (username, password) => {
    set({
      loading: true,
    })
    try {
      const { data } = await api.post('/auth/login', {
        username, password
      })

      set({
        token: data.token,
        loading: false
      })

      return true
    } catch (error: any) {
      set({
        error: error.response?.data?.errors || "Login failed",
        loading: false
      })
      return false
    }
  },

  logout: async () => {
    set({
      user: null
    })

    await api.post('/auth/logout')
  }

}))



interface User {
  id: string;
  username: string,
  fullname: string,
  email: string,
  role: string
}
